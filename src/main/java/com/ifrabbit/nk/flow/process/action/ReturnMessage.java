package com.ifrabbit.nk.flow.process.action;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.model.task.TaskType;
import com.bstek.uflo.process.handler.ActionHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("向快送哥系统提交结果反馈")
public class ReturnMessage implements ActionHandler {
    @Override
    public void handle(ProcessInstance processInstance, Context context) {
        //将流程处理结果写入系统内,流程结束

        Task task = new Task();
        task.setSubject(null);
        task.setProgress(null);
        task.setCountersignCount(0);
        task.setProcessId(processInstance.getProcessId());
        task.setDescription("向快送哥系统提交结果反馈");
        task.setPriority(null);
        task.setPrevTask("未知");
        task.setPrevState(null);
        task.setOpinion(null);
        task.setOwner(processInstance.getPromoter());
        task.setEndDate(null);
        task.setDuedate(null);
        task.setDueActionDate(null);
        task.setDateUnit(null);
        task.setNodeName("提交反馈结果");
        task.setType(TaskType.Normal);
        task.setState(TaskState.InProgress);
        task.setUrl(null);
        task.setBusinessId(processInstance.getBusinessId());
        task.setTaskName(processInstance.getCurrentTask());
        task.setAssignee(processInstance.getPromoter());
        task.setCreateDate(new Date());
        task.setProcessInstanceId(processInstance.getId());
        task.setRootProcessInstanceId(processInstance.getRootId());
        task.setId(41330);
    }
}
