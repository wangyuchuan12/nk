
package com.ifrabbit.nk.flow.process.decision.phone.sub;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.express.service.CallDetailService;
import com.ifrabbit.nk.express.service.DealTypeService;
import com.ifrabbit.nk.express.service.TableInfoService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("上一级网点子流程")
public class NetRB implements DecisionHandler{

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(NetRB.class);

    @Autowired
    private DealTypeService dealTypeService;
    @Autowired
    private CallDetailService callDetailService;
    @Autowired
    private TableInfoService tableInfoService;

    @Override
    public String handle(Context context, ProcessInstance processInstance) {

        String businessId = processInstance.getBusinessId();
        Long instanceId = processInstance.getId();
        String nodeName = processInstance.getCurrentTask();
        Long processId = processInstance.getProcessId();

        //获取电话结果
        CallDetail callDetail = new CallDetail();
        callDetail.setCalldetail_problemid(Long.valueOf(businessId));
        callDetail.setCalldetail_nodename(nodeName);
        CallDetail callDetail1 = callDetailService.findOne(callDetail);
        Integer answerId = callDetail1.getCalldetail_answerid();

        if(nodeName != null && answerId != null){
            //进行电话结果判断
            if(nodeName.contains("第一次拨打网点第一个电话(C)") ){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "听留言";
                    case 23 :
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第一个电话（C）-count",
                                "C2");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第一个电话（C）-amount",
                                "A1");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第一个电话（C）-modal",
                                "M3");
                        return "有按1,但无留言";
                    case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        return "未接通及接通挂断";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第二次拨打网点第一个电话(C)")){
                switch (answerId) {
                    case 21:
                        return "有效回复";
                    case 22:
                        return "按1有留言";
                    case 23:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                        context.getProcessService().saveProcessVariable(instanceId, "第三次拨打网点第一个电话(C)-count",
                                "C3");
                        context.getProcessService().saveProcessVariable(instanceId, "第三次拨打网点第一个电话(C)-amount",
                                "A1");
                        context.getProcessService().saveProcessVariable(instanceId, "第三次拨打网点第一个电话(C)-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第三次拨打网点第一个电话(C)")){
                    switch (answerId){
                        case 21 :
                            return "有效回复";
                        case 22 :
                            return "按1有留言";
                        case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                            return "其他所有情况拨打上级";
                        default:
                            return null;
                }
            }else if (nodeName.contains("第一次拨打网点第一个电话（B）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "听留言";
                    case 23 :
                        context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第二个电话(B)-count",
                                "C1");
                        context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第二个电话(B)-amount",
                                "A2");
                        context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第二个电话(B)-modal",
                                "M3");
                        return "有按1，但无留言";
                    case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        return "未接通及接通挂断";
                    default:
                        return "";
                }
            }else if (nodeName.contains("第一次拨打网点第二个电话(B)")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第一个电话（B）-count",
                                "C2");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第一个电话（B）-amount",
                                "A1");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第一个电话（B）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第二次拨打网点第一个电话(B)")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第二个电话（B）-count",
                                "C2");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第二个电话（B）-amount",
                                "A2");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第二个电话（B）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第二次拨打网点第二个电话（B）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第一个电话（B）-count",
                                "C3");
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第一个电话（B）-amount",
                                "A1");
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第一个电话（B）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第三次拨打网点第一个电话(B)")){
                switch (answerId){
                    case 21 :
                        return "留言有效";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第二个电话（B）-count",
                                "C3");
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第二个电话（B）-amount",
                                "A2");
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第二个电话（B）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第三次拨打网点第二个电话（B）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        return "其他所有情况拨打上级";
                    default:
                        return null;
                }
            }

            else if (nodeName.contains("第一次拨打网点第一个电话(A)")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "听留言";
                    case 23 :
                        return "有按1,无留言";
                    case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第二个电话（A）-count",
                                "C1");
                        context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第二个电话（A）-amount",
                                "A2");
                        context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第二个电话（A）-modal",
                                "M3");
                        return "未接通及挂断";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第一次拨打网点第二个电话（A）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第三个电话（A）-count",
                                "C1");
                        context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第三个电话（A）-amount",
                                "A3");
                        context.getProcessService().saveProcessVariable(instanceId,"第一次拨打网点第三个电话（A）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }
            else if (nodeName.contains("第一次拨打网点第三个电话（A）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第一个电话（A）-count",
                                "C2");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第一个电话（A）-amount",
                                "A1");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第一个电话（A）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第二次拨打网点第一个电话（A）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第二个电话（A）-count",
                                "C2");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第二个电话（A）-amount",
                                "A2");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第二个电话（A）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第二次拨打网点第二个电话（A）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第三个电话（A）-count",
                                "C2");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第三个电话（A）-amount",
                                "A3");
                        context.getProcessService().saveProcessVariable(instanceId,"第二次拨打网点第三个电话（A）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第二次拨打网点第三个电话（A）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第一个电话（A）-count",
                                "C3");
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第一个电话（A）-amount",
                                "A1");
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第一个电话（A）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第三次拨打网点第一个电话（A）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第二个电话（A）-count",
                                "C3");
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第二个电话（A）-amount",
                                "A2");
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第二个电话（A）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第三次拨打网点第二个电话（A）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第三个电话（A）-count",
                                "C3");
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第三个电话（A）-amount",
                                "A3");
                        context.getProcessService().saveProcessVariable(instanceId,"第三次拨打网点第三个电话（A）-modal",
                                "M3");
                        return "其他所有情况";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第三次拨打网点第三个电话（A）")){
                switch (answerId){
                    case 21 :
                        return "有效回复";
                    case 22 :
                        return "按1有留言";
                    case 23 :case 31:case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                        return "其他所有情况拨打上级";
                    default:
                        return null;
                }
            }
        }
        return null;
    }
}


