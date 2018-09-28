package com.ifrabbit.nk.flow.process.action;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.variable.Variable;
import com.bstek.uflo.process.listener.TaskListener;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.service.ProcessService;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.service.*;
import com.ifrabbit.nk.mq.producer.CallBackProducer;
import com.ifrabbit.nk.mq.queue.ExpressSender;
import com.ifrabbit.nk.mq.queue.PhoneSender;
import com.ifrabbit.nk.usercenter.domain.*;
import com.ifrabbit.nk.usercenter.service.JobListService;
import com.ifrabbit.nk.usercenter.service.StaffService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/3
 * Time:10:23
 */
@Component("监听任何任务")
public class TaskListenerOfMission implements TaskListener {
    @Autowired
    ProblemService problemService;
    @Autowired
    StaffService staffService;
    @Autowired
    CallBackProducer callBackProducer;
    @Autowired
    DealinfoService dealinfoService;
    @Autowired
    TableInfoService tableInfoService;
    @Autowired
    private ExpressSender expressSender;
    @Autowired
    private PhoneSender phoneSender;
    @Autowired
    ProcessService processService;
    @Autowired
    private DealTypeService dealTypeService;
    @Autowired
    private JobListService jobListService;

    @Override
    public boolean beforeTaskCreate(Context context, ProcessInstance processInstance, TaskNode taskNode) {
        return false;
    }

    @Override
    public void onTaskCreate(Context context, Task task) {
        //3个需要插入数据的实体类
        JobList jobList = new JobList();
        Dealinfo dealinfo = new Dealinfo();

        //获取businessID,用于查找问题件businessID = problemID
        Long businessId = Long.valueOf(task.getBusinessId());
        //任务的ID
        long taskId = task.getId();
        //实例ID
        long processInstanceId = task.getProcessInstanceId();
        //流程ID
        int processId =(int) task.getProcessId();
        //节点名
        String nodeName = task.getNodeName();

        //获取当前时间点，用于表单生成时间
        Date date = new Date();
        Timestamp createDate = new Timestamp(date.getTime());

        //根据businessID获取到问题件实体数据
        Problem problem = problemService.get(businessId);
        Integer problempartsType = problem.getProblemparts_type();
        String problempartsSendphone = problem.getProblemparts_sendphone();
        String problempartsExpressnumber = String.valueOf(problem.getProblemparts_expressnumber());
        Long problemID = problem.getId();

        //根据processID来获取ufloProcess实体
        ProcessDefinition processById = processService.getProcessById(processId);
        String ufloProcessName = processById.getName();




        /**
         * 根据taskName.processId来获取dealtype表里面的dealtype字段
         */
        DealTypeDTO dealTypeDTO = new DealTypeDTO();
        //设置节点名
        dealTypeDTO.setDealDealName(nodeName);
        dealTypeDTO.setDealTabTypeId(task.getProcessId());
        DealType dealTypeServiceOne = dealTypeService.findOne(dealTypeDTO);
        //获取dealType
        Integer dealType = Integer.valueOf(dealTypeServiceOne.getDealType());
        String dealDealName = dealTypeServiceOne.getDealDealName();

        String value = "";
        //取工作流上的参数值
        List<Variable> processVariables = context.getProcessService().getProcessVariables(processInstanceId);
        int size = processVariables.size();
        String str = "";

        for (int i = 0; i < size; i++) {
            Variable variable = processVariables.get(i);
            if(variable.getKey().contains(nodeName)){
                String[] split = variable.getKey().split("-");
                str += split[1] + "-" + variable.getValue()+ "/";
            }
        }
        //判断是否为空，去掉最后一个'/'
        if(StringUtils.isNotBlank(str)){
            value = str.substring(0, str.length() - 1);
        }

        //插入dealinfo数据
        dealinfo.setAppdealProblemid(problemID);//问题件表的主键ID
        dealinfo.setAppdealTabletype(processId);//表单类型，processID
        dealinfo.setAppdealTabletypename(ufloProcessName);//processID对应的名字
        dealinfo.setAppdealDealtype(dealType);//操作类型
        dealinfo.setAppdealDealname(dealDealName);//流程环节名称
        dealinfo.setAppdealTaskid(taskId);//工作流流程环节ID
        dealinfo.setAppdealDealstate(0);//操作状态，0未处理，1已完成，2处理中
        dealinfo.setAppdealResult(0);//处理结果的整数值。不设置会报错
        dealinfo.setAppdealResulttext("");//处理结果。不设置会报错
        dealinfo.setAppdealDealername("null");//处理人。不设置会报错
        dealinfo.setAppdealDealcreatedate(createDate);//生成表的时间
        dealinfo.setAppdealRecord(value);//在当前监听获得的值
        dealinfo.setAppdealProblemtype(problempartsType);

        try{
            dealinfoService.insert(dealinfo);
            //插入tableinfo数据

            TableInfoDTO tableInfoDTO = new TableInfoDTO();
            tableInfoDTO.setTableinfo_tabid(processInstanceId);
            TableInfo tableInfo = tableInfoService.findOne(tableInfoDTO);
            if(tableInfo != null){
                tableInfo.setTableinfo_bussinessid(businessId);
                tableInfo.setTableinfo_problemid(problemID);//问题件表的主键ID
                tableInfo.setTableinfo_dealid(dealinfo.getId());//AppDeal_ID对应,工作流流程环节ID
                tableInfo.setTableinfo_tabletype(processId);//表单类型，processID
                tableInfo.setTableInfo_tabletypename(ufloProcessName);
                tableInfo.setTableinfo_dealtype(dealType);//操作类型
                tableInfo.setTableinfo_dealname(dealDealName);//流程环节名称
                tableInfo.setTableinfo_dealcreatedate(createDate);//表单生成时间
                tableInfo.setTableinfo_result(0);//处理结果的整数值。不设置会报错
                tableInfo.setTableinfo_dealername("null");//处理人名字
                tableInfo.setTableinfo_record(value);//在当前监听获得的值
                tableInfo.setTableinfo_problemtype(problempartsType);//问题件类型
                tableInfo.setTableinfo_dealstate(1);//处理状态为1,0为已结束
                tableInfoService.updateIgnore(tableInfo);

                dealinfo.setAppdealTabid(tableInfo.getId());//tableInfo表主键ID
                jobList.setJoblistTabid(tableInfo.getId());//tableInfo表主键ID
                jobList.setJoblistUptabid(tableInfo.getTableinfo_uptabid());//父流程的instanceID
            }else{
                TableInfo tableInfo1 = new TableInfo();
                tableInfo1.setTableinfo_bussinessid(businessId);
                tableInfo1.setTableinfo_problemid(problemID);//问题件表的主键ID
                tableInfo1.setTableinfo_dealid(dealinfo.getId());//AppDeal_ID对应,工作流流程环节ID
                tableInfo1.setTableinfo_tabletype(processId);//表单类型，processID
                tableInfo1.setTableInfo_tabletypename(ufloProcessName);
                tableInfo1.setTableinfo_dealtype(dealType);//操作类型
                tableInfo1.setTableinfo_dealname(dealDealName);//流程环节名称
                tableInfo1.setTableinfo_dealcreatedate(createDate);//表单生成时间
                tableInfo1.setTableinfo_result(0);//处理结果的整数值。不设置会报错
                tableInfo1.setTableinfo_dealername("null");//处理人名字
                tableInfo1.setTableinfo_record(value);//在当前监听获得的值
                tableInfo1.setTableinfo_problemtype(problempartsType);//问题件类型
                tableInfo1.setTableinfo_dealstate(1);//处理状态为1,0为已结束
                tableInfoService.insert(tableInfo1);

                dealinfo.setAppdealTabid(tableInfo1.getId());//tableInfo表主键ID
                jobList.setJoblistTabid(tableInfo1.getId());//tableInfo表主键ID
                jobList.setJoblistUptabid(tableInfo1.getTableinfo_uptabid());//父流程的instanceID
            }

            dealinfoService.updateIgnore(dealinfo);

            //插入joblist数据
            jobList.setJoblistProblemid(problemID);//问题件表的主键ID
            jobList.setJoblistExpressnumber(problempartsExpressnumber);//运单号
            jobList.setJoblistDealid(dealinfo.getId());//dealInfo表的主键ID
            jobList.setJoblistTabletype(processId);//对应uflotask的processID
            jobList.setJoblistTabletypename(ufloProcessName);//对应processID的名称
            jobList.setJoblistDealname(dealDealName);//操作名称 流程环节名称
            jobList.setJoblistState(0);//代办工作状态 0=未处理，1=已处理，2=处理中，3=异常错误终止
            jobList.setJoblistTime(createDate);//代办产生的时间
            jobList.setJoblistDealtype(dealType);//操作类型
            jobList.setJoblistSubmitterphone(problempartsSendphone);//投诉人电话
            jobList.setJoblistRecord(value);//在当前监听获得的值
            jobList.setJoblistResult(0);//表单处理结果
            jobList.setJoblistProblemtype(problempartsType);//问题件类型
            jobList.setJoblistOwner("null");//设置拥有人为null
            jobList.setJoblistTaskid(taskId);//任务ID

            jobListService.insert(jobList);
            //如果是人工任务或者是复制任务，就不用发送mq,  101和201 是物流和电话的标记
            switch (dealType){
                case 101:
                    phoneSender.phoneSender(jobList);//发送电话专属mq
                    break;
                case 201:
                    expressSender.expressSender(jobList);//发送物流专属mq
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskComplete(Context context, Task task) {
    }
}
