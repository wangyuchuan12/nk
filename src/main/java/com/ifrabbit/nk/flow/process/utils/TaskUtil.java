package com.ifrabbit.nk.flow.process.utils;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.task.Task;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.service.CallDetailService;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.ProblemService;
import com.ifrabbit.nk.express.service.TableInfoService;
import com.ifrabbit.nk.express.utils.TimeUtil;
import com.ifrabbit.nk.message.domain.SmsMiddleTable;
import com.ifrabbit.nk.message.repository.SmsMiddleTableRepository;
import com.ifrabbit.nk.message.service.MessageService;
import com.ifrabbit.nk.mq.domain.Contain;
import com.ifrabbit.nk.mq.producer.CallBackProducer;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.repository.UfloTaskRepository;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import ir.nymph.date.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: WangYan
 * @Date: 2018/7/6 11:17
 * @Description:
 */
@Component
public class TaskUtil {
    @Autowired
    private TableInfoService tempTableInfoService;
    @Autowired
    private DealinfoService tempDealinfoService;
    @Autowired
    private SmsMiddleTableRepository tempSmsMiddleTableRepository;
    @Autowired
    private MessageService tempMessageService;
    @Autowired
    private ProblemService tempProblemService;
    @Autowired
    private UfloTaskRepository tempTaskRepository;
    @Autowired
    private UserReportService tempUserReportService;
    @Autowired
    private CompanyService tempCompanyService;
    @Autowired
    private CallBackProducer tempcallBackProducer;
    @Autowired
    private CallDetailService tempcallDetailService;

    private static SmsMiddleTableRepository smsMiddleTableRepository;
    private static MessageService messageService;
    private static UfloTaskRepository taskRepository;
    private static TableInfoService tableInfoService;
    private static DealinfoService  dealinfoService;
    private static ProblemService problemService;
    private static UserReportService userReportService;
    private static  CompanyService companyService;
    private static  CallBackProducer callBackProducer;
    private static CallDetailService callDetailService;

    @PostConstruct
    public void init() {  dealinfoService = tempDealinfoService ;
                          tableInfoService = tempTableInfoService;
                          messageService = tempMessageService;
                          smsMiddleTableRepository = tempSmsMiddleTableRepository;
                          taskRepository = tempTaskRepository;
                          problemService = tempProblemService;
                          userReportService=tempUserReportService;
                          companyService=tempCompanyService;
                            callBackProducer=tempcallBackProducer;
                          callDetailService=tempcallDetailService;
     }

    /**
     * 初始化一条任务的表单数据
     * @param task
     */
    public static void  inserteDate(Task task) {
        Dealinfo dealinfo =  new Dealinfo();
        // TODO: 2018/7/8  这里需要增加时间间隔的控制  用系统参数来控制具体的时间 有了时间的控制之后，轮询的地方就可以根据时间排序
        //该任务节点的任务名来判断任务类型(0:打电话 1:发短信 2:物流)
        //根据不同的流程以及环节设置对应的生效时间
        DateTime dateTime = new DateTime();
        long businessId = Long.valueOf(task.getBusinessId());
        Problem problem = problemService.get(businessId);
        dealinfo.setAppdealProblemtype(problem.getProblemparts_type());

        Long id = null;
         if(task.getNodeName().contains("物流") || task.getTaskName().contains("揽收")) {
//            dealinfo.setAppdealDealtype(2);
            Integer wlcxcs = problem.getProblemparts_callrecipientscount();//当天物流已经查询了几次
            if(task.getNodeName().contains("三天")) {
                Integer tempTime = Integer.parseInt(userReportService.getParameter("3thckwlxx"));
                long l = dateTime.getTime() + 1000 * tempTime * 10;
                DateTime d = new DateTime(l);
                dealinfo.setAppdealDealcreatedate(d.toTimestamp());
            }else if(task.getNodeName().contains("两天")) {
                Integer tempTime = Integer.parseInt(userReportService.getParameter("3thckwlxx"));
                long l = dateTime.getTime() + 1000 * tempTime * 10;
                DateTime d = new DateTime(l);
                dealinfo.setAppdealDealcreatedate(d.toTimestamp());
            }else if(task.getNodeName().contains("一天")) {
                //第二天
                if(wlcxcs == 0){
                    Calendar calendar = dateTime.toCalendar();
                    calendar.add(calendar.DATE,1);
                    calendar.set(calendar.HOUR_OF_DAY,8);
                    calendar.set(Calendar.MINUTE, 30);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    dateTime.setTime(calendar.getTimeInMillis());
                    Timestamp timestamp = dateTime.toTimestamp();
                    dealinfo.setAppdealDealcreatedate(timestamp);
                }else{
                    Integer tempTime = Integer.parseInt(userReportService.getParameter("wlcxsjjg"));
                    long l = dateTime.getTime() + 1000 * tempTime * 60;
                    DateTime d = new DateTime(l);
                    dealinfo.setAppdealDealcreatedate(d.toTimestamp());
                }
            }else if(task.getNodeName().contains("3小时")){
                Integer tempTime = Integer.parseInt(userReportService.getParameter("3thckwlxx"));
                long l = dateTime.getTime() + 1000 * tempTime * 20;
                DateTime d = new DateTime(l);
                dealinfo.setAppdealDealcreatedate(d.toTimestamp());

                DealinfoDTO dealinfoDTO = new DealinfoDTO();
                dealinfoDTO.setAppdealProblemid(businessId);
                List<Dealinfo> all = dealinfoService.findAll(dealinfoDTO);
                Dealinfo dealinfo1 = all.get(all.size() - 1);
                Float appdealFloatparama = dealinfo1.getAppdealFloatparama();
                id = dealinfo1.getId();
                if(appdealFloatparama == null){
                    dealinfo.setAppdealFloatparama(0f);
                }else{
                    appdealFloatparama += 1f;
                    dealinfo.setAppdealFloatparama(appdealFloatparama);
                }
            }
        }else if(task.getNodeName().contains("短信")) {
//            dealinfo.setAppdealDealtype(1);
            Integer tempTime = 0;
            if(task.getTaskName().equals("给收件人发送短信是否签收")){
                 tempTime = Integer.parseInt(userReportService.getParameter("sjrdxsxsj"));//给收件人发短信的时候，设置半天或者一天的时效,签收未收到
            }else{
                 tempTime = Integer.parseInt(userReportService.getParameter("dxsxsj"));
            }
            //long l = dateTime.getTime() +  60 * 60 * 20;//1min
            long l = dateTime.getTime() + 1000 * tempTime * 60;
            DateTime d = new DateTime(l);
            dealinfo.setAppdealDealcreatedate(d.toTimestamp());
        }else if(task.getNodeName().contains("定时")) {
//            dealinfo.setAppdealDealtype(3);
            Integer tempTime = Integer.parseInt(userReportService.getParameter("dxsxsj"));
            //long l = dateTime.getTime() +  60 * 60 * 20;//1min
            long l = dateTime.getTime() + 1000 * tempTime * 60;
            DateTime d = new DateTime(l);
            dealinfo.setAppdealDealcreatedate(d.toTimestamp());
        }
        dealinfo.setAppdealTabid(businessId);
        TableInfoDTO cond = new TableInfoDTO();
        cond.setTableinfo_bussinessid(businessId);
        TableInfo tableInfo = tableInfoService.findBasicOne(cond);
        Boolean A;
        if (null == tableInfo){
            tableInfo = new TableInfo();
            A = true;
        }else{
            A = false;
        }

        //state 0:未处理 1:已处理
        dealinfo.setAppdealDealstate(0);
        //自动绑定 智能客服
        dealinfo.setAppdealDealername("zhinengkefu");
        //处理结果 未处理 0  已处理 1
        dealinfo.setAppdealResult(0);
        dealinfo.setAppdealRecorderid(task.getId());
        //未处理结果详情 waiting
        dealinfo.setAppdealResulttext("等待处理");
        dealinfo.setAppdealContent("等待处理");
        dealinfo.setAppdealVarparamb("0");
        // 占用备用字段 绑定局域网ip
        try {
            InetAddress   address = InetAddress.getLocalHost();
            String hostAddress = address.getHostAddress();
            dealinfo.setAppdealVarparama(hostAddress);
            tableInfo.setTableinfo_varparama(hostAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
            dealinfoService.insert(dealinfo);

            tableInfo.setTableinfo_bussinessid(Long.valueOf(task.getBusinessId()));
            tableInfo.setTableinfo_dealid(dealinfo.getId());
            //问题件类型
            tableInfo.setTableinfo_problemtype(problem.getProblemparts_type());
            //电话 0  短信 1
            tableInfo.setTableinfo_dealtype(dealinfo.getAppdealDealtype());
            // 未完成 0  完成1
            tableInfo.setTableinfo_dealstate(dealinfo.getAppdealDealstate());
            tableInfo.setTableinfo_uptabid(dealinfo.getId());
            tableInfo.setTableinfo_dealername("zhinengkefu");
            tableInfo.setTableinfo_recorderid(task.getId());
            tableInfo.setTableinfo_result(dealinfo.getAppdealResult());
            if (A) {
                //只需新增的时候填入，后面无需修改（流程历史创建日期用到）
                tableInfo.setTableinfo_dealcreatedate(new Timestamp(new Date().getTime()));
                tableInfoService.insert(tableInfo);
            } else {
                tableInfoService.update(tableInfo);
            }
        }

    //0571-63473223 去除'0'跟'-'、空格 变成 57163473223
    public static String FilterPhoneNumber(String phoneNumber) {
        if(phoneNumber.contains("-")){
            String trim = phoneNumber.replaceAll(" ","");
            String[] split = trim.split("-");
            trim = split[0].replaceAll("0", "");
            trim += split[1];
            return trim;
        }
        String trim = phoneNumber.replaceAll(" ","");
        return trim;
    }

    /**
     * 发送短信
     */
    public static  void sms(Context context, Task task){
        Long business_id = Long.valueOf(task.getBusinessId());
        Problem problem = problemService.getByProblemId(business_id);
        String type = "未定义";//type-问题件类型
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
        String receivename = problem.getProblemparts_receivename();//收件人名字
        String receivephone = problem.getProblemparts_receivephone();//收件人电话

        Long expressnumber = problem.getProblemparts_expressnumber();//运单号
        String sendphone = problem.getProblemparts_sendphone();//发件人电话
        String sendname = problem.getProblemparts_sendname();//发件人姓名

        String modal = "";

        String[] data = new String[4];//[0发件人姓名][1运单号][2问题类型][3收件人姓名]
        data[0] = sendname;
        data[1] = String.valueOf(expressnumber);
        data[2] = type;
        data[3] = receivename;

        String messageNumber = "";
        String currentNode =task.getTaskName();
        String messageType = "";
        if(currentNode.contains("发送短信")){
            //发送短信
            if(problem.getProblemparts_type() == 1){//签收未收到的短信
                if(currentNode.contains("收件人发送短信是否签收")){
                    modal= "262560";
                    messageType = "询问收件人: " + receivename + ",是否收到快递的通知短信";
                    messageNumber = receivephone;
                }else{
                    modal = "256866";//给发件人发送通知短信，询问是否收到
                    messageType = "询问发件人: " + sendname + ",是否收到快递的通知短信";
                    messageNumber = sendphone;
                }
            }if(problem.getProblemparts_type() == 2){//破损的短信，是bean发送，不用人工任务来发送
                modal = "260776";//告知发件人收件人无法打通
                messageType = "告知发件人: " + sendname + ",收件人: " + receivename +",无法打通的短信";
                messageNumber = sendphone;
            }if(problem.getProblemparts_type() == 3){//改地址
                if(currentNode.contains("签收")){
                    modal = "256866";//询问是否签收
                    messageType = "询问发件人: "+ sendname+",收件人是否收到快递的通知短信";
                    messageNumber = sendphone;
                }else{
                    modal = "259585";//如何问题类型是改地址就发送 询问发件人是否改地址的短信
                    messageType = "询问发件人: " +sendname+ ",收件人是否改地址的短信";
                    messageNumber = sendphone;
                }
            }if(problem.getProblemparts_type() == 4){//退回
                if(currentNode.contains("签收")){
                    modal = "256866";//询问是否签收
                    messageType = "询问发件人: " + sendname +",是否收到快递的通知短信";
                    messageNumber = sendphone;
                }else{
                    modal = "262060";//询问发件人是否退回
                    messageType = "询问发件人: " + sendname+ ",是否退回的短信";
                    messageNumber = sendphone;
                }
            }if(problem.getProblemparts_type() == 5){//催单
                if(currentNode.contains("签收")){
                    if(currentNode.contains("收件人")){
                        modal = "262560";//给发件人发送通知短信，询问是否收到
                        messageType = "询问收件人: " + receivename + ",是否收到快递的通知短信";
                        messageNumber = receivephone;
                    }else{
                        modal = "256866";//给发件人发送通知短信，询问是否收到
                        messageType = "询问发件人: " + sendname+ ",是否收到快递的通知短信";
                        messageNumber = sendphone;
                    }

                }else{
                    modal = "262562";//询问发件人是否知道签收日期
                    messageType = "询问发件人: " + sendname+ ",是否是否知道签收日期的短信";
                    messageNumber = sendphone;
                }
            }

            SmsMiddleTable smsMiddleTable = new SmsMiddleTable();
            smsMiddleTable.setPhone_number(messageNumber);
            smsMiddleTable.setBusiness_id(task.getBusinessId());
            smsMiddleTable.setTask_id(task.getId());
            smsMiddleTableRepository.insert(smsMiddleTable);
            sendMessage(messageNumber,data,modal);

            Long businessId = Long.valueOf(business_id);
            TableInfoDTO ti = new TableInfoDTO();
            ti.setTableinfo_bussinessid(businessId);
            TableInfo tableInfo = tableInfoService.findOne(ti);
            Long dealid = tableInfo.getTableinfo_dealid();

            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(date);
            //这里不需要增加dealinfo 因为任务固定会加
            //insertDealInfo(businessId,dealid,1,new String(time+"向客户电话为："+messageNumber+",发送了一条"+messageType),7,problemPartsType);

        }

    }
    public static void insertDealInfo (Long business_id_,Long appDealId, Integer state,String option,Integer Result,Integer problemPartsType) {
        Dealinfo dealinfo =  new Dealinfo();
        dealinfo.setAppdealDealername("zhinengkefu");
        dealinfo.setAppdealTabid(business_id_);
//        dealinfo.setAppdealDealtype(1);
        dealinfo.setId(appDealId);
        dealinfo.setAppdealProblemtype(problemPartsType);
        dealinfo.setAppdealDealstate(state);// 1:表示该条记录已被执行过
        dealinfo.setAppdealContent(option);//详细的操作过程
        dealinfo.setAppdealResult(Result);//1:表示打通 2:表示没打通 7:短信发送成功
        dealinfo.setAppdealVarparamb("0");
        String Resulttext ="";
        if(Result == 7){
            Resulttext = "已发送短信";
        }else{
            Resulttext = "发送错误";
        }
        dealinfo.setAppdealResulttext(Resulttext);//Result的中文对照
        DateTime dateTime = new DateTime();
        dealinfo.setAppdealDealcreatedate(dateTime.toTimestamp());
//        dealinfoService.updateIgnore(dealinfo);
        dealinfoService.insert(dealinfo);
    }


    private static void sendMessage(String messageNumber, String[] data,String modal) {
        boolean flag = true;
        while (flag) {
            String s = messageService.sendMessage(messageNumber, modal, data);
            if (s.equals("0")) {
                //发送请求成功
                flag = false;
            }else{
                //发送失败重新发送
                sendMessage( messageNumber,  data, modal);
            }
        }
    }
}
