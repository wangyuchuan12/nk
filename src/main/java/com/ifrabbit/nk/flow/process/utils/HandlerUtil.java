package com.ifrabbit.nk.flow.process.utils;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.ifrabbit.nk.express.domain.TempVariable;
import com.ifrabbit.nk.flow.service.TaskService;
import com.ifrabbit.nk.flow.service.VariableService;
import com.ifrabbit.nk.flow.service.impl.TaskServiceImpl;
import com.ifrabbit.nk.usercenter.domain.Company;
import ir.nymph.date.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;


/**
 @Author: lishaomiao
  * @Date:2018/4/19
 * @Description:通用方法
 */
@Component
public class HandlerUtil {
    @Autowired
    private VariableService variableService;
    private  static VariableService variableService1;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(HandlerUtil.class);
    //在static方法里用@Autowire
    @PostConstruct
    public void init() {
        variableService1 = variableService;
    }

    /**
     * 终止流程
     * Created with IDEA
     * author:lishaomiao
     * Date:2018/4/26
     */
    public static String stop(ProcessInstance processInstance,Context context){
        Long id = processInstance.getProcessId();
        logger.info("=======获取到的processInstanceId==============="+id);
        context.getProcessService().deleteProcessInstanceById(id);
        return "fail";
    }

    /**
     * 工作流的系统参数在使用之后直接删除避免（累计电话次数的参数不用删）
     */
    public static String getProcessVariable(ProcessInstance processInstance,Context context,String variableKey){
        String variable = (String) context.getProcessService().getProcessVariable(variableKey, processInstance.getId());
        context.getProcessService().deleteProcessVariable(variableKey,processInstance.getId());
        return  variable;
    }


    public static void setProcessVariable(ProcessInstance processInstance,Context context,String variableKey){
        if("第一天第一次拨打收件人电话".equals(variableKey)){
            context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第一次拨打收件人电话-day",
                    "D1");
            context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第一次拨打收件人电话-count",
                    "C1");
            context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第一次拨打收件人电话-modal",
                    "M1");
        }else if("第一天第二次拨打收件人电话".equals(variableKey)){
            context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第二次拨打收件人电话-day",
                    "D1");
            context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第二次拨打收件人电话-count",
                    "C2");
            context.getProcessService().saveProcessVariable(processInstance.getId(),"第一天第二次拨打收件人电话-modal",
                    "M1");
        }else if("第二天第一次拨打收件人电话".equals(variableKey)){
            context.getProcessService().saveProcessVariable(processInstance.getId(),"第二天第一次拨打收件人电话-day",
                    "D2");
            context.getProcessService().saveProcessVariable(processInstance.getId(),"第二天第一次拨打收件人电话-count",
                    "C1");
            context.getProcessService().saveProcessVariable(processInstance.getId(),"第二天第一次拨打收件人电话-modal",
                    "M1");
        }else if("第二天第二次拨打收件人电话".equals(variableKey)){
            context.getProcessService().saveProcessVariable(processInstance.getId(), "第二天第二次拨打收件人电话-day",
                    "D2");
            context.getProcessService().saveProcessVariable(processInstance.getId(), "第二天第二次拨打收件人电话-count",
                    "C2");
            context.getProcessService().saveProcessVariable(processInstance.getId(), "第二天第二次拨打收件人电话-modal",
                    "M1");
        }else if("第三天第一次拨打收件人电话".equals(variableKey)){
            context.getProcessService().saveProcessVariable(processInstance.getId(), "第三天第一次拨打收件人电话-day",
                    "D3");
            context.getProcessService().saveProcessVariable(processInstance.getId(), "第三天第一次拨打收件人电话-count",
                    "C1");
            context.getProcessService().saveProcessVariable(processInstance.getId(), "第三天第一次拨打收件人电话-modal",
                    "M1");
        }else if("第三天第二次拨打收件人电话".equals(variableKey)){
            context.getProcessService().saveProcessVariable(processInstance.getId(), "第三天第二次拨打收件人电话-day",
                    "D3");
            context.getProcessService().saveProcessVariable(processInstance.getId(), "第三天第二次拨打收件人电话-count",
                    "C2");
            context.getProcessService().saveProcessVariable(processInstance.getId(), "第三天第二次拨打收件人电话-modal",
                    "M1");
        }
    }

    public static String parseType(Integer type){
        switch (type) {
            case 1:
                return "签收未收到";
            case 2:
                return "破损";
            case 3:
                return "改地址";
            case 4:
                return "退回";
            case 5:
                return "催单";
        }
        return  null;
    }

    public static String getLink(String pNum,Company netWork){
        switch (pNum){
            case "1":
                return netWork.getCompany_tel();
            case "2":
                return netWork.getCompany_tel_2();
            case "3":
                return  netWork.getCompany_tel_3();
        }
        return null;
    }

    public static Timestamp parseTime(String time, DateTime dateTime){
        if (time.equals("0900")){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,9);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dateTime.setTime(calendar.getTimeInMillis());
            return dateTime.toTimestamp();
        }else {
            Integer ts = Integer.parseInt(time);
            long l = dateTime.getTime() + ts * 60 * 1000;
            DateTime date = new DateTime(l);
            return  date.toTimestamp();
        }
    }

    public static boolean text01(ProcessInstance processInstance, Context context,String phone,String name,String type,String siteid,String expressNumber,Integer tag){
        long id = processInstance.getId();
        Map map = HttpUtils.post(phone,name,type, siteid,String.valueOf(id),expressNumber);
        if (null!=map.get("outcome")&&"SUCCESS".equals(map.get("outcome"))){
            Object call_uuid = map.get("call_uuid");
            context.getProcessService().saveProcessVariable(processInstance.getId(),"call_uuid",call_uuid);
            context.getProcessService().saveProcessVariable(processInstance.getId(),"outcome","SUCCESS");
            DateTime dateTime = new DateTime();
            String option = null;
            String taskName = null;
            switch (tag){
                case 0:
                    taskName = "呼叫收件人";
                    option = "系统在" + dateTime + "拨打了收件人电话" + phone +"姓名为"+name;
                    break;
                case 1:
                    taskName = "呼叫网点";
                    TempVariable v = new TempVariable();
                    v.setProcessId(processInstance.getId());
                    v.setT_key("call_uuid");
                    v.setT_value(call_uuid.toString());
                    variableService1.insert(v);
                    String u_message = variableService1.getPhoneVariable(processInstance,"U_message");
                    if(!"4".equals(u_message)){
                    String u_line = variableService1.getPhoneVariable(processInstance, "U_line");
                    option = "系统在" + dateTime + "拨打了" + name + "网点进行播报问题件" + u_line + "号线已收到";}
                    break;
                case 2:
                    taskName = "呼叫上级网点、中心";
                    String u_line1 = variableService1.getPhoneVariable(processInstance, "U_line");
                    option = "系统在" + dateTime + "拨打了" + name + "网点(中心)播报网点联系不上" + u_line1 + "号线已收到";
                    break;
                case 3:
                    taskName = "呼叫发起人";
                    option = "系统在" + dateTime + "拨打了发起人的电话" + phone +"姓名为"+ name;
                    break;
                case 4:
                    taskName = "呼叫发件人";
                    option = "系统在" + dateTime + "拨打了发件人的电话" + phone +"姓名为"+ name;
            }

            TaskService taskService = new TaskServiceImpl();
            taskService.createHisTask(processInstance, context, taskName, option,map.get("call_uuid").toString());
            return true;
        }else {
            DateTime dateTime = new DateTime();
            String option = null;
            String taskName = null;
            switch (tag) {
                case 0:
                    taskName = "呼叫收件人";
                    option = "系统在" + dateTime + "未成功拨通收件人" + name +"电话为"+ phone;
                    break;
                case 1:
                    taskName = "呼叫网点";
                    option = "系统在" + dateTime + "未成功拨通" + name + "网点电话"+phone+",播报问题件失败";
                    break;
                case 2:
                    taskName = "呼叫上级网点、中心";
                    option = "系统在" + dateTime + "未成功拨通" + name + "网点(中心)电话"+phone+"'播报网点联系不上'";
                    break;
                case 3:
                    taskName = "呼叫发起人";
                    option = "系统在" + dateTime + "未成功拨通发起人" + name + phone;
                    break;
                case 4:
                    taskName = "呼叫发件人";
                    option = "系统在" + dateTime + "未成功拨通发件人" + name + phone;
            }

            TaskService taskService = new TaskServiceImpl();
            taskService.createHisTask(processInstance, context, taskName, option, null);
            return  false;
        }
    }
}


