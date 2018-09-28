package com.ifrabbit.nk.flow.process.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.flow.process.utils.HandlerUtil;
import org.springframework.stereotype.Component;


@Component("测试流程第一天第一次拨打收件人电话参数设置")
public class SetProcessVariableTestCall implements DecisionHandler {
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        context.getProcessService().saveProcessVariable(processInstance.getId(),"第一次拨打网点第一个电话-count",
                "C1");
        context.getProcessService().saveProcessVariable(processInstance.getId(),"第一次拨打网点第一个电话-seq",
                "A1");
        context.getProcessService().saveProcessVariable(processInstance.getId(),"第一次拨打网点第一个电话-modal",
                "M2");
        return "是";
    }
}
