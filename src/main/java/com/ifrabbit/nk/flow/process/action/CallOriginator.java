package com.ifrabbit.nk.flow.process.action;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.ActionHandler;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.domain.ProblemDTO;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.flow.process.utils.HandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: WangYan
 * @Date: 2018/4/18 14:46
 * @Description: 智能客服拨打问题件发起人执行程序
 */
@Component("呼叫发起人")
public class CallOriginator implements ActionHandler {
    @Autowired
    private ProblemRepository problemRepository;
    @Override
    public void handle(ProcessInstance processInstance, Context context) {
        ProblemDTO cond = new ProblemDTO();
        cond.setId(Long.valueOf(processInstance.getBusinessId()));
        Problem problem=  problemRepository.findOne(cond);
        String originator= problem.getProblemparts_submitterphone();
        String siteid = new String();
        switch (problem.getProblemparts_type()){
            case 1: siteid="10000192";
                break;
            case 2:
               if (processInstance.getCurrentNode().equals("智能语音拨打发起人电话")){
                   siteid="10000192";
               }else {
                   siteid="10000248";
               }
                break;
            case 3: siteid="10000218";
                break;
            case 4: siteid="10000192";
                break;
            case 5: siteid="10000192";
                break;
              }
            HandlerUtil.text01(processInstance,context,originator,problem.getProblemparts_submittername(),null,siteid,
                    problem.getProblemparts_expressnumber().toString(),3);
    }
}