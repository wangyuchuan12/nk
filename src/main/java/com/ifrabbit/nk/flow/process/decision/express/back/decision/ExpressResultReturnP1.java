package com.ifrabbit.nk.flow.process.decision.express.back.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import com.ifrabbit.nk.express.domain.ExpressInfoRecordDTO;
import com.ifrabbit.nk.express.service.ExpressInfoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/20
 * Time:10:27
 */
@Component("退回-物流结果判断(P1)")
public class ExpressResultReturnP1 implements DecisionHandler {
    @Autowired
    ExpressInfoRecordService expressInfoRecordService;

    @Override
    public String handle(Context context, ProcessInstance processInstance) {

        long tabID = processInstance.getId();

        ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
        expressInfoRecordDTO.setExpress_processinstanceid(tabID);
        expressInfoRecordDTO.setExpress_nodename("退回物流解析(P1)");
        List<ExpressInfoRecord> expressInfoRecordServiceAll = expressInfoRecordService.findAll(expressInfoRecordDTO);
        ExpressInfoRecord expressInfoRecord = expressInfoRecordServiceAll.get(expressInfoRecordServiceAll.size() - 1);
        Integer express_result = expressInfoRecord.getExpress_result();

        if(express_result == 61)
            return "退回";
        return "未退回";
    }
}
