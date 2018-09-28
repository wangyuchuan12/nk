package com.ifrabbit.nk.mq.customer;


import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.service.TaskOpinion;
import com.ifrabbit.nk.constant.Constant;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.repository.ExpressInfoRecordRepository;
import com.ifrabbit.nk.express.service.*;
import com.ifrabbit.nk.flow.process.utils.GetLocalHost;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.domain.JobList;
import com.ifrabbit.nk.usercenter.domain.JobListDTO;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import com.ifrabbit.nk.usercenter.service.JobListService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import ir.nymph.date.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/8/20
 * Time:16:47
 */


@Component
@RabbitListener(queues = Constant.EXPRESS_QUEUE_NAME)
public class ExpressDelayedReceiver {
    private static Logger logger = LoggerFactory.getLogger(ExpressDelayedReceiver.class);
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
    @Autowired
    ExpressInfoRecordRepository expressInfoRecordRepository;
    @Autowired
    ExpressInfoRecordService expressInfoRecordService;

    @RabbitHandler
    //用户处理joblist的解析
    public void process(JobList jobList) {
        String expressNumber = jobList.getJoblistExpressnumber();
        Long problemID = jobList.getJoblistProblemid();
        Long dealID = jobList.getJoblistDealid();
        String nodeName = jobList.getJoblistDealname();
        Integer dealType = jobList.getJoblistDealtype();
        Long tabID = jobList.getJoblistTabid();
        DateTime dateTime = new DateTime();
        Integer interval = null;
        String exType = null;

        Dealinfo dealinfo = dealinfoService.get(dealID);
        Long taskID = dealinfo.getAppdealTaskid();
        TableInfo tableInfo = tableInfoService.get(tabID);
        Long instanceID = tableInfo.getTableinfo_tabid();

        // TODO: 2018/9/16 getJoblistRecord的长度需要固化
        String record = jobList.getJoblistRecord();

            if(record != null && !record.equals("")){
                String[] records= record.split("/");
                String[] day = records[0].split("-");
                String[] modal = records[1].split("-");
                String param = day[1] + modal[1];

                switch (param) {
                    case "D1M1":
                        interval = Integer.valueOf(userReportService.getParameter("W1"));
                        exType = userReportService.getParameter("W6");
                        break;
                    case "D2M2":
                        interval = Integer.valueOf(userReportService.getParameter("W3"));
                        exType = userReportService.getParameter("W7");
                        break;
                    case "D2M3":
                        interval = Integer.valueOf(userReportService.getParameter("W3"));
                        exType = userReportService.getParameter("W8");
                        break;
                }
            }

        if (dealType == 201) {
            //可以直接完成
            setParamsOfAfterADay(instanceID,taskID,dealType, expressNumber, problemID, dealID, nodeName, dateTime,interval,exType,tabID);
        }
    }

    //设置第二天的时间
    private void setParamsOfAfterADay(Long instanceID,Long taskID,Integer dealType, String expressNumber, Long problemID,Long dealID, String nodeName, DateTime dateTime,Integer interval,String exType,Long tabID) {
        ExpressInfoRecord expressInfoRecord = new ExpressInfoRecord();
        Timestamp timestamp;
        String ipAddress;

        if(interval != 0) {
            //设置计划查询时间为 第二天中午9点开始查询,(以参数为准)
            timestamp = GetTimeAfterDay(dateTime,interval);
            expressInfoRecord.setExpress_plantime(timestamp);
        }else{
            timestamp = dateTime.toTimestamp();
            expressInfoRecord.setExpress_plantime(timestamp);
        }

        //存入所需的值
        expressInfoRecord.setExpress_state(0);
        expressInfoRecord.setExpress_taskid(taskID);
        expressInfoRecord.setExpress_nodename(nodeName);
        expressInfoRecord.setExpress_dealtype(dealType);
        expressInfoRecord.setExpress_number(expressNumber);
        expressInfoRecord.setExpress_problemid(problemID);
        expressInfoRecord.setExpress_modal(exType);
        expressInfoRecord.setExpress_tabid(tabID);
        expressInfoRecord.setExpress_dealid(dealID);
        expressInfoRecord.setExpress_processinstanceid(instanceID);

        //绑定局域网ip
        ipAddress = GetLocalHost.getIp();
        expressInfoRecord.setExpress_ipaddress(ipAddress);

        expressInfoRecordService.insert(expressInfoRecord);
    }

    //设置计划查询时间为 9点开始查询(以系统参数为准)
    private Timestamp GetTimeAfterDay(DateTime dateTime,Integer interval) {
        Calendar calendar = dateTime.toCalendar();
        calendar.add(calendar.DATE,1);
        calendar.set(calendar.HOUR_OF_DAY,interval);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestamp = new DateTime(calendar.getTimeInMillis()).toTimestamp();
        return timestamp;
    }
}
