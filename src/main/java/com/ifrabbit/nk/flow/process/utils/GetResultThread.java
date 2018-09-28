package com.ifrabbit.nk.flow.process.utils;

import com.bstek.uflo.service.TaskOpinion;
import com.bstek.uflo.service.TaskService;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.service.*;
import com.ifrabbit.nk.mq.queue.PhoneResultSender;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Auther: WangYan
 * @Date: 2018/8/23 14:19
 * @Description:
 */
@Component
public class GetResultThread extends Thread {
    private static Logger logger = LoggerFactory.getLogger(GetResultThread.class);
    private final static int pageNum = 1;
    private final static int pageSize = 100;
    private final static String url = "https://open.duyansoft.com/api/v1/call_log?apikey=bBjhoF3DdLWYm5buafF0Fi4Gv5a0aGtA";
    @Autowired
    private CallDetailService tempCallDetailService;
    @Autowired
    private UserReportService tempUserReportService;
    @Autowired
    private ProblemService tempproblemService;
    @Qualifier("uflo.taskService")
    @Autowired
    private TaskService tempdecorator;
    @Autowired
    private TableInfoService temptableInfoService;
    @Autowired
    private DealinfoService tempdealinfoService;
    @Autowired
    public ExpressInfoDetailService tempexpressInfoDetailService;
    @Autowired
    private CompanyService tempcompanyService;
    @Autowired
    private AmqpTemplate tempRabbitTemplate;
    @Autowired
    private PhoneResultSender tempPhoneResultSender;

    private static TaskOpinion taskOpinion;
    private static CallDetailService callDetailService;
    private static UserReportService userReportService;
    private static ProblemService problemService;
    private static TaskService decorator;
    private static TableInfoService tableInfoService;
    private static DealinfoService dealinfoService;
    private static ExpressInfoDetailService expressInfoDetailService;
    private static CompanyService companyService;
    private static AmqpTemplate amqpTemplate;
    private static PhoneResultSender phoneResultSender;
    public volatile boolean exit = false;


    @PostConstruct
    public void init() {
        companyService = tempcompanyService;
        callDetailService = tempCallDetailService;
        userReportService = tempUserReportService;
        problemService = tempproblemService;
        decorator = tempdecorator;
        tableInfoService = temptableInfoService;
        dealinfoService = tempdealinfoService;
        expressInfoDetailService = tempexpressInfoDetailService;
        amqpTemplate = tempRabbitTemplate;
        phoneResultSender = tempPhoneResultSender;
    }
    @Override
    public void run(){
        //获得参数
        while (!exit) {
            String starttime = userReportService.getParameter("sysjc");
            Long interval = Long.valueOf(userReportService.getParameter("sjjg"));//时间间隔系统参数为60s
            long startTime =Long.valueOf(starttime) ;
            long endTime = startTime + interval * 1000;
            long waitTime = interval * 1000;
            userReportService.updateBycode(String.valueOf(endTime),"sysjc");
            try {
                parseData(startTime, endTime,pageNum,pageSize);
                Thread.sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // 解析通话结果 并放入到消息队列里面
    private void parseData(long startTime, long endTime ,int pageNum,int pageSize) {
        String s = url + "&page_num=" + pageNum + "&page_size=" + pageSize + "&start_time=" + startTime + "&end_time=" + endTime + "&include_vars=" + true;
        String request = CallUtil.getRequest(s);
        System.out.println(request);
//        //解析结果
//        logger.info("============================开始解析度言的数据=======================");
//        //获取解析对象
        JSONObject json = JSONObject.fromObject(request);
//        //根据key获取value
        String status = json.getString("status");
//        //判断状态是否为1，1表示正常，否则无消息或其他异常
        if (status.equals("1")) {
            //发送到消息队列
//            //获取data里的数据
            JSONObject data = json.getJSONObject("data");
//            //根据key获取list值
            JSONArray call_logs = data.getJSONArray("call_logs");
            if (call_logs == null || call_logs.size() == 0) {
                logger.debug("============================call_logs的值为空=====================");
                return;
            } else {
                int size = call_logs.size();
//                //创建实体类
                CallDetail callDetail = new CallDetail();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = call_logs.getJSONObject(i);
                    JSONObject vars = jsonObject.getJSONObject("vars");
                    if (vars.toString().contains("U_hostip")) {
                        if (!vars.getString("U_hostip").equals(GetLocalHost.getIp())){
                            continue;}
                            //uuid  网点+uline   结果存放 answerid   +详情信息
                        if (vars.toString().contains("U_vendor") && vars.toString().contains("U_dealid")) {
                            String u_vendor = vars.getString("U_vendor");
                            String dealid = vars.getString("U_dealid");
                            String tabid = vars.getString("U_tabid");
                            callDetail.setId(Long.valueOf(u_vendor));
                            callDetail.setCalldetail_tableid(Long.valueOf(tabid));
                            callDetail.setCalldetail_dealid(Long.valueOf(dealid));
                            String outcome = jsonObject.getString("outcome");
                            if (outcome.equalsIgnoreCase("SUCCESS")) {
                                String call_uuid = jsonObject.getString("call_uuid");
                                //************************  answer ==> call_uuid 如果是网点 则有线路
                                callDetail.setCalldetail_answer(call_uuid);
                                // 拨打收件人
                                if (vars.toString().contains("U_qianshou")) {
                                    String u_qianshou = vars.getString("U_qianshou");
                                    if (u_qianshou.equals("1")) {
                                        //****************** answerid  ==> 有收到 未收到(破损件为有责 无责)
                                        callDetail.setCalldetail_answerid(11);
                                    } else if (u_qianshou.equals("2")) {
                                        callDetail.setCalldetail_answerid(12);
                                    }
                                    //拨打网点
                                }else if (vars.toString().contains("U_message")) {
                                    String u_message = vars.getString("U_message");
                                    if (u_message.equals("1")) {
                                        if (vars.toString().contains("U_line")) {
                                            String u_line = vars.getString("U_line");
                                            if (u_line.equals("0")) {
                                                callDetail.setCalldetail_answerid(22);
                                            } else {
                                                callDetail.setCalldetail_answerid(21);
                                                callDetail.setCalldetail_answer(callDetail.getCalldetail_answer() + "/" + u_line);
                                            }
                                        } else {
                                            callDetail.setCalldetail_answerid(31);
                                        }
                                    } else {
                                        callDetail.setCalldetail_answerid(23);
                                    }
                                    //拨打破损收件人
                                }else if (vars.toString().contains("U_outside")){
                                    String u_outside = vars.getString("U_outside");
                                    String u_inside = vars.getString("U_inside");
                                    String u_selflf = vars.getString("U_selflf");
                                    String u_request = vars.getString("U_request");
                                    String u_asked = vars.getString("U_asked");
                                    if (StringUtils.isNotBlank(u_outside)&&StringUtils.isNotBlank(u_inside)&&StringUtils.isNotBlank(u_selflf)
                                            &&StringUtils.isNotBlank(u_request)&&StringUtils.isNotBlank(u_asked)&&u_asked != "0") {
                                        String param = u_outside + u_inside + u_selflf + u_request + u_asked;
                                        switch (param){
                                            case "21222":case "12222":case "21221":case "12221":case "21212": case "12212": case "11222": case "21211":
                                            case "12211": case "11221": case "11212": case "11211":
                                                callDetail.setCalldetail_answerid(13);
                                                callDetail.setCalldetail_result(Integer.parseInt(param));
                                                break;
                                            default:
                                                callDetail.setCalldetail_answerid(14);
                                                callDetail.setCalldetail_result(Integer.parseInt(param));
                                        }
                                    }else {
                                        callDetail.setCalldetail_answerid(31);
                                    }
                                } else {
                                    callDetail.setCalldetail_answerid(31);
                                }
                            }else {
                                Integer reason = unConReason(outcome);
                                callDetail.setCalldetail_answerid(reason);
                            }
                            phoneResultSender.phoneResultSender(callDetail,1000l);
                        }
                    }
                }
                if (size == 100) {
                    this.parseData(startTime, endTime, pageNum++, pageSize);
                }
            }
        }
    }
    public Integer unConReason(String check) {
        switch (check) {
            case "USER_BUSY":
                return 32;
            case "POWER_OFF":
                return 33;
            case "SUSPENDED":
//                return "停机";
                return  34;
            case "NOT_EXIST":
                return 35;
            default:
                return 36;
        }
    }
}
