package com.ifrabbit.nk.flow.process.decision.express.change.param;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/19
 * Time:15:51
 */
@Component("改地址-放入改地址物流参数(A1)")
public class PutExpressParamsOfChangeA1 implements DecisionHandler {

    @Override
    public String handle(Context context, ProcessInstance processInstance) {

            context.getProcessService().saveProcessVariable(processInstance.getId(), "改地址物流解析(A1)-day",
                    "D2");
            context.getProcessService().saveProcessVariable(processInstance.getId(), "改地址物流解析(A1)-modal",
                    "M3");

            return "是";
    }
}

