package com.ifrabbit.nk.flow.process.decision.test;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import org.springframework.stereotype.Component;

@Component("testExDecision")
public class TestExDecision  implements DecisionHandler {
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        context.getProcessService().saveProcessVariable(processInstance.getId(),"解析物流-count",
                "D1");
        context.getProcessService().saveProcessVariable(processInstance.getId(),"解析物流-seq",
                "M1");
        return "是";
    }
}
