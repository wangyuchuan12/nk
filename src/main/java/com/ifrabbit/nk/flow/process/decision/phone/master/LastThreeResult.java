package com.ifrabbit.nk.flow.process.decision.phone.master;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import com.ifrabbit.nk.express.domain.TableInfo;
import com.ifrabbit.nk.express.domain.TableInfoDTO;
import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
import com.ifrabbit.nk.express.service.TableInfoService;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("拨打上三级网点子流程结果判断")
public class LastThreeResult implements DecisionHandler {
    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    private CompanyService companyService;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        TableInfoDTO cond = new TableInfoDTO();
        cond.setTableinfo_tabid(processInstance.getId());
        TableInfo tableInfo = tableInfoService.findOne(cond);
        Integer tableinfo_result = tableInfo.getTableinfo_result();
        switch (tableinfo_result){
            case 21 : case 24:
                return "子流程结束指向拨打收件人";
            case 23 :case 25 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                ExpressInfoDetail expressInfoDetail = expressInfoDetailService.findByType(3,processInstance.getBusinessId());
                Company company = companyService.get(Long.valueOf(expressInfoDetail.getBegin_companyid()));
                Company parent1 = companyService.get(company.getCompany_parentid());
                Company parent2 = companyService.get(parent1.getCompany_parentid());
                Company parent3 = companyService.get(parent2.getCompany_parentid());
                Company parent4 = companyService.get(parent3.getCompany_parentid());
                if (null == parent4|| parent4.getCompany_name().equals("上海")){
                    return "无上级网点信息或上级为上海";
                }else {
                    return "有。子流程结束指向拨打上级";
                }
        }
        return null;
    }
}
