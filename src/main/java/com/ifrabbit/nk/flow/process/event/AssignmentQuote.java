package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component("处理人置为问题件上报账号")
public class AssignmentQuote implements AssignmentHandler {
    @Autowired
    private ProblemService problemService;

    @Override
    public Collection<String> handle(TaskNode taskNode, ProcessInstance processInstance, Context context)  {
        long businessId = Long.valueOf(processInstance.getBusinessId());
        Problem problem = problemService.get(businessId);
        List list = new ArrayList();
        list.add(problem.getProblemparts_staffname());
        taskNode.setAssignees(list);
        return list;
    }
}
