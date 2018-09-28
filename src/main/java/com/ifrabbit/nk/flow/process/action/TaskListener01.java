package com.ifrabbit.nk.flow.process.action;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.listener.TaskListener;
import com.bstek.uflo.process.node.TaskNode;
import com.ifrabbit.nk.flow.process.utils.TaskUtil;
import org.springframework.stereotype.Component;

/**
 * @Auther: WangYan
 * @Date: 2018/7/4 18:10
 * @Description:
 */
@Component("监听")
public class TaskListener01 implements TaskListener {

    @Override
    public boolean beforeTaskCreate(Context context, ProcessInstance processInstance, TaskNode taskNode) {
        return false;
    }

    @Override
    public void onTaskCreate(Context context, Task task) {
        TaskUtil.inserteDate(task);
        System.out.println("=========================onTaskCreate====================");
    }

    @Override
    public void onTaskComplete(Context context, Task task) {
    }
}
