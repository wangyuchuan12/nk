package com.ifrabbit.nk.flow.process.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.TempVariable;
import com.ifrabbit.nk.flow.repository.VariableRepository;
import com.ifrabbit.nk.flow.service.VariableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/4/25
 * Time:14:43
 */
@Component("是否知道签收日期")
public class KnowedSignDate implements DecisionHandler {

    private static Logger logger = LoggerFactory.getLogger(KnowedSignDate.class);

    @Autowired
    private VariableService variableService;
    @Autowired
    VariableRepository variableRepository;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {

        logger.info("收件人是否知道日期......................................");
        String businessId = processInstance.getBusinessId();
        List<TempVariable> tempVariables = variableRepository.findByProcessId(Long.valueOf(businessId));

        String message = "";
        boolean empty = tempVariables.isEmpty();
        if(empty){
            message = variableService.getPhoneVariable(processInstance,"U_date");
        }else{
            int numberSize = tempVariables.size() - 1;
            TempVariable tempVariable = tempVariables.get(numberSize);
            String t_value = tempVariable.getT_value();
            if(t_value.equals("1")){
                message = "1";
            }else if(t_value.equals("2")){
                message = "2";
            }
        }
        if ("1".equals(message)){
            logger.info("++++++++++++++++++++++++++++++++++知道日期+++++++++++++++++++++++++++++++++");
            return "是";
        }else {
            logger.info("++++++++++++++++++++++++++++++++++不知道日期+++++++++++++++++++++++++++++++++");
            return  "否";
        }
    }
}
