
package com.ifrabbit.nk.flow.process.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import org.springframework.stereotype.Component;

@Component("放参数" )
public class InParamet implements DecisionHandler{

    @Override
    public String handle(Context context, ProcessInstance processInstance) {
            context.getProcessService().saveProcessVariable(processInstance.getId(), "物流解析-day",
                "D1");
            context.getProcessService().saveProcessVariable(processInstance.getId(), "物流解析-modal",
                    "M1");
        return "是";
    }
}

