package com.ifrabbit.nk.flow.process.action;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.ActionHandler;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.domain.ProblemDTO;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.flow.process.utils.HandlerUtil;
import com.ifrabbit.nk.usercenter.service.UserReportService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @Auther: WangYan
 * @Date: 2018/4/18 14:49
 * @Description: 智能客服拨打收件人电话执行程序
 */

@Component("呼叫收件人")
public class CallReceiver implements ActionHandler {

    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private UserReportService userReportService;

    @Override
    public void handle(ProcessInstance processInstance, Context context) {
        ProblemDTO cond = new ProblemDTO();
        cond.setId(Long.valueOf(processInstance.getBusinessId()));
        Problem problem = problemRepository.findOne(cond);
        String receiver = problem.getProblemparts_receivephone();
        String siteid = "";
        String tempTime = userReportService.getParameter("cbsjjg");
        Long time;
        if (null == tempTime){
            time = 24 * 60 * 60 * 1000L;
        }else {
            time = Long.valueOf(tempTime)*1000;
        }
        switch (problem.getProblemparts_type()) {
            case 1:
                siteid = "10000204";
                break;
            case 2:
                siteid = "10000183";
                break;
            case 3:
                if("智能语音拨打收件人电话".equals(processInstance.getCurrentNode())){
                    siteid = "10000268";
                }else{
                    siteid = "10000224";
                }
                break;
            case 4:
                siteid = "10000216";
                break;
            case 5:
                siteid = "10000224";
                break;
        }
        //累计循环拨打的次数
        Map<String, Object> vars = new HashMap<>();
        Integer uncollecteddays = (Integer) context.getProcessService().getProcessVariable("uncollecteddays", processInstance.getId());
        if (null == uncollecteddays) {
            vars.put("uncollecteddays", 1);
        } else {
            vars.put("uncollecteddays", uncollecteddays + 1);
        }
        context.getProcessService().saveProcessVariables(processInstance.getId(), vars);

        int i = 0 ;
        while (i < 3){
            boolean check = HandlerUtil.text01(processInstance, context, receiver, problem.getProblemparts_receivename(), problem.getProblemparts_insideitem(),
                    siteid, problem.getProblemparts_expressnumber().toString(),0);
            i++;
            if (check){
                return;
            }else {
                if(i==3){
                    return;
                }
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
