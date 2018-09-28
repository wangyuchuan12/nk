package com.ifrabbit.nk.flow.process.decision.phone.sub;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: WangYan
 * @Date: 2018/9/14 20:24
 * @Description:
 */
@Component("拨打收件人子流程放参数")
public class SetParamValue implements DecisionHandler {
    @Autowired
    private ProblemService problemService;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        String problemId = processInstance.getBusinessId();
        Problem problem = problemService.get(Long.valueOf(problemId));
        Integer type = problem.getProblemparts_type();
        switch (type){
            case 1: case 3:case 4:case 5:
                context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第一次拨打收件人电话-day",
                        "D1");
                context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第一次拨打收件人电话-count",
                        "C1");
                context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第一次拨打收件人电话-modal",
                        "M1");
                break;
            case 2:
                context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第一次拨打收件人电话-day",
                        "D1");
                context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第一次拨打收件人电话-count",
                        "C1");
                context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第一次拨打收件人电话-modal",
                        "M11");
                break;
        }
        return "是";
    }
}
