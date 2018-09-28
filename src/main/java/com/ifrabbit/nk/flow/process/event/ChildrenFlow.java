package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import com.bstek.uflo.process.node.NodeType;
import com.bstek.uflo.service.IdentityService;
import com.bstek.uflo.service.ProcessService;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.domain.ProblemDTO;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.flow.service.TaskService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/5/16
 * Time:9:37
 */
@Component("测试子流程")

public class ChildrenFlow implements NodeEventHandler {
    @Autowired
    private ProblemRepository problemRepository;

    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {
        ProblemDTO cond = new ProblemDTO();
        cond.setId(Long.valueOf(processInstance.getBusinessId()));
        Problem problem = problemRepository.findOne(cond);
        String problemparts_expressnumber = String.valueOf(problem.getProblemparts_expressnumber());
        problem.setProblemparts_type(4);//设置为4，走签收未收到


        System.out.println("+++++++++++++++++++++++++++++++++++++++这是子流程的进入操作+++++++++++++++++++++++++++++++++++++++");
    }

    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++这是子流程的离开操作+++++++++++++++++++++++++++++++++++++++");
    }
}
