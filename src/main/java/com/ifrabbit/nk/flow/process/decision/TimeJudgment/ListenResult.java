package com.ifrabbit.nk.flow.process.decision.TimeJudgment;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.TableInfo;
import com.ifrabbit.nk.express.domain.TableInfoDTO;
import com.ifrabbit.nk.express.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: WangYan
 * @Date: 2018/9/20 18:36
 * @Description:
 */
@Component("拨打网点子流程语音判断")
public class ListenResult implements DecisionHandler {
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
            case 24:
                return "接通及回复正确结果";
            case 25:
                return "听留言无效";
            case 26 :
                return "电话不正确";
            case 27 :
                String currentNode = processInstance.getCurrentNode();
                switch (currentNode){
                    case "判断电话是否正确及是否为语音电话(C)":
                        context.getProcessService().saveProcessVariable(processInstance.getId(),"第二次拨打网点第一个电话(C)-count",
                                "C2");
                        context.getProcessService().saveProcessVariable(processInstance.getId(),"第二次拨打网点第一个电话(C)-amount",
                                "A1");
                        context.getProcessService().saveProcessVariable(processInstance.getId(),"第二次拨打网点第一个电话(C)-modal",
                                "M2");
                        break;
                    case "判断电话是否正确及是否为语音电话(B)":
                        context.getProcessService().saveProcessVariable(processInstance.getId(),"第一次拨打网点第二个电话(B)-count",
                                "C1");
                        context.getProcessService().saveProcessVariable(processInstance.getId(),"第一次拨打网点第二个电话(B)-amount",
                                "A2");
                        context.getProcessService().saveProcessVariable(processInstance.getId(),"第一次拨打网点第二个电话(B)-modal",
                                "M2");
                        break;
                    case "判断电话是否正确及是否为语音电话":
                        context.getProcessService().saveProcessVariable(processInstance.getId(),"第一次拨打网点第二个电话（A）-count",
                                "C1");
                        context.getProcessService().saveProcessVariable(processInstance.getId(),"第一次拨打网点第二个电话（A）-amount",
                                "A2");
                        context.getProcessService().saveProcessVariable(processInstance.getId(),"第一次拨打网点第二个电话（A）-modal",
                                "M2");
                        break;
                }
                return "未接通且不为机器人";
            case 28 :
                return "接通及正确回复结果";
            case 29:
                return "机器人";
        }
        return null;
    }
}
