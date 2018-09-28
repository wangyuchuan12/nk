package com.ifrabbit.nk.flow.process.action;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.model.task.TaskType;
import com.bstek.uflo.process.handler.ActionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/4/25
 * Time:10:43
 */

/**
 * 发送人工智能工单
 */
@Component("发送人工智能工单")
public class CusServiceOrder implements ActionHandler {
    private static Logger logger = LoggerFactory.getLogger(CusServiceOrder.class);

    @Override
    public void handle(ProcessInstance processInstance, Context context) {
        System.out.println();
        System.out.println();
        System.out.println();
        logger.info("++++++++++++++++++++++++发送人工智能工单++++++++++++++++++++++");

        Task task = new Task();
        task.setSubject(null);
        task.setProgress(null);
        task.setCountersignCount(0);
        task.setProcessId(processInstance.getProcessId());
        task.setDescription("派发工单");
        task.setPriority(null);
        task.setPrevTask("未知");
        task.setPrevState(null);
        task.setOpinion(null);
        task.setOwner(processInstance.getPromoter());
        task.setEndDate(null);
        task.setDuedate(null);
        task.setDueActionDate(null);
        task.setDateUnit(null);
        task.setNodeName("发送智能工单");
        task.setType(TaskType.Normal);
        task.setState(TaskState.InProgress);
        task.setUrl(null);
        task.setBusinessId(processInstance.getBusinessId());
        task.setTaskName(processInstance.getCurrentTask());
        task.setAssignee(processInstance.getPromoter());
        task.setCreateDate(new Date());
        task.setProcessInstanceId(processInstance.getId());
        task.setRootProcessInstanceId(processInstance.getRootId());
        task.setId(41230);
    }
}
