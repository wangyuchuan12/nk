package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import com.ifrabbit.nk.express.domain.MessagesDTO;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.domain.ProblemDTO;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.MessagesService;
import com.ifrabbit.nk.express.service.ProblemService;
import ir.nymph.date.DateTime;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("1111")
public class MessageSend1 implements NodeEventHandler {
    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    MessagesService messagesService;
    @Autowired
    DealinfoService dealinfoService;

    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {
        context.getProcessService().getProcessVariable("打网点", processInstance);
    }

    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {

    }

}
