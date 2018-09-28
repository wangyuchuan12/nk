package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.process.node.UserData;
import com.ifrabbit.nk.express.utils.TimeUtil;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/5/24
 * Time:11:18
 */
@Component("处理人置为null")
public class Assignment implements AssignmentHandler {
    @Override
    public Collection<String> handle(TaskNode taskNode, ProcessInstance processInstance, Context context)  {
        List list = new ArrayList();
        list.add("null");
        taskNode.setAssignees(list);
        return list;
    }
}
