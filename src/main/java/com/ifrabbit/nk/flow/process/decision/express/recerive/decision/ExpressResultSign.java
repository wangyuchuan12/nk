package com.ifrabbit.nk.flow.process.decision.express.recerive.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import com.ifrabbit.nk.express.domain.ExpressInfoRecordDTO;
import com.ifrabbit.nk.express.service.DealTypeService;
import com.ifrabbit.nk.express.service.ExpressInfoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/17
 * Time:10:31
 */
@Component("签收未收到-物流结果判断是否签收")
public class ExpressResultSign implements DecisionHandler {
    @Autowired
    DealTypeService dealTypeService;
    @Autowired
    ExpressInfoRecordService expressInfoRecordService;

    @Override
    public String handle(Context context, ProcessInstance processInstance) {

        long tabID = processInstance.getId();

        ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
        expressInfoRecordDTO.setExpress_processinstanceid(tabID);
        ExpressInfoRecord expressInfoRecordServiceOne = expressInfoRecordService.findOne(expressInfoRecordDTO);
        Integer express_result = expressInfoRecordServiceOne.getExpress_result();

        switch (express_result){
            case 51:
                return "签收";
            case 57:
                return "过程中出现网点信息不匹配";
        }
        return "非签收";
    }
}
