//package com.ifrabbit.nk.flow.process.utils;
//
//import com.bstek.uflo.model.task.Task;
//import com.ifrabbit.nk.express.domain.*;
//import com.ifrabbit.nk.express.service.*;
//import com.ifrabbit.nk.express.utils.TimeUtil;
//import com.ifrabbit.nk.mq.producer.CallBackProducer;
//import com.ifrabbit.nk.usercenter.domain.Company;
//import com.ifrabbit.nk.usercenter.service.CompanyService;
//import com.ifrabbit.nk.usercenter.service.UserReportService;
//import ir.nymph.date.DateTime;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.sql.Timestamp;
//import java.util.Calendar;
//import java.util.Date;
//
///**
// * @Auther: WangYan
// * @Date: 2018/8/25 10:44
// * @Description:
// */
//@Component
//public class CallTasks {
//    @Autowired
//    private ProblemService tempProblemService;
//    @Autowired
//    private UserReportService tempUserReportService;
//    @Autowired
//    private TableInfoService tempTableInfoService;
//    @Autowired
//    private DealinfoService tempDealinfoService;
//    @Autowired
//    private CallDetailService tempCallDeatilService;
//    @Autowired
//    private CallBackProducer tempCallBackProducer;
//    @Autowired
//    private CompanyService tempCompanyService;
//    @Autowired
//    private ExpressInfoDetailService tempexpressInfoDetailService;
//    @Autowired
//    private ExpressInfoRecordService tempExpressInfoRecordService;
//
//
//    private static UserReportService userReportService;
//    private static  ProblemService problemService;
//    private static TableInfoService tableInfoService;
//    private static DealinfoService dealinfoService;
//    private static CallDetailService callDetailService;
//    private static CallBackProducer callBackProducer;
//    private static CompanyService companyService;
//    private static ExpressInfoDetailService expressInfoDetailService;
//    private static ExpressInfoRecordService expressInfoRecordService;
//
//    @PostConstruct
//    public void init(){
//        problemService = tempProblemService;
//        userReportService = tempUserReportService;
//        tableInfoService = tempTableInfoService;
//        dealinfoService = tempDealinfoService;
//        callDetailService = tempCallDeatilService;
//        callBackProducer = tempCallBackProducer;
//        companyService = tempCompanyService;
//        expressInfoDetailService = tempexpressInfoDetailService;
//        expressInfoRecordService  = tempExpressInfoRecordService;
//    }
//    //拨打收件人电话任务
//    public static void callReceicer(Task task){
//        Problem problem = getProblem(task.getBusinessId());
//        CallDetail callDetail= new CallDetail();
//        DateTime dateTime = new DateTime();
//        String prevTask = task.getPrevTask();//该任务的前一个任务
//        String insideItem = problem.getProblemparts_insideitem();//内物
//        String siteId = "";
//        String phoneName = "";
//        Integer tag = problem.getProblemparts_callcompanycount();//隔天标志
//        DateTime d = null;
//        Integer callNumber = 0;
//        if(tag==1||tag==0) {//非隔天
//            Integer tempTime = Integer.parseInt(userReportService.getParameter("cbsjjg"));
//            if(prevTask.contains("拨打收件人")){
//                long l = dateTime.getTime() + 1000 * tempTime * 60;
//                d = new DateTime(l);
//            }else {
//                d = new DateTime();
//            }
//        }else if(tag==2) {//隔天
//            Calendar calendar = dateTime.toCalendar();
//            calendar.add(calendar.DATE,1);
//            calendar.set(calendar.HOUR_OF_DAY,9);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//            calendar.set(Calendar.MILLISECOND, 0);
//            d = new DateTime(calendar.getTimeInMillis());
//        }
//        Timestamp timestamp = d.toTimestamp();
//        siteId = userReportService.getParameter("bdsjrIVRid");
//        if (problem.getProblemparts_type() == 2){
//            siteId = userReportService.getParameter("psbdsjrIVRid");
//        }
//        if (problem.getProblemparts_obtainstate() == 0) {
//            insideItem = "您购买的" + insideItem ;
//        } else {
//            insideItem = "这是第" + (problem.getProblemparts_obtainstate()+1) + "天拨打您的电话,您购买的" + insideItem;
//        }
//        phoneName = problem.getProblemparts_receivename();//收件人姓名
//        String phoneNumber = problem.getProblemparts_receivephone();
//        String nPhone = FilterPhoneNumber(phoneNumber);
//        callDetail.setCalldetail_callnumber(callNumber);
//        callDetail.setCalldetail_phonenumber(phoneNumber);
//        callDetail.setCalldetail_callphonenumber(nPhone);
//        callDetail.setCalldetail_calltype(1);
//        callDetail.setCalldetail_name(phoneName);
//        callDetail.setCalldetail_ivrid(siteId);
//        callDetail.setCalldetail_inside_item(insideItem);
//        insertData(callDetail,problem,timestamp,task);//插入数据
//    }
//
//    //拨打网点电话
//    public static void callNetwork(Task task){
//        Problem problem = getProblem(task.getBusinessId());
//        Integer nodeCallTimes = problem.getProblemparts_callrecipientscount();//已拨打电话次数
//        Integer uncollectedDays = problem.getProblemparts_uncollecteddays();
////        Integer companyId = problem.getProblemparts_callsendercount();//其他逻辑中需要注意处理拨打网点的id
//        String prevTask = task.getPrevTask();
//        CallDetail callDetail= new CallDetail();
//        DateTime dateTime = new DateTime();
//        String siteId = "";
//        siteId =   userReportService.getParameter("bdwdIVRid");
//        String insideItem = problem.getProblemparts_insideitem();//内物
//        String expressNumber = String.valueOf(problem.getProblemparts_expressnumber());//运单号
//        String x = ",";//定义一个单号的语音播报间隔
//        String voiceNumber = expressNumber.substring(0,4)+x+expressNumber.substring(4,8)+x+expressNumber.substring(8,12)+x;
//        Company company = null;
//        String phoneNumber = "";
//        Integer callNumber = 0;
//            switch (problem.getProblemparts_type()) {
//                case 1:
//                    insideItem = "查件,签收未收到,单号,"+voiceNumber;
//                    break;
//                case 2:
//                    insideItem = "破损件,非本人签收,内外都破损";
//                    break;
//                case 3:
//                    ExpressInfoRecord expressInfoRecord = expressInfoRecordService.findExpressNumber(expressNumber);
//                    Integer expresstype = expressInfoRecord.getExpress_type();
//                    if (expresstype == 50){
//                        if(uncollectedDays == 0) {
//                            insideItem = "查件,改地址，" + voiceNumber;
//                        }else {
//                            insideItem = "查件,改地址，"+voiceNumber+"，麻烦尽快改出";
//                        }
//                    }else if (expresstype == 20){
//                        if (uncollectedDays == 0){
//                            insideItem = "查件,签收未收到改地址，" + voiceNumber;
//                        }else {
//                            insideItem = "查件,签收未收到改地址，" + voiceNumber+"，麻烦尽快改出";
//                        }
//                    }
//                    break;
//                case 4:
//                    if (uncollectedDays==0){
//                        insideItem = "查件,退回,"+ voiceNumber;
//                    }else {
//                        insideItem = "查件,退回,"+ voiceNumber +",麻烦尽快退回,";
//                    }
//                    break;
//                case 5:
//                    insideItem = "查件,催件,"+voiceNumber+",麻烦尽快派件";
//                    break;
//            }
//            String nodeName =task.getNodeName();
//            if (nodeName.contains("网点电话")&&!nodeName.contains("上级A")){
//            //获取派件网点网点信息
//            ExpressInfoDetail expressInfoDetail = expressInfoDetailService.findByType(3, expressNumber);
//            company = companyService.get(Long.valueOf(expressInfoDetail.getBegin_companyid()));
//            }else{
//                Integer companyID = problem.getProblemparts_callsendercount();
//                company = companyService.get(Long.valueOf(companyID.toString()));
//            }
//            String tel1= company.getCompany_tel();
//            String tel2=company.getCompany_tel_2();
//            String tel3=company.getCompany_tel_3();
//            int telNumber=0;
//            if(null !=tel1 && !tel1.isEmpty()&& !"无".equals(tel1)) {telNumber++;}
//            if(null !=tel2 && !tel2.isEmpty()&& !"无".equals(tel2)) {telNumber++;}
//            if(null !=tel3 && !tel3.isEmpty()&& !"无".equals(tel3)) {telNumber++;}
//
//            //电话拨打次数
//            callNumber = telNumber*3 - 1;
//                //当前网点已经打了几次了（nodeCallTimes）
//            int i = nodeCallTimes % telNumber;
//            if (i == 0 && nodeCallTimes != 0){
//                //完成了一轮
//                Integer tempTime = Integer.parseInt(userReportService.getParameter("cbsjjg"));
//                long l = dateTime.getTime() + 1000 * tempTime * 60;
//                dateTime = new DateTime(l);
//            }
//            int tel = nodeCallTimes % telNumber;
//            if(tel==1 && !tel2.isEmpty()){
//                phoneNumber = tel2;
//            }else if (tel == 2 && !tel3.isEmpty()){
//                phoneNumber = tel3;
//            }else {
//                phoneNumber = tel1;
//            }
//
//        String qymndh = userReportService.getParameter("qymndh");
//        if ("true".equals(qymndh)) {
//            phoneNumber = problem.getProblemparts_receivephone();//默认联系人
//        }
//        String nPhone = FilterPhoneNumber(phoneNumber);
//        callDetail.setCalldetail_callnumber(callNumber);
//        callDetail.setCalldetail_phonenumber(phoneNumber);
//        callDetail.setCalldetail_callphonenumber(nPhone);
//        callDetail.setCalldetail_calltype(2);//区分拨打收件人还是网点电话  1 是收件人 2 是网点
//        callDetail.setCalldetail_name(company.getCompany_name());
//        callDetail.setCalldetail_companyid(company.getId());
//        callDetail.setCalldetail_ivrid(siteId);
//        callDetail.setCalldetail_inside_item(insideItem);
//        callDetail.setCalldetail_ip(GetLocalHost.getIp());//放入本机ip,用于只筛选出属于自己ip的任务
//        insertData(callDetail,problem,dateTime.toTimestamp(),task);// 插入数据
//    }
//
//    //拨打上海总中心电话(拨打上级已经变成人工任务)
////    public static void callCenter(Task task){
////        Problem problem = getProblem(task.getBusinessId());
////        DateTime dateTime = new DateTime();
////        Integer tempTime = Integer.parseInt(userReportService.getParameter("cbsjjg"));
////        long l = dateTime.getTime() + 1000 * tempTime * 60;
////        dateTime = new DateTime(l);
////        insertData(problem,dateTime.toTimestamp(),task);
////    }
//
//
//    //查找问题件
//    public static Problem getProblem(String id){
//        long businessId = Long.valueOf(id);
//        Problem problem = problemService.get(businessId);
//        return problem;
//    }
//    public static void insertData( CallDetail callDetail,Problem problem,Timestamp timestamp,Task task){
//        Dealinfo dealinfo = new Dealinfo();
//        dealinfo.setAppdealProblemtype(problem.getProblemparts_type());
//        dealinfo.setAppdealDealtype(0);
//        dealinfo.setAppdealDealcreatedate(timestamp);
//        dealinfo.setAppdealTabid(problem.getId());
//        //state 0:未处理 1:已处理
//        dealinfo.setAppdealDealstate(0);
//        dealinfo.setAppdealVarparame(task.getTaskName());//任务节点名称
//        //自动绑定 智能客服
//        dealinfo.setAppdealDealername("zhinengkefu");
//        //处理结果 未处理 0  已处理 1
//        dealinfo.setAppdealResult(0);
//        dealinfo.setAppdealRecorderid(task.getId());
//        //未处理结果详情 waiting
//        dealinfo.setAppdealResulttext("等待处理");
//        dealinfo.setAppdealContent("等待处理");
//        /**
//         * tableInfo表单的更新(若无则创建),关联dealInfo
//         */
//        TableInfoDTO cond = new TableInfoDTO();
//        cond.setTableinfo_bussinessid(problem.getId());
//        TableInfo tableInfo = tableInfoService.findOne(cond);
//        Boolean A;
//        if (null == tableInfo){
//            tableInfo = new TableInfo();
//            A = true;
//        }else{
//            A = false;
//        }
//        dealinfo.setAppdealVarparamb("0");
//        try {
//            InetAddress address = InetAddress.getLocalHost();      // 占用备用字段 绑定局域网ip
//            String hostAddress = address.getHostAddress();
//            dealinfo.setAppdealVarparama(hostAddress);
//            tableInfo.setTableinfo_varparama(hostAddress);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        dealinfoService.insert(dealinfo);
//        tableInfo.setTableinfo_dealerid(task.getId());
//        tableInfo.setTableinfo_bussinessid(Long.valueOf(problem.getId()));
//        tableInfo.setTableinfo_dealid(dealinfo.getId());
//        //问题件类型
//        tableInfo.setTableinfo_problemtype(problem.getProblemparts_type());
//        //电话 0  短信 1
//        tableInfo.setTableinfo_dealtype(dealinfo.getAppdealDealtype());
//        // 未完成 0  完成1
//        tableInfo.setTableinfo_dealstate(dealinfo.getAppdealDealstate());
//        tableInfo.setTableinfo_uptabid(dealinfo.getId());
//        tableInfo.setTableinfo_dealername("zhinengkefu");
//        tableInfo.setTableinfo_recorderid(task.getId());
//        tableInfo.setTableinfo_result(dealinfo.getAppdealResult());
//        if (A) {
//            //只需新增的时候填入，后面无需修改（流程历史创建日期用到）
//            tableInfo.setTableinfo_dealcreatedate(TimeUtil.getTime(new Date()));
//            tableInfoService.insert(tableInfo);
//        } else {
//            tableInfoService.update(tableInfo);
//        }
//
//        //增加callDetail记录
//        // TODO: 2018/8/25 还缺少时间
//        String expressNumber = String.valueOf(problem.getProblemparts_expressnumber());//运单号
//        String x = ",";//定义一个单号的语音播报间隔
//        String voiceNumber = expressNumber.substring(0,4)+x+expressNumber.substring(4,8)+x+expressNumber.substring(8,12)+x;
//        callDetail.setCalldetail_taskid(task.getId());
//        callDetail.setCalldetail_expressnumber(expressNumber);
//        callDetail.setCalldetail_callexpressnumber(voiceNumber);
//        callDetail.setCalldetail_dealid(dealinfo.getId());
//        callDetail.setCalldetail_businessid(problem.getId());
//        callDetail.setCalldetail_state(0);
//        callDetail.setCalldetail_nodename(task.getTaskName());
//        callDetail.setCalldetail_time(timestamp);
//        callDetail.setCalldetail_tableid(problem.getId());//放入tableid
//        callDetail.setCalldetail_ip(GetLocalHost.getIp());
//        callBackProducer.send(callDetail, 6000);
//    }
//    //0571-63473223 去除'0'跟'-'、空格 变成 57163473223
//    public static String FilterPhoneNumber(String phoneNumber) {
//        if(phoneNumber.contains("-")){
//            String trim = phoneNumber.replaceAll(" ","");
//            String[] split = trim.split("-");
//            trim = split[0].replaceAll("0", "");
//            trim += split[1];
//            return trim;
//        }
//        String trim = phoneNumber.replaceAll(" ","");
//        return trim;
//    }
//}
