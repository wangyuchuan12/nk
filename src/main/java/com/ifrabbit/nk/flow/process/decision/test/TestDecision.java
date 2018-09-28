package com.ifrabbit.nk.flow.process.decision.test;


import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import org.springframework.stereotype.Component;

@Component("testDecision")
public class TestDecision implements DecisionHandler {
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        context.getProcessService().saveProcessVariable(processInstance.getId(),"拨打收件人-count",
                "D1");
        context.getProcessService().saveProcessVariable(processInstance.getId(),"拨打收件人-seq",
                "C1");
        context.getProcessService().saveProcessVariable(processInstance.getId(),"拨打收件人-modal",
                "M1");
        return "是";
    }
}
