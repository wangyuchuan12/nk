/**
 * Copyright (C), 2018-2018, KSG有限公司
 * FileName: InParametA
 * Author:
 * Date:     2018-09-10 10:10
 * Description:
 * History:
 */


package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("放参数1")
public class InParametA implements NodeEventHandler{

    @Autowired
    MessagesService messagesService;
    @Autowired
    DealinfoService dealinfoService;

    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {

        context.getProcessService().saveProcessVariable(processInstance.getId(), "收件人",
                "interval-T1/call_index-P1/call_num-C2/ivr_id-R1/content_id-S1");

    }

    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {
    }

}