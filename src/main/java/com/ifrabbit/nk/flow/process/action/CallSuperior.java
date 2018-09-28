package com.ifrabbit.nk.flow.process.action;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.ActionHandler;
import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.domain.ProblemDTO;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
import com.ifrabbit.nk.flow.process.utils.HandlerUtil;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: WangYan
 * @Date: 2018/4/18 14:56
 * @Description: 智能客服呼叫上级网点执行程序
 */
@Component("呼叫上级网点,中心等")
public class CallSuperior implements ActionHandler {
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    private UserReportService userReportService;

    @Override
    public void handle(ProcessInstance processInstance, Context context) {
        Problem problem = this.getExpressNumber(processInstance.getBusinessId());
        String expressNumber = problem.getProblemparts_expressnumber().toString();
        Company company = this.getBody(3, expressNumber);
        Company parentCom = this.getCom(company.getCompany_parentid());
        Map<String, Object> vars = new HashMap<>();
        String number = null;
        String siteid = null;
        String message = null;
        String name = null;
        switch (processInstance.getCurrentNode()) {
            case "上级网点":
                number = parentCom.getCompany_linktel();
                siteid = "10000197";
                message = parentCom.getCompany_name() + "网点您好,这里是快送哥" + company.getCompany_name() + "网点联系不上,请确认";
                name = parentCom.getCompany_name();
                vars.put("company", 1);
                break;
            case "上级中心":
                Company parentCenter = this.getCom(parentCom.getCompany_parentid());
                message = parentCenter.getCompany_name() + "中心您好,这里是快送哥" + company.getCompany_name() + "网点联系不上,请确认";
                name = parentCenter.getCompany_name();
                number = parentCenter.getCompany_linktel();
                siteid = "10000197";
                vars.put("company", 2);
                break;
            case "上海总中心":
                Company parentCenter2 = this.getCom(parentCom.getCompany_parentid());
                Company center = this.getCom(parentCenter2.getCompany_parentid());
                message = center.getCompany_name() + "中心您好,这里是快送哥" + company.getCompany_name() + "网点联系不上,请确认";
                name = center.getCompany_name();
                number = center.getCompany_linktel();
                siteid = "10000197";
                vars.put("company", 3);
                break;
            case "再次拨打电话确认":
                Integer num = (Integer) context.getProcessService().getProcessVariable("company", processInstance.getId());
                switch (num) {
                    case 1:
                        message = parentCom.getCompany_name() + "网点您好,这里是快送哥" + company.getCompany_name() + "网点是否可以联系上";
                        name = parentCom.getCompany_name();
                        number = parentCom.getCompany_linktel();
                        siteid = "10000259";
                        break;
                    case 2:
                        Company parentCenter3 = this.getCom(parentCom.getCompany_parentid());
                        message = parentCenter3.getCompany_name() + "中心您好,这里是快送哥" + company.getCompany_name() + "网点是否可以联系上";
                        name = parentCenter3.getCompany_name();
                        number = parentCenter3.getCompany_linktel();
                        siteid = "10000259";
                        break;
                    case 3:
                        Company parentCenter4 = this.getCom(parentCom.getCompany_parentid());
                        Company center2 = this.getCom(parentCenter4.getCompany_parentid());
                        message = center2.getCompany_name() + "中心您好,这里是快送哥" + company.getCompany_name() + "网点是否可以联系上";
                        name = center2.getCompany_name();
                        number = center2.getCompany_linktel();
                        siteid = "10000259";
                        break;
                }
        }
        if (null != vars) {
            context.getProcessService().saveProcessVariables(processInstance.getId(), vars);
        }
//        context.getProcessService().saveProcessVariable(processInstance.getId(), "sonid", processInstance.getId());
        String qymndh = userReportService.getParameter("qymndh");
        if ("true".equals(qymndh)) {
            number = problem.getProblemparts_receivephone();
        }
        HandlerUtil.text01(processInstance, context, number, message, problem.getProblemparts_type().toString(),
                    siteid, problem.getProblemparts_expressnumber().toString(),2);
    }

    //得到运单号
    private Problem getExpressNumber(String id) {
        ProblemDTO cond = new ProblemDTO();
        cond.setId(Long.valueOf(id));
        Problem problem = problemRepository.findOne(cond);
        return problem;
    }

    //查询派送网点信息
    private Company getBody(Integer num, String expressNumber) {
        ExpressInfoDetail expressInfoDetail = expressInfoDetailService.findByType(num, expressNumber);
        Company company = companyService.findOneByID(Long.valueOf(expressInfoDetail.getBegin_companyid()));
        return company;
    }

    //获得上级网点信息
    private Company getCom(Long id) {
        Company panrentCom = companyService.findOneByID(id);
        return panrentCom;
    }
}
