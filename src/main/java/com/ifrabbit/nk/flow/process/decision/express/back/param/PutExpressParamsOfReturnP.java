package com.ifrabbit.nk.flow.process.decision.express.back.param;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/19
 * Time:15:53
 */
@Component("退回-放入退回物流参数(P)")
public class PutExpressParamsOfReturnP implements DecisionHandler {

    @Override
    public String handle(Context context, ProcessInstance processInstance) {

            context.getProcessService().saveProcessVariable(processInstance.getId(), "退回物流解析(P1)-day",
                    "D2");
            context.getProcessService().saveProcessVariable(processInstance.getId(), "退回物流解析(P1)-modal",
                    "M3");
            return "是";
    }
}

