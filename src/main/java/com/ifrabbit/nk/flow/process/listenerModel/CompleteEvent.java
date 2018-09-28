package com.ifrabbit.nk.flow.process.listenerModel;

import org.springframework.context.ApplicationEvent;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/7
 * Time:20:53
 */
public class CompleteEvent extends ApplicationEvent {
    public CompleteEvent(Object source) {
        super(source);
    }

    private long taskId;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
