package com.ifrabbit.nk.flow.process.decision.express.reminder.prescription;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import com.ifrabbit.nk.express.domain.ExpressInfoRecordDTO;
import com.ifrabbit.nk.express.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/25
 * Time:16:02
 */
@Component("催单-时效判断A")
public class ReminderTimeJudgmentA implements DecisionHandler {
    @Autowired
    DealTypeService dealTypeService;
    @Autowired
    ExpressInfoRecordService expressInfoRecordService;
    @Autowired
    ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    DealinfoService dealinfoService;
    @Autowired
    TableInfoService tableInfoService;

    @Override
    public String handle(Context context, ProcessInstance processInstance) {

        Long tabID = Long.valueOf(processInstance.getId());

        ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
        expressInfoRecordDTO.setExpress_processinstanceid(tabID);
        expressInfoRecordDTO.setExpress_nodename("物流解析是否签收");
        List<ExpressInfoRecord> expressInfoRecordServiceAll = expressInfoRecordService.findAll(expressInfoRecordDTO);
        ExpressInfoRecord expressInfoRecord = expressInfoRecordServiceAll.get(expressInfoRecordServiceAll.size() - 1);

        Integer express_result = expressInfoRecord.getExpress_result();

        if(express_result == 54){
            return "超时";
        }else{
            //删除原有的数据，避免processInstanceId重复
            return "未超时";
        }

    }
}
