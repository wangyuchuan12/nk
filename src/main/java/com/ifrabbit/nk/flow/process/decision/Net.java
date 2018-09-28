/**
 * Copyright (C), 2018-2018, KSG有限公司
 * FileName: Net
 * Author:
 * Date:     2018-09-09 22:20
 * Description:
 * History:
 */


package com.ifrabbit.nk.flow.process.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.flow.repository.VariableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("网点取参数")
public class Net  implements DecisionHandler {

    private static Logger logger = LoggerFactory.getLogger(Net.class);
    @Autowired
    VariableRepository variableRepository;

    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        context.getProcessService().getProcessVariable("B", processInstance);
        return "是";
    }
}
