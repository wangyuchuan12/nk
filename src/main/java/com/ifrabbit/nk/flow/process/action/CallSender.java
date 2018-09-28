package com.ifrabbit.nk.flow.process.action;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.ActionHandler;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.domain.ProblemDTO;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.flow.process.utils.HandlerUtil;
import com.ifrabbit.nk.flow.repository.VariableRepository;
import com.ifrabbit.nk.flow.service.TaskService;
import com.ifrabbit.nk.message.domain.SmsMiddleTable;
import com.ifrabbit.nk.message.repository.SmsMiddleTableRepository;
import com.ifrabbit.nk.message.service.MessageService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: WangYan
 * @Date: 2018/4/18 14:43
 * @Description: 智能客服拨打发件人执行程序
 */
@Component("呼叫发件人")
public class CallSender implements ActionHandler {
    private static Logger logger = LoggerFactory.getLogger(CallSender.class);

    @Autowired
    HttpServletRequest request;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private VariableRepository variableRepository;
    @Autowired
    private UserReportService userReportService;

    @Autowired
    private SmsMiddleTableRepository smsMiddleTableRepository;

    @Override
    public void handle(ProcessInstance processInstance, Context context) {
        ProblemDTO cond = new ProblemDTO();
        cond.setId(Long.valueOf(processInstance.getBusinessId()));
        Problem problem = problemRepository.findOne(cond);
        String sender = problem.getProblemparts_sendphone();
        String siteid = new String();
        String tempTime = userReportService.getParameter("cbsjjg");
        Long time;
        if (null == tempTime){
            time = 24 * 60 * 60 * 1000L;
        }else {
            time = Long.valueOf(tempTime)*1000;
        }
        switch (problem.getProblemparts_type()) {
            case 1:
                siteid = "10000204";
                break;
            case 2:
                siteid = "10000183";
                break;
            case 3:
                siteid = "10000228";
                break;
            case 4:
                siteid = "10000220";
                break;
            case 5:
                siteid = "10000228";
                break;
        }

        int i = 0 ;
        while (i < 3){
            boolean check = HandlerUtil.text01(processInstance,context,sender,problem.getProblemparts_sendname(),null,siteid,
                    problem.getProblemparts_expressnumber().toString(),4);
            i++;
            if(i==3){
                sendM(processInstance, context, problem);
                return;
            }else {
                if(check){
                sendM(processInstance, context, problem);
                return;
                }else{
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 三次拨不通时给发件人发送短信
//        String messageNumber = userReportService.getParameter("dxmndh");
//        if (null == messageNumber){
//            messageNumber=sender;
//        }
    }

    private void sendM(ProcessInstance processInstance, Context context, Problem problem) {
        String messageNumber = problem.getProblemparts_sendphone();
        String message = HandlerUtil.getProcessVariable(processInstance,context,"outcome");
        String expressNumber = String.valueOf(problem.getProblemparts_expressnumber());
        String type = "未定义";
        Integer problemPartsType = problem.getProblemparts_type();
        switch (problemPartsType){
            case 1:
                type = "签收未收到";
                break;
            case 2:
                type = "破损";
                break;
            case 3:
                type = "改地址";
                break;
            case 4:
                type = "退回";
                break;
            case 5:
                type = "催单";
                break;
        }
        String[] data = new String[4];
        data[0] = problem.getProblemparts_sendname();
        data[1] = expressNumber;
        data[2] = type;
        data[3] = problem.getProblemparts_receivename();

        Map<String, Object> vars = new HashMap<>();
        vars.put("phoneMessage", messageNumber);
        vars.put("data", data);
        vars.put("problem", problem);
        vars.put("modal",1);
        if ("SUCCESS".equals(message)){
            //不用发送短信
            vars.put("selectSend", 1);
        }else {
            vars.put("selectSend", 2);
            //TODO processInstance，messageNumber存入中间表，之后会进入系统代办，等客户回复后，会完成系统代办。
        }
        context.getProcessService().saveProcessVariables(processInstance.getId(), vars);
    }



//        try {
//            logger.info("系统睡眠十秒");
//            Thread.sleep(10000);
//            List<TempVariable> phoneNumber = variableRepository.findPhoneNumber(messageNumber);
//            int size = phoneNumber.size() - 1;
//            TempVariable tempVariable = phoneNumber.get(size);
//            String t_key = tempVariable.getT_key();
//
//            Map<String, Object> vars = new HashMap<>();
//            vars.put("U_sign", "1");//置为1表示是短信的签收
//            if(t_key.equalsIgnoreCase("U_content")){
//                String t_value = tempVariable.getT_value();
//                if(t_value.equals("1")){
//                    vars.put("U_recive", "1");
//                    logger.info("客户已收到短信并回复了1");
//
//                }else if(t_value.equals("2")){
//                    vars.put("U_recive", "2");
//                    logger.info("客户已收到短信并回复了2");
//                }else{
//                    logger.info("客户没有收到了短信，没有作答复");
//                    String remindModal = "259193";//回复的信息不符合要求可以再发一条提醒客户
//                    sendMessage1(processInstance, context, messageNumber, data,remindModal);
//                }
//                context.getProcessService().saveProcessVariables(processInstance.getId(), vars);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        try {
//            //系统十秒后去数据库查有没有客户回复的信息
//            logger.info("系统睡眠十秒");
//            Thread.sleep(10000);
//            List<TempVariable> phoneNumber = variableRepository.findPhoneNumber(messageNumber);
//            int size = phoneNumber.size() - 1;
//            TempVariable tempVariable = phoneNumber.get(size);
//            String t_key = tempVariable.getT_key();
//
//            Map<String, Object> vars = new HashMap<>();
//            vars.put("U_flag", "1");//置为1表示是短信的签收
//            if(t_key.equalsIgnoreCase("U_content")){
//                String t_value = tempVariable.getT_value();
//                if(t_value.equals("1")){
//                    vars.put("U_content", "1");
//                    logger.info("客户已收到短信并回复了1");
//
//                }else if(t_value.equals("2")){
//                    vars.put("U_content", "2");
//                    logger.info("客户已收到短信并回复了2");
//                }else{
//                    logger.info("客户没有收到了短信，没有作答复");
//                   String remindModal = "259193";//回复的信息不符合要求可以再发一条提醒客户
//                   sendMessage(processInstance, context, messageNumber, data,remindModal);
//                }
//                context.getProcessService().saveProcessVariables(processInstance.getId(), vars);
//            }
//
////                DateTime time = new DateTime();
////                String option1 = "系统在" + time + "向发件人: " + problem.getProblemparts_sendname() + "发送了短信通知";
////                Map<String, Object> vars1 = new HashMap<>();
////                context.getProcessService().saveProcessVariables(processInstance.getId(), vars);
////                Object call_uuid = context.getProcessService().getProcessVariable("call_uuid", processInstance.getId());
////                taskService.createHisTask(processInstance, context, "呼叫发件人", option1,call_uuid.toString());
//        }catch (Exception e){
//            e.printStackTrace();
//        }

}
