package com.ifrabbit.nk.flow.process.decision.phone.sub;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.service.CallDetailService;
import com.ifrabbit.nk.usercenter.domain.UfloTask;
import com.ifrabbit.nk.usercenter.domain.UfloTaskDTO;
import com.ifrabbit.nk.usercenter.service.UfloTaskService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("破损拨打收件人电话结果判断")
public class BrokenPhoneResult implements DecisionHandler {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BrokenPhoneResult.class);

    @Autowired
    private CallDetailService callDetailService;

    @Autowired
    private UfloTaskService ufloTaskService;
    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        //当前环节任务名称
        String businessId = processInstance.getBusinessId();
        Long instanceId = processInstance.getId();
        String nodeName = processInstance.getCurrentTask();

        //在task中找到这个任务节点  拿到taskID
        UfloTaskDTO ufloTaskDTO = new UfloTaskDTO();
        ufloTaskDTO.setProcessInstanceId(instanceId);
        ufloTaskDTO.setNodeName(nodeName);
        UfloTask ufloTask = ufloTaskService.findOne(ufloTaskDTO);
        Long taskId = ufloTask.getId();


        //获取电话结果
        CallDetail callDetail = new CallDetail();
        callDetail.setCalldetail_problemid(Long.valueOf(businessId));
        callDetail.setCalldetail_nodename(nodeName);
        callDetail.setCalldetail_dealid(taskId);
        CallDetail callDetail1 = callDetailService.findOne(callDetail);
        Integer answerId = callDetail1.getCalldetail_answerid();
        /*13 有责  14 无责  31 挂断  32 正忙  33 关机 34 停机 35 空号 36 其他 37 无电话 38 超出时间*/
        if(nodeName != null){
            //进行电话结果判断
            //第一天第一次拨打收件人电话
            if(nodeName.equals("第一天第一次拨打收件人电话") && answerId != null){
                switch (answerId){
                    case 13:
                        return "有责";
                    case 14:
                        return "无责";
                    case 31:
                        context.getProcessService().saveProcessVariable(instanceId,"第二天第一次拨打收件人电话-day",
                                "D2");
                        context.getProcessService().saveProcessVariable(instanceId,"第二天第一次拨打收件人电话-count",
                                "C1");
                        context.getProcessService().saveProcessVariable(instanceId,"第二天第一次拨打收件人电话-modal",
                                "M11");
                        return "接通挂断";
                    case 32:case 33:case 34:case 36:case 37:case 38:
                        context.getProcessService().saveProcessVariable(instanceId,"第一天第二次拨打收件人电话-day",
                                "D1");
                        context.getProcessService().saveProcessVariable(instanceId,"第一天第二次拨打收件人电话-count",
                                "C2");
                        context.getProcessService().saveProcessVariable(instanceId,"第一天第二次拨打收件人电话-modal",
                                "M11");
                        return "未接通";
                    case 35:
                        return "空号";
                    default:
                        return null;
                }
            }else if (nodeName.equals("第一天第二次拨打收件人电话") && answerId != null){
                switch (answerId) {
                    case 13:
                        return "有责";
                    case 14:
                        return "无责";
                    case 31:
                    case 32:
                    case 33:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                        context.getProcessService().saveProcessVariable(instanceId, "第二天第一次拨打收件人电话-day",
                                "D2");
                        context.getProcessService().saveProcessVariable(instanceId, "第二天第一次拨打收件人电话-count",
                                "C1");
                        context.getProcessService().saveProcessVariable(instanceId, "第二天第一次拨打收件人电话-modal",
                                "M11");
                        return "未接通及接通挂断";
                    case 34:
                        return "停机";
                    default:
                        return null;
                }
            }else if (nodeName.equals("第二天第一次拨打收件人电话") && answerId != null){
                switch (answerId) {
                    case 13:
                        return "有责";
                    case 14:
                        return "无责";
                    case 31:
                        context.getProcessService().saveProcessVariable(instanceId, "第三天第一次拨打收件人电话-day",
                                "D3");
                        context.getProcessService().saveProcessVariable(instanceId, "第三天第一次拨打收件人电话-count",
                                "C1");
                        context.getProcessService().saveProcessVariable(instanceId, "第三天第一次拨打收件人电话-modal",
                                "M11");
                        return "接通挂断";
                    case 32:
                    case 33:
                    case 34:
                    case 36:
                    case 37:
                    case 38:
                        context.getProcessService().saveProcessVariable(instanceId, "第二天第二次拨打收件人电话-day",
                                "D2");
                        context.getProcessService().saveProcessVariable(instanceId, "第二天第二次拨打收件人电话-count",
                                "C2");
                        context.getProcessService().saveProcessVariable(instanceId, "第二天第二次拨打收件人电话-modal",
                                "M11");
                        return "未接通";
                    case 35:
                        return "空号";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第二天第二次拨打收件人电话") && answerId != null){
                switch (answerId) {
                    case 13:
                        return "有责";
                    case 14:
                        return "无责";
                    case 31:
                    case 32:
                    case 33:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                        context.getProcessService().saveProcessVariable(instanceId, "第三天第一次拨打收件人电话-day",
                                "D3");
                        context.getProcessService().saveProcessVariable(instanceId, "第三天第一次拨打收件人电话-count",
                                "C1");
                        context.getProcessService().saveProcessVariable(instanceId, "第三天第一次拨打收件人电话-modal",
                                "M11");
                        return "未接通及接通挂断";
                    case 34:
                        return "停机";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第三天第一次拨打收件人电话") && answerId != null){
                switch (answerId) {
                    case 13:
                        return "有责";
                    case 14:
                        return "无责";
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 36:
                    case 37:
                    case 38:
                        context.getProcessService().saveProcessVariable(instanceId, "第三天第二次拨打收件人电话-day",
                                "D3");
                        context.getProcessService().saveProcessVariable(instanceId, "第三天第二次拨打收件人电话-count",
                                "C2");
                        context.getProcessService().saveProcessVariable(instanceId, "第三天第二次拨打收件人电话-modal",
                                "M11");
                        return "未接通及接通挂断";
                    case 35:
                        return "空号";
                    default:
                        return null;
                }
            }else if (nodeName.contains("第三天第二次拨打收件人电话") && answerId != null){
                switch (answerId) {
                    case 13:
                        return "有责";
                    case 14:
                        return "无责";
                    case 31:
                    case 32:
                    case 33:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                        return "未接通及接通挂断";
                    case 34:
                        return "停机";
                    default:
                        return null;
                }
            }
        }
        return null;
    }
}