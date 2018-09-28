package com.ifrabbit.nk.flow.process.decision.test;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.StartProcessInfo;
import com.bstek.uflo.service.TaskService;
import com.bstek.uflo.utils.EnvironmentUtils;
import com.ifrabbit.nk.constant.Constant;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.ProblemService;
import com.ifrabbit.nk.express.utils.TimeUtil;
import com.ifrabbit.nk.usercenter.domain.Staff;
import com.ifrabbit.nk.usercenter.domain.StaffDTO;
import com.ifrabbit.nk.usercenter.service.StaffService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import ir.nymph.date.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("初始化电话数据")
public class SetProblemDecision implements DecisionHandler {

    @Autowired
    private StaffService staffService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserReportService userReportService;

    @Autowired
    private DealinfoService dealinfoService;

    @Autowired
    private ProcessService processService;
    private TaskService taskService;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        Problem problem = new Problem();
        problem.setProblemparts_type(1);
        problem.setProblemparts_newreceivephone("13738139702");
        problem.setProblemparts_sendphone("13738139702");
        problem.setProblemparts_receivephone("13738139702");
        problem.setProcessInstanceId(processInstance.getId());
        problem.setProblemparts_expressnumber(261325504974L);
        problem.setProblemparts_expresstype(0);
        String staffName = problem.getProblemparts_staffname();
        String inSideItem =  problem.getProblemparts_insideitem();
        String sendName = problem.getProblemparts_sendname();
        String sendPhone = problem.getProblemparts_sendphone();
        String sendAdd = problem.getProblemparts_sendaddress();
        Staff staff = staffService.getByUsername("admin");
        String result = CreateProblemFile(problem,staff);//创建问题件

        processInstance.setBusinessId(problem.getId()+"");


        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(problem.getProcessInstanceId())
                .addTaskState(TaskState.Created).addTaskState(TaskState.Ready)
                .assignee(EnvironmentUtils.getEnvironment().getLoginUser())
                .businessId(String.valueOf(problem.getId())).list().stream()
                .map(t -> {
                    Task task = new Task();
                    task.setId(t.getId());
                    task.setNodeName(t.getNodeName());
                    task.setAssignee(t.getAssignee());
                    task.setTaskName(t.getTaskName());
                    task.setUrl(t.getUrl());
                    task.setBusinessId(t.getBusinessId());
                    return task;
                }).collect(Collectors.toList());
        return "是";
    }

    //创建问题件
    private String CreateProblemFile(Problem problem,Staff staff) {
        problem.setProblemparts_createdate(TimeUtil.getTime(new Date()));//创建时间
        problem.setProblemparts_source(Constant.SYSTEM);//系统10
        problem.setProblemparts_uncollecteddays(0);
        problem.setProblemparts_callrecipientscount(0);
        problem.setProblemparts_callcompanycount(0);
        problem.setProblemparts_dutystate(0);
        problem.setProblemparts_obtainstate(0);
        problem.setProblemparts_tasktype(0);
        problemService.insert(problem);
        //开始启动流程
        Long id = problem.getId();
        insertDealInfo (id,"收件人姓名:"+problem.getProblemparts_receivename()+" 电话:"+problem.getProblemparts_receivephone()+" 地址:"+problem.getProblemparts_receiveaddress(),problem.getProblemparts_type());
        insertDealInfo (id,"内物:"+ problem.getProblemparts_insideitem(),problem.getProblemparts_type());
        if(problem.getProblemparts_type() == 3){
            insertDealInfo(id,"新收件人姓名:"+problem.getProblemparts_newreceivename()+" 新收件人电话:"+problem.getProblemparts_newreceivephone()+" 新收件人地址:"+problem.getProblemparts_newreceiveaddress(),problem.getProblemparts_type());
        }
        StartProcessInfo info = new StartProcessInfo(staff.getStaffUsername());
        info.setBusinessId(String.valueOf(id));
        return ReportProblem(problem, info,staff);
    }



    private String ReportProblem(Problem problem, StartProcessInfo info,Staff staff) {
        /*String qswsdlc = userReportService.getParameter("QSWSDLC");
        String pslc= userReportService.getParameter("PSLC");
        String gdzlc = userReportService.getParameter("GDZLC");
        String cdlc = userReportService.getParameter("CDLC");
        String thlc = userReportService.getParameter("THLC");*/

        problem.setProblemparts_staffid(Integer.parseInt(staff.getId()));
        problemService.saveIgnoreNull(problem);
        return "effective";
    }



    public void insertDealInfo (Long business_id_,String option,Integer problemPartsType) {
        Dealinfo dealinfo = new Dealinfo();
        dealinfo.setAppdealProblemid(business_id_);
        dealinfo.setAppdealDealstate(1);//state 0:未处理 1:已处理
        dealinfo.setAppdealContent(option);
//        dealinfo.setAppdealDealtype(0);
        dealinfo.setAppdealProblemtype(problemPartsType);
        dealinfo.setAppdealDealername("zhinengkefu");
        dealinfo.setAppdealVarparamb("0");
//        dealinfo.setAppdealRecorderid(taskId);
        dealinfo.setAppdealResult(10);
        dealinfo.setAppdealResulttext("初始记录添加");
        dealinfo.setAppdealDealcreatedate(new DateTime().toTimestamp());
        dealinfoService.insert(dealinfo);
    }
}
