package com.ifrabbit.nk.flow.process.decision.phone.master;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
import com.ifrabbit.nk.express.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("是否已经打过上海")
public class CallShanghai implements DecisionHandler {
    @Autowired
    public ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    public ProblemRepository problemRepository;
    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private DealinfoService dealinfoService;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        Long businessId = Long.valueOf(processInstance.getBusinessId());
        Problem problem = problemRepository.findOne(businessId);
        TableInfoDTO dto = new TableInfoDTO();
        dto.setTableinfo_bussinessid(problem.getId());
        TableInfo one = tableInfoService.findOne(dto);
        DealinfoDTO cond = new DealinfoDTO();
        cond.setAppdealTabid(one.getId());
        List<Dealinfo> all = dealinfoService.findAll(cond);
        for (Dealinfo dealinfo : all) {
            if (dealinfo.getAppdealResult() == 13){
                return  "是";
            }
        }
        return  "否";
    }
}
