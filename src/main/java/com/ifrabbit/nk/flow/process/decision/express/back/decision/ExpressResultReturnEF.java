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
 * Date:2018/9/19
 * Time:16:25
 */
@Component("退回-物流结果判断EF")
public class ExpressResultReturnEF implements DecisionHandler {
    @Autowired
    ExpressInfoRecordService expressInfoRecordService;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {

        long tabID = processInstance.getId();

        //先查出运单号
        ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
        expressInfoRecordDTO.setExpress_processinstanceid(tabID);
        expressInfoRecordDTO.setExpress_nodename("物流解析(A)");
        List<ExpressInfoRecord> expressInfoRecordServiceAll = expressInfoRecordService.findAll(expressInfoRecordDTO);
        ExpressInfoRecord expressInfoRecord = expressInfoRecordServiceAll.get(expressInfoRecordServiceAll.size() - 1);
        Integer express_result = expressInfoRecord.getExpress_networknumber();
        Integer express_type = expressInfoRecord.getExpress_type();

        if(express_type == 20) {
            return "签收";
        } else if (express_result == 1) {
            return "停滞一个网点";
        }
        return "停滞两个网点";
    }
}
