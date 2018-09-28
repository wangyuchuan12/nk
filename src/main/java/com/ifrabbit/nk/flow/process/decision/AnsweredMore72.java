package com.ifrabbit.nk.flow.process.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.flow.service.VariableService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/4/25
 * Time:15:12
 */

/**
 * 不接电话是否超过3天
 * 没超过3天继续呼叫，1天一次
 */
@Component("不接电话是否超过3天")
public class AnsweredMore72 implements DecisionHandler {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AnsweredMore72.class);
   @Autowired
   private VariableService variableService;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        logger.info("判断不接电话天数是否超过3天.............................");
        //从数据库中获取未接电话的天数，超过3天return是，不超过3天return否
        //TODO callrecipientscount待确定
        String u_change = variableService.getPhoneVariablebyid(processInstance.getId(), "U_change");
        Object callNumber = context.getProcessService().getProcessVariable("callNumber", processInstance.getId());
        String outnumber = ((Integer)callNumber >= 3&&"2".equals(u_change)) ? "是" : "否";
        if (outnumber.equals("否")) {
            try {
                Thread.sleep(6000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outnumber;
        //测试
        //return "否";
        //return "是";
    }
}
