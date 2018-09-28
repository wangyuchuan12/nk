package com.ifrabbit.nk.flow.process.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.domain.ProblemDTO;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.CallDetailService;
import com.ifrabbit.nk.express.service.ProblemService;
import com.ifrabbit.nk.flow.service.VariableService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/4/25
 * Time:15:12
 */

/**
 * 是否未收到天数超过3天
 * 没超过3天继续呼叫，1天一次
 */
@Component("是否连续拨打网点超过三天")
public class CallNetWorkMore72 implements DecisionHandler {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CallNetWorkMore72.class);

    @Autowired
    private ProblemService problemService;

    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        logger.info("判断未收到的天数是否超过3天.............................");
        Long businessId = Long.valueOf(processInstance.getBusinessId());
        Problem problem = problemService.get(businessId);
        Integer uncollectedDays = problem.getProblemparts_uncollecteddays();
        String outnumber=(uncollectedDays>=2)?"是":"否";
        if("否".equals(outnumber)){
            //如果未超过3天则保存累计值
            updateProblem(businessId,uncollectedDays+1);
        }
        return outnumber;
    }

    public void updateProblem (Long business_id_,Integer times) {
        Problem problem = new Problem();
        problem.setId(business_id_);
        problem.setProblemparts_uncollecteddays(times);
        problemService.updateIgnore(problem);
    }

//    public void updateProblemNew(Long business_id_, Integer nodeCallTimes,Integer DutyState,Integer ObtainState,Integer callcompanycount, Integer tag) {
//        Problem problem = new Problem();
//        problem.setId(business_id_);
//        if (null == tag) {
//            if( nodeCallTimes != -1)  problem.setProblemparts_callrecipientscount(nodeCallTimes);
//            if( DutyState != -1) problem.setProblemparts_dutystate(DutyState);
//            if( ObtainState != -1) problem.setProblemparts_obtainstate(ObtainState);
//            problem.setProblemparts_callcompanycount(callcompanycount);
//        } else {
//            problem.setProblemparts_callcompanycount(tag);
//        }
//        problemService.updateIgnore(problem);
//    }
}
