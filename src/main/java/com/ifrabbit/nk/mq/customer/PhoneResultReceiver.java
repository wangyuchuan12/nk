package com.ifrabbit.nk.mq.customer;

import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.ifrabbit.nk.constant.Constant;
import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.express.domain.Dealinfo;
import com.ifrabbit.nk.express.domain.TableInfo;
import com.ifrabbit.nk.express.domain.TableInfoDTO;
import com.ifrabbit.nk.express.repository.ExpressInfoRecordRepository;
import com.ifrabbit.nk.express.service.*;
import com.ifrabbit.nk.usercenter.domain.JobList;
import com.ifrabbit.nk.usercenter.domain.JobListDTO;
import com.ifrabbit.nk.usercenter.service.JobListService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Auther: WangYan
 * @Date: 2018/9/25 17:37
 * @Description:
 */
@Component
@RabbitListener(queues = Constant.PHONERESULT_QUEUE_NAME)
public class PhoneResultReceiver {
    private static Logger logger = LoggerFactory.getLogger(PhoneResultReceiver.class);
    @Autowired
    private CallDetailService callDetailService;
    @Autowired
    DealinfoService dealinfoService;
    @Autowired
    TableInfoService tableInfoService;
    @Autowired
    ProblemService problemService;
    @Autowired
    ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    UserReportService userReportService;
    @Qualifier("uflo.taskService")
    @Autowired
    private com.bstek.uflo.service.TaskService decorator;
    @Autowired
    ExpressInfoRecordRepository expressInfoRecordRepository;
    @Autowired
    ExpressInfoRecordService expressInfoRecordService;
    @Autowired
    private JobListService jobListService;

    @RabbitHandler
    public void process(CallDetail callDetail) {
        logger.info("DelayedReceiver 接受时间: " + LocalDateTime.now() + " callDetail内容：" + callDetail);
        //更新CallDeatil
        callDetailService.updateIgnore(callDetail);
        Long taskId = callDetail.getCalldetail_dealid();
        Long processInstanceId = callDetail.getCalldetail_tableid();
        TableInfoDTO cond = new TableInfoDTO();
        Dealinfo dealinfo = new Dealinfo();
        TableInfo lastTableInfo = new TableInfo();
        cond.setTableinfo_tabid(processInstanceId);
        //找到子流程tableinfo 并更新结果
        TableInfo tableInfo = tableInfoService.findOne(cond);
        tableInfo.setTableinfo_result(callDetail.getCalldetail_answerid());
        if (callDetail.getCalldetail_result() != null){
            tableInfo.setTableinfo_varparama(callDetail.getCalldetail_result().toString());
            dealinfo.setAppdealVarparama(callDetail.getCalldetail_result().toString());
            lastTableInfo.setTableinfo_varparama(callDetail.getCalldetail_result().toString());
        }
        tableInfoService.updateIgnore(tableInfo);
        //更新dealInfo的结果数据
        dealinfo.setId(tableInfo.getTableinfo_dealid());
        dealinfo.setAppdealResult(callDetail.getCalldetail_answerid());
        dealinfo.setAppdealResulttext(callDetail.getCalldetail_answer());
        dealinfoService.updateIgnore(dealinfo);
        //同步更新主流程的结果数据
        lastTableInfo.setTableinfo_result(callDetail.getCalldetail_answerid());
        lastTableInfo.setId(tableInfo.getTableinfo_uptabid());
        tableInfoService.updateIgnore(lastTableInfo);
        Task task = decorator.getTask(callDetail.getCalldetail_dealid());
        TaskState state = task.getState();
        JobListDTO job = new JobListDTO();
        job.setJoblistDealid(taskId);
        JobList one = jobListService.findOne(job);
        one.setJoblistState(1);
        jobListService.updateIgnore(one);
        if (state.equals(TaskState.InProgress)) {
            decorator.complete(callDetail.getCalldetail_dealid());
        } else {
            decorator.start(callDetail.getCalldetail_dealid());
            decorator.complete(callDetail.getCalldetail_dealid());
        }
    }
}
