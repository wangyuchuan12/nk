package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/5/24
 * Time:11:18
 */
@Component("处理人置为智能客服")
public class AssignmentB implements AssignmentHandler {
    @Override
    public Collection<String> handle(TaskNode taskNode, ProcessInstance processInstance, Context context)  {
//        if (processInstance.getCurrentNode().equals("上级A电话")){
//            context.getProcessService().saveProcessVariable(processInstance.getId(),"sonid",processInstance.getId());
//        }
        List list = new ArrayList();
        list.add("zhinengkefu");
        taskNode.setAssignees(list);
        return list;
    }
}
