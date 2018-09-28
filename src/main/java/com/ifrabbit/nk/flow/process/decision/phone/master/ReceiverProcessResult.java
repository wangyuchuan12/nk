package com.ifrabbit.nk.flow.process.decision.phone.master;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.TableInfo;
import com.ifrabbit.nk.express.domain.TableInfoDTO;
import com.ifrabbit.nk.express.service.TableInfoService;
import com.ifrabbit.nk.flow.process.utils.CallDetailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("拨打收件人子流程结束结果判断")
public class ReceiverProcessResult implements DecisionHandler {
    @Autowired
    private TableInfoService tableInfoService;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        //在tableinfo里面取结果
        TableInfoDTO cond = new TableInfoDTO();
        cond.setTableinfo_tabid(processInstance.getId());
        TableInfo tableInfo = tableInfoService.findOne(cond);
        Integer tableinfo_result = tableInfo.getTableinfo_result();
        switch (tableinfo_result){
            case 11:
                return "子流程结束指向拨打网点";
        }
        return "子流程结束指向结束";
    }
}
