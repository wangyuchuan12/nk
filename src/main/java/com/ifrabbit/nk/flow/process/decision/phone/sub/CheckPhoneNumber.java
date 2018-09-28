package com.ifrabbit.nk.flow.process.decision.phone.sub;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
import com.ifrabbit.nk.express.service.ProblemService;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: WangYan
 * @Date: 2018/9/16 11:30
 * @Description:
 */
@Component("拨打网点电话个数判断")
public class CheckPhoneNumber implements DecisionHandler {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    private CompanyService companyService;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        Problem problem = problemService.get(Long.valueOf(processInstance.getBusinessId()));
        ExpressInfoDetail expressInfoDetail = expressInfoDetailService.findByType(3, String.valueOf(problem.getProblemparts_expressnumber()));
        Company company = companyService.get(Long.valueOf(expressInfoDetail.getBegin_companyid()));
        Long instanceId = processInstance.getId();
        String t1 = company.getCompany_tel();
        String t2 = company.getCompany_tel_2();
        String t3 = company.getCompany_tel_3();
        int num = 0;
        if (StringUtils.isNotBlank(t1)&& !t1.equals("无")){num++;}
        if (StringUtils.isNotBlank(t2)){num++;}
        if (StringUtils.isNotBlank(t3)){num++;}
        switch (num){
            case 0:
                return "无电话拨打上级";
            case 1:
                context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第一个电话（C）-count",
                        "C1");
                context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第一个电话（C）-amount",
                        "A1");
                context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第一个电话（C）-modal",
                        "M2");
                return "一个电话";
            case 2:
                context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第一个电话（C）-count",
                        "C1");
                context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第一个电话（C）-amount",
                        "A1");
                context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第一个电话（C）-modal",
                        "M2");
                return "两个电话";
            case 3:
                context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第一个电话（C）-count",
                        "C1");
                context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第一个电话（C）-amount",
                        "A1");
                context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第一个电话（C）-modal",
                        "M2");
                return "三个电话";
        }
        return null;
    }
}
