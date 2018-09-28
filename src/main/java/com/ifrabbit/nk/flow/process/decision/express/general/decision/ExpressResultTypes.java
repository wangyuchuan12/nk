
package com.ifrabbit.nk.flow.process.decision.express.general.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import com.ifrabbit.nk.express.domain.ExpressInfoRecordDTO;
import com.ifrabbit.nk.express.repository.ExpressInfoDetailRepository;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.ExpressInfoRecordService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("通用-物流结果判断多种状态")
public class ExpressResultTypes implements DecisionHandler{

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ExpressResultTypes.class);
    @Autowired
    public ExpressInfoRecordService expressInfoRecordService;
    @Autowired
    public ExpressInfoDetailRepository expressInfoDetailRepository;

    @Autowired
    public ProblemRepository problemRepository;
    @Autowired
    public UserReportService userReportService;


    @Override
    public String handle(Context context, ProcessInstance processInstance) {

        Long processInstanceId = processInstance.getId();

        //获取物流结果
        ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
        expressInfoRecordDTO.setExpress_nodename("物流解析(A)");
        expressInfoRecordDTO.setExpress_processinstanceid(processInstanceId);

        List<ExpressInfoRecord> expressInfoRecordServiceAll = expressInfoRecordService.findAll(expressInfoRecordDTO);
        ExpressInfoRecord expressInfoRecord = expressInfoRecordServiceAll.get(expressInfoRecordServiceAll.size() - 1);
        Integer expressResult = expressInfoRecord.getExpress_result();

        switch (expressResult) {
                    case 51:
                        return "C状态";
                    case 52:
                        return "无揽收";
                    case 53:
                        return "B状态";
                    case 54:
                        return "B状态";
                    case 55:
                        return "A状态";
                    case 57:
                        return "添加网点信息";
                }
        return null;
    }
}

