package com.ifrabbit.nk.flow.process.decision.express.general.param;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/19
 * Time:15:56
 */
@Component("通用-放入及时查询物流的参数")
public class PutExpressParamsOfTimely implements DecisionHandler {

    @Override
    public String handle(Context context, ProcessInstance processInstance) {

        context.getProcessService().saveProcessVariable(processInstance.getId(), "物流解析(A)-day",
                "D1");
        context.getProcessService().saveProcessVariable(processInstance.getId(), "物流解析(A)-modal",
                "M1");
        return "是";
    }
}
