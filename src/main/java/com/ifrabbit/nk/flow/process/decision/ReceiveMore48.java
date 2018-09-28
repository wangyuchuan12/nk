package com.ifrabbit.nk.flow.process.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.service.ProblemService;
import com.ifrabbit.nk.flow.service.VariableService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/8/2
 * Time:10:30
 */
@Component("未收到天数是否超过2天")
public class ReceiveMore48 implements DecisionHandler {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReceiveMore48.class);
    @Autowired
    private VariableService variableService;

    @Autowired
    private ProblemService problemService;

    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        logger.info("判断未收到的天数是否超过3天.............................");
        Integer uncollectedDays = Integer.parseInt(variableService.getPhoneVariable(processInstance,"uncollectedDays"));
        Integer obtainState = Integer.parseInt(variableService.getPhoneVariable(processInstance,"obtainState"));
        Long business_id_ = Long.valueOf(variableService.getPhoneVariable(processInstance,"business_id_"));
        String outnumber=(uncollectedDays>=1)?"是":"否";
        if("否".equals(outnumber)){
            //如果未超过3天则保存累计值
            updateProblem(business_id_,uncollectedDays+1);
            updateProblemNew(business_id_, 0, 0,obtainState+1,2,null);
        }
        return outnumber;
    }

    public void updateProblem (Long business_id_,Integer times) {
        Problem problem = new Problem();
        problem.setId(business_id_);
        problem.setProblemparts_uncollecteddays(times);
        problemService.updateIgnore(problem);
    }

    public void updateProblemNew(Long business_id_, Integer nodeCallTimes,Integer DutyState,Integer ObtainState,Integer callcompanycount, Integer tag) {
        Problem problem = new Problem();
        problem.setId(business_id_);
        if (null == tag) {
            if( nodeCallTimes != -1)  problem.setProblemparts_callrecipientscount(nodeCallTimes);
            if( DutyState != -1) problem.setProblemparts_dutystate(DutyState);
            if( ObtainState != -1) problem.setProblemparts_obtainstate(ObtainState);
            problem.setProblemparts_callcompanycount(callcompanycount);
        } else {
            problem.setProblemparts_callcompanycount(tag);
        }
        problemService.updateIgnore(problem);
    }
}

