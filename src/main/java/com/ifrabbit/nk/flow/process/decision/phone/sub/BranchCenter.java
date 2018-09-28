package com.ifrabbit.nk.flow.process.decision.phone.sub;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
import com.ifrabbit.nk.flow.service.VariableService;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("是否存在上级网点")
public class BranchCenter implements DecisionHandler {
    @Autowired
    public ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    public ProblemRepository problemRepository;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        Long businessId = Long.valueOf(processInstance.getBusinessId());
        Problem problem = problemRepository.findOne(businessId);
        String expressNumber = String.valueOf(problem.getProblemparts_expressnumber());
        ExpressInfoDetail expressInfoDetail = expressInfoDetailService.findByType(3, expressNumber);
        Company company = companyService.get(Long.valueOf(expressInfoDetail.getBegin_companyid()));//派件网点（可能是一级网点也可能是二级网点）
        Integer company_parentid = company.getCompany_parentid().intValue();
        problem.setProblemparts_callsendercount(company_parentid);
        company = companyService.get(company.getCompany_parentid());//派件网点的上级网点（可能是二级网点可能直接是上海）
        problemRepository.updateIgnoreNull(problem);
        if("上海".equals(company.getCompany_name())){
            return  "否";
        }else{
            return  "是";
        }
    }
}
