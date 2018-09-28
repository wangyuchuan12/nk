package com.ifrabbit.nk.flow.process.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.flow.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("是否可以继续打网点")
public class Recall implements DecisionHandler {
    @Autowired
    private VariableService variableService;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
//        Object u_recall = context.getProcessService().getProcessVariable("recall", processInstance.getId());
        Long sonId = (Long) context.getProcessService().getProcessVariable("sonId", processInstance.getId());
        String u_recall = variableService.getPhoneVariablebyid(sonId, "U_recall");
        if ("1".equals(u_recall)){
            return "是";
        }else {
            return  "否";
        }

    }
}
