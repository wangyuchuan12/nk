package com.ifrabbit.nk.flow.process.decision.express.change.decision;

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
 * Date:2018/9/17
 * Time:10:45
 */
@Component("改地址-物流结果判断(A1)")
public class ExpressResultChangeA1 implements DecisionHandler {
    @Autowired
    ExpressInfoRecordService expressInfoRecordService;

    @Override
    public String handle(Context context, ProcessInstance processInstance) {

        long tabID = processInstance.getId();

        //根据processInstanceID和nodeName找到最后一个对象
        ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
        expressInfoRecordDTO.setExpress_processinstanceid(tabID);
        expressInfoRecordDTO.setExpress_nodename("改地址物流解析(A1)");
        List<ExpressInfoRecord> expressInfoRecordServiceAll = expressInfoRecordService.findAll(expressInfoRecordDTO);
        ExpressInfoRecord expressInfoRecord = expressInfoRecordServiceAll.get(expressInfoRecordServiceAll.size() - 1);
        Integer express_result = expressInfoRecord.getExpress_result();

        if(express_result == 60)
            return "改地址";
        return "未改地址";
    }
}
