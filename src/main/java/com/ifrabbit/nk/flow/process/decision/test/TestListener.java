package com.ifrabbit.nk.flow.process.decision.test;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.listener.TaskListener;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.service.TaskOpinion;
import com.bstek.uflo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component("测试任务")
public class TestListener implements TaskListener {

    @Qualifier("uflo.taskService")
    @Autowired
    private TaskService decorator;

    @Override
    public boolean beforeTaskCreate(Context context, ProcessInstance processInstance, TaskNode taskNode) {
        return false;
    }

    @Override
    public void onTaskCreate(Context context, Task task) {
        Long taskId = task.getId();
        TaskOpinion taskOpinion = new TaskOpinion("");
        decorator.start(taskId);
        decorator.complete(taskId, taskOpinion);
    }

    @Override
    public void onTaskComplete(Context context, Task task) {

    }
}
