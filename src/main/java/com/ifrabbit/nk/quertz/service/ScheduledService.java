//package com.ifrabbit.nk.quertz.service;
//
//import com.bstek.uflo.service.TaskOpinion;
//import com.ifrabbit.nk.express.domain.*;
//import com.ifrabbit.nk.express.repository.ExpressInfoRecordRepository;
//import com.ifrabbit.nk.express.service.DealinfoService;
//import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
//import com.ifrabbit.nk.express.service.ProblemService;
//import com.ifrabbit.nk.express.service.TableInfoService;
//import com.ifrabbit.nk.express.utils.TimeUtil;
//import com.ifrabbit.nk.flow.process.utils.HttpUtils;
//import com.ifrabbit.nk.flow.process.utils.TaskUtil;
//import com.ifrabbit.nk.flow.repository.VariableRepository;
//import com.ifrabbit.nk.flow.service.VariableService;
//import com.ifrabbit.nk.usercenter.domain.Company;
//import com.ifrabbit.nk.usercenter.service.AppProblempartsService;
//import com.ifrabbit.nk.usercenter.service.CompanyService;
//import com.ifrabbit.nk.usercenter.service.UserReportService;
//import ir.nymph.date.DateTime;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Component
//public class ScheduledService {
//    private static Logger logger = LoggerFactory.getLogger(ScheduledService.class);
//    @Autowired
//    private AppProblempartsService appProblempartsService;
//    @Autowired
//    private CompanyService companyService;
//    @Autowired
//    public ExpressInfoDetailService expressInfoDetailService;
//    @Autowired
//    private UserReportService userReportService;
//    @Autowired
//    private VariableService variableService;
//    @Autowired
//    private VariableRepository variableRepository;
//
//    @Autowired
//    private ExpressInfoRecordRepository expressInfoRecordRepository;
//
//    @Autowired
//    private ProblemService problemService;
//
//    private static TaskOpinion taskOpinion;
//
//    @Qualifier("uflo.taskService")
//    @Autowired
//    private com.bstek.uflo.service.TaskService decorator;
//
//    @Autowired
//    private TableInfoService tableInfoService;
//    @Autowired
//    private DealinfoService dealinfoService;
//
//    @Scheduled(cron = "0 0/2 * * * ?  ")
////   @Scheduled(cron = "0/20 * * * * ?  ")
//    public void scheduled() {
//         log.info("=====>>>>>使用cron  {}", System.currentTimeMillis());
//        //查询出当前需要执行的任务
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", "zhinengkefu");
//        try {
//            InetAddress address = InetAddress.getLocalHost();
//            String hostAddress = address.getHostAddress();
//            map.put("ip", hostAddress);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        List<Map<String, Object>> taskList = this.appProblempartsService.finduflotaskByParams(map);
//        // 根据不同的任务类型做相应的处理
//        for (int i = 0; i < taskList.size(); i++) {
//            Map<String, Object> dataMap = taskList.get(i);
//            Integer dealType = (Integer)dataMap.get("appdeal_dealtype");
//            Long taskId = (Long) dataMap.get("ID_");//任务id
//            String nodeName = (String) dataMap.get("NODE_NAME_");//环节名称
//            Integer problemType = Integer.parseInt(String.valueOf(dataMap.get("problemparts_type")));//问题件类型
//            String expressNumber = String.valueOf(dataMap.get("problemparts_expressnumber"));//运单号
//            Long business_id_ = Long.valueOf(String.valueOf(dataMap.get("BUSINESS_ID_")));//问题件ID
//            String insideItem = (String) dataMap.get("problemparts_insideitem");//内物
//            Long appdeal_id = (Long)dataMap.get("appdeal_id");
//            String x = ",";//定义一个单号的语音播报间隔
//            Integer nodeCallTimes = Integer.parseInt(String.valueOf(dataMap.get("problemparts_callrecipientscount")));//当天打了收件人几次电话
//            Integer uncollectedDays = Integer.parseInt(String.valueOf(dataMap.get("problemparts_uncollecteddays")));//流程循环几次一般循换3次
//
//            //更新AppdealDealstate 0代表可以被轮询，1代表已完成，3代表正在进行中，不会被轮询出来
//            Dealinfo dealinfo = dealinfoService.get(appdeal_id);
//            dealinfo.setAppdealDealstate(3);//设置成3,表示进行中,不会再被轮询出来
//            dealinfoService.updateIgnore(dealinfo);
//            logger.info("==============================更新AppdealDealstate的字段值为3=====================");
//
//            if (dealType == 2) {//物流
//                 nodeCallTimes = Integer.parseInt(String.valueOf(dataMap.get("problemparts_callrecipientscount")));//当天已经查询了几次物流
//                 uncollectedDays = Integer.parseInt(String.valueOf(dataMap.get("problemparts_uncollecteddays")));//流程循环几次一般循换3次
//                String processInstanceId = String.valueOf(dataMap.get("PROCESS_INSTANCE_ID_"));//流程实例id
//                deleteVariable(Long.valueOf(processInstanceId));
//                insertVariable(Long.valueOf(processInstanceId), "uncollectedDays", String.valueOf(uncollectedDays));
//                insertVariable(Long.valueOf(processInstanceId), "business_id_", String.valueOf(dataMap.get("BUSINESS_ID_")));
//                // TODO: 2018/7/11 这里需要改
//                if (nodeName.contains("是否有物流信息更新")) { //查看物流是否更新
//                    //记录更新前的json的size大小
//                    Integer beforeSize = Integer.valueOf(expressInfoDetailService.expressAnalysis(expressNumber, business_id_, 2));
//                    String qymnwl = userReportService.getParameter("qymnwl");
//                    if (!"true".equals(qymnwl)) {
//                        Integer afterSize = Integer.valueOf(expressInfoDetailService.expressAnalysis(expressNumber, business_id_, 2));
//
//                        //记录更新后的json的size大小
//                        String option = "智能客服在" + new DateTime() + "完成了物流查询任务";
//                        updateDealInfo(business_id_, Long.valueOf(String.valueOf(dataMap.get("appdeal_id"))), 1, option, 3, "");
//
//                        if (beforeSize == afterSize) {
//
//                            // TODO: 2018/8/2 多次之后才可以关闭
//                            Integer dtwlcxsc = Integer.parseInt(userReportService.getParameter("dtwlcxsc"));//一天物流最多查询几次
//                            if (nodeCallTimes >= dtwlcxsc - 1) {//当天查询的次数超过设定次数则直接结束当前任务
//                                //多个updateProblem  nodeCallTimes重置，dutyState重置   ObtainState+1  隔天标志更改状态false
//                                updateProblemNew(business_id_, 0, 0, 0, 0, null);
//                                closeFlowTask("查询完是否有物流信息更新任务，关闭该任务", "否", taskId);
//                            }else
//                            {
//                                updateProblemNew1(business_id_, nodeCallTimes + 1, 0);
//                                decorator.forward(taskId, nodeName);
//                            }
//                        } else {
//                            closeFlowTask("查询完是否有物流信息更新任务，关闭该任务", "是", taskId);
//                        }
//                    }
//                } else if(nodeName.contains("是否显示有改出")){
//
//                } else if(nodeName.contains("是否显示退回")){
//                    String qymnwl = userReportService.getParameter("qymnwl");
//                    if (!"true".equals(qymnwl)) {
//                        String result = expressInfoDetailService.expressAnalysis(expressNumber, business_id_, 6);//是否显示退回
//
//                        String option = "智能客服在" + new DateTime() + "完成了查询物流是否退回的任务";
//                        updateDealInfo(business_id_, Long.valueOf(String.valueOf(dataMap.get("appdeal_id"))), 1, option, 3, "");
//
//                        if (result.equalsIgnoreCase("否")) {//是否显示退回
//                            // TODO: 2018/8/2 多次之后才可以关闭
//                            Integer dtwlcxsc = Integer.parseInt(userReportService.getParameter("dtwlcxsc"));//一天物流最多查询几次
//                            if (nodeCallTimes >= dtwlcxsc - 1) {//当天查询的次数超过设定次数则直接结束当前任务
//                                //多个updateProblem  nodeCallTimes重置，dutyState重置   ObtainState+1  隔天标志更改状态false
//                                updateProblemNew(business_id_, 0, 0, 0, 0, null);
//                                closeFlowTask("完成了查询物流是否退回的任务，关闭该任务", "否", taskId);
//                            }else
//                            {
//                                updateProblemNew1(business_id_, nodeCallTimes + 1, 0);
//                                decorator.forward(taskId, nodeName);
//                            }
//                        } else {
//                            closeFlowTask("查询完是否有物流信息更新任务，关闭该任务", "是", taskId);
//                        }
//                    }
//                } else if (nodeName.contains("查询物流信息")) {
//                    String option = "智能客服在" + new DateTime() + "完成了物流查询任务";
//                    updateDealInfo(business_id_, Long.valueOf(String.valueOf(dataMap.get("appdeal_id"))), 1, option, 3, "");
//                    closeTask("完成24小时查询物流信息任务", taskId);
//                } else if (nodeName.contains("是否显示签收")) {
//                    Integer express_type =0;
//                    String qymnwl = userReportService.getParameter("qymnwl");
//                    if (!"true".equals(qymnwl)) {
//                         String expressType =expressInfoDetailService.expressAnalysis(expressNumber, business_id_, 4) ;
//                         if(!"".equals(expressType)){
//                             express_type=20;
//                         }
//                    }else{
//                        ExpressInfoRecord expressInfoRecord = expressInfoRecordRepository.findExpressNumber(expressNumber);
//                        express_type = expressInfoRecord.getExpress_type();
//                    }
//                    StringBuffer sb = new StringBuffer();
//
//                    if (express_type == 20) {
//                        String option = "智能客服在" + new DateTime() + "查询物流信息，物流已签收";
//                        sb.append("是");
//                        String to = String.valueOf(sb);
//                        closeFlowTask(option,to, taskId);
//                    } else {
//                        String option = "智能客服在" + new DateTime() + "查询物流信息，物流未签收";
//                        sb.append("否");
//                        String to = String.valueOf(sb);
//                        updateDealInfo(business_id_, Long.valueOf(String.valueOf(dataMap.get("appdeal_id"))), 1, option, 3, "");
//                        // TODO: 2018/8/2 多次之后才可以关闭
//                        Integer dtwlcxsc = Integer.parseInt(userReportService.getParameter("dtwlcxsc"));//一天物流最多查询几次
//                        if (nodeCallTimes >= dtwlcxsc - 1) {//当天查询的次数超过设定次数则直接结束当前任务
//
//                                //多个updateProblem  nodeCallTimes重置，dutyState重置   ObtainState+1  隔天标志更改状态false
//                            updateProblemNew(business_id_, 0, 0, 0, 0, null);
//                            closeFlowTask(option,to, taskId);
//
//                        }else
//                        {
//                            updateProblemNew1(business_id_, nodeCallTimes + 1, 0);
//    //                        }
//                            decorator.forward(taskId, nodeName);
//                        }
//                    }
//                }else if(nodeName.contains("揽收记录")){
//                    String qymnwl = userReportService.getParameter("qymnwl");
//                    if (!"true".equals(qymnwl)) {
////                        Integer integer = expressInfoDetailService.hasRecord(expressNumber, business_id_);
//                        StringBuffer sb = new StringBuffer();
////                        if(integer != 1){
////                            sb.append("否");
////                        }else{
////                            sb.append("是");
////                        }
//                    String option = "智能客服在" + new DateTime() + "完成了揽收记录查询任务";
//                    updateDealInfo(business_id_, Long.valueOf(String.valueOf(dataMap.get("appdeal_id"))), 1, option, 3, "");
//                    String to = String.valueOf(sb);
//                    taskOpinion = new TaskOpinion("完成了揽收记录查询任务");
//                    decorator.start(taskId);
//                    decorator.complete(taskId, to, taskOpinion);
//                    }
//                }
//            }
//        }
//    }
//
//
//
//    /**
//     * 在开始该次拨打电话任务之前 删掉该流程上一节点所有变量
//     * @param processInstanceId
//     */
//    public void deleteVariable(Long processInstanceId) {
//        TempVariable cond = new TempVariable();
//        cond.setProcessId(processInstanceId);
//        variableRepository.deleteByCondition(cond);
//    }
//
//    public void insertVariable(Long processId, String key, String value) {
//        TempVariable variable = new TempVariable();
//        variable.setProcessId(processId);
//        variable.setT_key(key);
//        variable.setT_value(value);
//        variableRepository.insert(variable);
//    }
//
//    public void closeTask(String option, Long taskId) {
//        taskOpinion = new TaskOpinion(option);
//        decorator.start(taskId);
//        decorator.complete(taskId, taskOpinion);
//    }
//
//    public void closeFlowTask(String option, String flow, Long taskId) {
//        taskOpinion = new TaskOpinion(option);
//        decorator.start(taskId);
//        decorator.complete(taskId, flow, taskOpinion);
//    }
//
//    public void updateDealInfo(Long business_id_, Long appDealId, Integer state, String option, Integer Result, String audioUrl) {
//        Dealinfo dealinfo = new Dealinfo();
//        dealinfo.setId(appDealId);
//        dealinfo.setAppdealDealstate(state);// 1:表示该条记录已被执行过
//        //dealinfo.setAppdealDealtype(0);//这个字段更新的时候不能为空
//        dealinfo.setAppdealContent(option);//详细的操作过程
//        dealinfo.setAppdealResult(Result);//1:表示打通 2:表示没打通 3：其他（拒绝等）
//        String Resulttext = "";
//        if (Result == 1) {
//            Resulttext = "电话打通";
//        } else if (Result == 2) {
//            Resulttext = "电话未打通";
//        } else if (Result == 3) {
//            Resulttext = "物流查询";
//        } else if(Result == 4){
//            Resulttext = "短信超出时效";
//        } else if(Result == 8){
//            Resulttext = "电话打通但无效";
//        } else if(Result == 12){
//            Resulttext = "电话打通但选择录音";
//        }else {
//            Resulttext = "其他";
//        }
//        dealinfo.setAppdealResulttext(Resulttext);//Result的中文对照
//        dealinfo.setAppdealAudiourl(audioUrl);//录音url
//        dealinfoService.updateIgnore(dealinfo);
//
//        TableInfoDTO cond = new TableInfoDTO();
//        cond.setTableinfo_bussinessid(business_id_);
//        TableInfo tableInfo = tableInfoService.findBasicOne(cond);
//        tableInfo.setTableinfo_dealid(dealinfo.getId());
//        tableInfo.setTableinfo_dealstate(dealinfo.getAppdealDealstate());
//        tableInfo.setTableinfo_uptabid(dealinfo.getId());
//        tableInfoService.updateIgnore(tableInfo);
//    }
//
//
//
//
//    public void updateProblemNew(Long business_id_, Integer nodeCallTimes,Integer DutyState,Integer ObtainState,Integer callcompanycount, Integer tag) {
//        Problem problem = new Problem();
//        problem.setId(business_id_);
//        if( nodeCallTimes != -1)  problem.setProblemparts_callrecipientscount(nodeCallTimes);
//        if( DutyState != -1) problem.setProblemparts_dutystate(DutyState);
//        if( ObtainState != -1) problem.setProblemparts_obtainstate(ObtainState);
//        problem.setProblemparts_callcompanycount(callcompanycount);
//        problemService.updateIgnore(problem);
//    }
//
//    public void updateProblemNew1(Long business_id_, Integer nodeCallTimes,Integer callcompanycount) {
//        Problem problem = new Problem();
//        problem.setId(business_id_);
////        if (null == tag) {
//            if( nodeCallTimes != -1)  problem.setProblemparts_callrecipientscount(nodeCallTimes);
//            problem.setProblemparts_callcompanycount(callcompanycount);
////        } else {
////            problem.setProblemparts_callcompanycount(tag);
////        }
//        problemService.updateIgnore(problem);
//    }
//}
