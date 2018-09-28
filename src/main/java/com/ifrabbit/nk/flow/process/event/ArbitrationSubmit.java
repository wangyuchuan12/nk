package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("仲裁提交")
@Slf4j
public class ArbitrationSubmit implements NodeEventHandler {

    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {
        log.info("enter " + processInstance.getId() + "[" + node.getName() + "].");
        System.out.println("仲裁申请单填写");
    }

    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {
        log.info("leave " + processInstance.getId() + "[" + node.getName() + "].");
        System.out.println("OVER");
    }
}
