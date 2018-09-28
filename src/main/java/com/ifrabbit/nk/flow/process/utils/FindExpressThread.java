package com.ifrabbit.nk.flow.process.utils;

import com.bstek.uflo.service.TaskOpinion;
import com.bstek.uflo.service.TaskService;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.service.*;
import com.ifrabbit.nk.express.utils.BelongCalenderUtil;
import com.ifrabbit.nk.usercenter.domain.JobList;
import com.ifrabbit.nk.usercenter.domain.JobListDTO;
import com.ifrabbit.nk.usercenter.service.JobListService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import ir.nymph.date.DateTime;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/6
 * Time:14:36
 */
@Component
public class FindExpressThread extends Thread {
    @Autowired
    private ExpressInfoRecordService expressInfoRecordService;
    @Autowired
    private UserReportService userReportService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private ExpressInfoDetailService expressInfoDetailService;
    @Qualifier("uflo.taskService")
    @Autowired
    private TaskService decorator;
    @Autowired
    DealinfoService dealinfoService;
    @Autowired
    TableInfoService tableInfoService;
    @Autowired
    private JobListService jobListService;
    @Autowired
    private ExpressInfoTestService expressInfoTestService;

    private static TaskOpinion taskOpinion;
    public volatile boolean exit = false;

    @Override
    public void run() {
        while (!exit) {
            try {
                fandAll();
                Long interval = Long.valueOf(userReportService.getParameter("sjjg"));//时间间隔系统参数为60s
                long waitTime = interval * 1000;
                Thread.sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 找出全部状态值为0的,并属于自己ip地址的值，
     */
    private void fandAll() throws UnknownHostException {
        //获取本机ip地址
        String hostAddress = GetLocalHost.getIp();
        //设置查询条件
        ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
        expressInfoRecordDTO.setExpress_state(0);
        expressInfoRecordDTO.setExpress_ipaddress(hostAddress);

        //开始从数据库查找出符合条件500条数据
        query(expressInfoRecordDTO);
    }

    //开始从数据库查找出符合条件500条数据
    private void query(ExpressInfoRecordDTO cond) {
        List<ExpressInfoRecord> list = expressInfoRecordService.findAllExpressTasks(cond, new PageRequest(0,500));
        if (!list.isEmpty()) {
            for (ExpressInfoRecord expressInfoRecord : list) {
                Integer dealtype = expressInfoRecord.getExpress_dealtype();//分类
                String expressNumber = expressInfoRecord.getExpress_number();//问题件
                Long problemID = expressInfoRecord.getExpress_problemid();//问题件ID
                Long taskID = expressInfoRecord.getExpress_taskid();//任务ID
                String express_modal = expressInfoRecord.getExpress_modal();//解析类型
                Long tableID = expressInfoRecord.getExpress_tabid();//tableInfo表的主键ID
                Long dealID = expressInfoRecord.getExpress_dealid();//dealInfo表的主键ID
                Long processinstanceID = expressInfoRecord.getExpress_processinstanceid();
                //查找系统间隔参数
                Integer interval = Integer.valueOf(userReportService.getParameter("W2"));
                Integer anotherDayTime = Integer.valueOf(userReportService.getParameter("W3"));

                String resultBoolean = null;
                Timestamp timestamp = new DateTime().toTimestamp();
                DateTime dateTime = new DateTime();
                //判断是否查出物流时间范围
                Boolean overtime = getaOverTimeBoolean();
                //如果在查询时间范围内，并且
                if(overtime) {
                    //没有超时
                     if (express_modal.equals("normal")){
                            //是否有错误的网点名
                            String hasErrorName = expressInfoDetailService.expressAnalysis(expressInfoRecord,expressNumber, problemID, 1,taskID);
                            String options = "";
                            Integer express_type;
                            Integer intResult = 0;
                            if(hasErrorName.equals("")){
                                ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
                                expressInfoRecordDTO.setExpress_taskid(taskID);
                                ExpressInfoRecord expressInfoRecordServiceOne = expressInfoRecordService.findOne(expressInfoRecordDTO);

                                Integer express_updatestate = expressInfoRecordServiceOne.getExpress_updatestate();//是否更新
                                express_type = expressInfoRecordServiceOne.getExpress_type();//物流状态
                                if (express_type == 20) {
                                    intResult = 51;
                                    options = "智能客服在" + new DateTime() + "查询物流信息，物流已签收";
                                    updateDealInfo(dealID,tableID,1, options, intResult);
                                }else if(express_type == 50){
                                    intResult = 53;
                                    options = "智能客服在" + new DateTime() + "查询物流信息，物流派件中";
                                    updateDealInfo( dealID,tableID,1, options, intResult);
                                }else if(express_updatestate == 20){
                                    options = "智能客服在" + new DateTime() + "查询物流信息，物流停滞";
                                    updateDealInfo( dealID,tableID,1,options, intResult);
                                    intResult = isUpdate(problemID,54);
                                    if(intResult == 0){
                                        intResult = 54;
                                    }
                                }
                            }else{
                                if(hasErrorName.equals("无揽收记录")){
                                    intResult = 52;
                                }else{
                                    intResult = 57;//有错误的网点名
                                }
                            }
                            //0表示没有结果
                            if(intResult == 0) {
                                //如果查询不到结果，就新创建一个2小时后的查询任务继续查询
                                updateMission(expressInfoRecord, dateTime, interval);
                                //插入 物流记录测试的值
                                insertExpressInfoTest(expressInfoRecord, dealtype, expressNumber, problemID, express_modal, timestamp, intResult,processinstanceID);
                                return;
                            }

                        //插入 物流记录测试的值
                         insertExpressInfoTest(expressInfoRecord, dealtype, expressNumber, problemID, express_modal, timestamp, intResult,processinstanceID);
                        //设置expressInfoRecord的状态值和查询时间
                         setResult(expressInfoRecord, timestamp, intResult);
                         //更新JobList
                         updateJobList(dealID,intResult);
                         closeFlowTask(options, taskID);
                     }else if(express_modal.equals("return")){
                        //是否退回
                        resultBoolean = userReportService.getParameter("qymnwl");
                        String result = null;String option = "";Integer intresult = 0;
                        if (!"true".equals(resultBoolean)) {
                            result = expressInfoDetailService.expressAnalysis(expressInfoRecord,expressNumber, problemID, 6,taskID);//是否显示退回
                            option = "智能客服在" + new DateTime() + "完成了查询物流是否退回的任务";
                            if (result.equalsIgnoreCase("否")) {//是否显示退回
                                intresult = isUpdate(problemID,63);//未退回
                            } else {
                                intresult = 61;//显示退回
                            }
                            updateDealInfo( dealID,tableID,1, option, intresult);
                        }
                        if(intresult == 0){
                            //如果查询不到结果，就新创建一个1天后8:30的查询任务继续查询
                            updateMission(expressInfoRecord,dateTime,anotherDayTime);
                            //插入 物流记录测试的值
                            insertExpressInfoTest(expressInfoRecord, dealtype, expressNumber, problemID, express_modal, timestamp, intresult,processinstanceID);
                            return;
                        }
                         //插入 物流记录测试的值
                         insertExpressInfoTest(expressInfoRecord, dealtype, expressNumber, problemID, express_modal, timestamp, intresult,processinstanceID);
                         setResult(expressInfoRecord, timestamp, intresult);//未退回
                         updateJobList(dealID,intresult);
                         closeFlowTask(option, taskID);
                    }else if(express_modal.equals("change")){
                         resultBoolean = userReportService.getParameter("qymnwl");
                         String result = null;Integer intresult = 0;String option = "";
                         if (!"true".equals(resultBoolean)) {
                             result = expressInfoDetailService.expressAnalysis(expressInfoRecord,expressNumber, problemID, 8,taskID);//是否显示改地址
                             option = "智能客服在" + new DateTime() + "完成了查询物流是否改地址的任务";
                             if (result.equalsIgnoreCase("否")) {//是否显示改地址
                                 intresult = isUpdate(problemID,62);//未改地址
                             } else {
                                 intresult = 60;//显示改地址
                             }
                             updateDealInfo( dealID,tableID,1, option, intresult);
                         }
                         if(intresult == 0){
                             //如果查询不到结果，就新创建一个1天后8:30的查询任务继续查询
                             updateMission(expressInfoRecord,dateTime,anotherDayTime);
                             //插入 物流记录测试的值
                             insertExpressInfoTest(expressInfoRecord, dealtype, expressNumber, problemID, express_modal, timestamp, intresult,processinstanceID);
                             return;
                         }
                         //插入 物流记录测试的值
                         insertExpressInfoTest(expressInfoRecord, dealtype, expressNumber, problemID, express_modal, timestamp, intresult,processinstanceID);
                         setResult(expressInfoRecord, timestamp, intresult);
                         updateJobList(dealID,intresult);
                         closeFlowTask(option, taskID);
                     }else{//没有符合的解析类型
                         expressInfoRecord.setExpress_state(9);//9代表类型错误，不会再被轮询出来
                         expressInfoRecordService.updateIgnore(expressInfoRecord);
                     }
                    }else{//超出时间范围
                    //就新创建一个查询任务第二天8:30继续查询
                    updateMission(expressInfoRecord,dateTime,anotherDayTime);
                    return;
                }
            }
        }
    }

    private void insertExpressInfoTest(ExpressInfoRecord expressInfoRecord, Integer dealtype, String expressNumber, Long problemID, String express_modal, Timestamp timestamp, Integer intResult,Long processinstanceID) {
        ExpressInfoTest expressInfoTest = new ExpressInfoTest();
        expressInfoTest.setExpress_processinstanceid(processinstanceID);
        expressInfoTest.setExpress_number(expressNumber);//运单号
        expressInfoTest.setExpress_taskid(expressInfoRecord.getExpress_taskid());//设置任务ID
        expressInfoTest.setExpress_dealtype(dealtype);//dealtype
        expressInfoTest.setExpress_nodename(expressInfoRecord.getExpress_nodename());//节点名
        expressInfoTest.setExpress_querytime(timestamp);//查询时间
        expressInfoTest.setExpress_plantime(expressInfoRecord.getExpress_plantime());//计划查询时间
        expressInfoTest.setExpress_problemid(problemID);//问题件ID
        expressInfoTest.setExpress_result(intResult);//解析的结果值
        expressInfoTest.setExpress_modal(express_modal);//物流解析类型
        expressInfoTest.setExpress_state(1);//完成的状态1=完成
        expressInfoTest.setExpress_ipaddress(expressInfoRecord.getExpress_ipaddress());//主机地址
        expressInfoTestService.insert(expressInfoTest);
    }

    private Integer isUpdate(Long problemID,Integer value) {
        Integer intResult;//根据businessID,获取到全部的ExpressInfoRecord
        ExpressInfoRecordDTO dto = new ExpressInfoRecordDTO();
        dto.setExpress_state(1);
        dto.setExpress_problemid(problemID);
        List<ExpressInfoRecord> expressInfoRecordServiceAll = expressInfoRecordService.findAll(dto);

        int size = expressInfoRecordServiceAll.size();

        if(size <= 1){
            return value;
        }

        //取最后一个对象
        ExpressInfoRecord expressInfoRecord1 = expressInfoRecordServiceAll.get(size - 1);
        //取最后第二个对象
        ExpressInfoRecord expressInfoRecord2 = expressInfoRecordServiceAll.get(size- 2);

        //最后一次查询时间
        Timestamp express_querytime1 = expressInfoRecord1.getExpress_querytime();
        //最后第二次查询时间
        Timestamp express_querytime2 = expressInfoRecord2.getExpress_querytime();
        //最后一次的停滞网点
        String stagnationCompanyName1 = expressInfoRecord1.getExpress_stagnationcompanyname();
        //最后第二次的停滞网点
        String stagnationCompanyName2 = expressInfoRecord2.getExpress_stagnationcompanyname();

        long time = express_querytime1.getTime();
        long time1 = express_querytime2.getTime();
        //2天相差的毫秒值
        long differenceValue  = time - time1;

        //获取判断停滞超过时效（单位天）
        Integer prescription = Integer.valueOf(userReportService.getParameter("W11"));

        if( (differenceValue / 86400000) >= prescription && stagnationCompanyName1.equals(stagnationCompanyName2))
            intResult = 54;//超时
        intResult = 56;//未超时
        return intResult;
    }

    //如果查询不到结果，就新创建一个2小时后的查询任务继续查询
    private void updateMission(ExpressInfoRecord expressInfoRecord, DateTime dateTime, Integer num) {
        ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
        expressInfoRecordDTO.setExpress_processinstanceid(expressInfoRecord.getExpress_processinstanceid());
        expressInfoRecordDTO.setExpress_nodename(expressInfoRecord.getExpress_nodename());
        expressInfoRecordDTO.setExpress_state(1);
        List<ExpressInfoRecord> expressInfoRecordServiceAll = expressInfoRecordService.findAll(expressInfoRecordDTO);
        if(expressInfoRecordServiceAll.size() != 0){
            ExpressInfoRecord expressInfoRecord1 = expressInfoRecordServiceAll.get(expressInfoRecordServiceAll.size() - 1);
            expressInfoRecord.setExpress_lasttime(expressInfoRecord1.getExpress_querytime());//放入上一次查询的时间
        }

        Timestamp timestampNew;
        //设置计划查询时间为 2小时后开始查询
        timestampNew = GetTimeAfterDay(dateTime,num);
        expressInfoRecord.setExpress_plantime(timestampNew);//放入设定的时间
        expressInfoRecordService.updateIgnore(expressInfoRecord);
    }

    private void updateJobList(Long dealID,Integer result) {
        JobListDTO jobListDTO = new JobListDTO();
        jobListDTO.setJoblistDealid(dealID);
        JobList jobList = jobListService.findOne(jobListDTO);
        jobList.setJoblistState(1);
        jobList.setJoblistResult(result);
        jobListService.updateIgnore(jobList);
    }

    //设置完成查询后的状态和时间
    private void setResult(ExpressInfoRecord expressInfoRecord, Timestamp timestamp, Integer result) {
        ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
        expressInfoRecordDTO.setExpress_processinstanceid(expressInfoRecord.getExpress_processinstanceid());
        expressInfoRecordDTO.setExpress_state(1);
        List<ExpressInfoRecord> expressInfoRecordServiceAll = expressInfoRecordService.findAll(expressInfoRecordDTO);
        if(expressInfoRecordServiceAll.size() != 0){
            ExpressInfoRecord expressInfoRecord1 = expressInfoRecordServiceAll.get(expressInfoRecordServiceAll.size() - 1);
            expressInfoRecord.setExpress_lasttime(expressInfoRecord1.getExpress_querytime());//设置上一次查询时间
        }

        expressInfoRecord.setExpress_state(1);//已处理
        expressInfoRecord.setExpress_result(result);//设置结果
        expressInfoRecord.setExpress_querytime(timestamp);//设置实际查询时间

        //更新数据
        expressInfoRecordService.updateIgnore(expressInfoRecord);
    }

    //设置计划查询时间为 2小时后开始查询(以系统参数为准),或者第二天9点
    private Timestamp GetTimeAfterDay(DateTime dateTime,Integer num) {
        Calendar calendar = null;
        //获取系统参数的间隔
        if(num == 2){
            Integer interval = Integer.valueOf(userReportService.getParameter("W2")); int intervalMinute = interval * 60;
            dateTime = new DateTime();
            calendar = dateTime.toCalendar();
            calendar.add(Calendar.MINUTE, intervalMinute);
        }else if(num == 9){
            calendar = dateTime.toCalendar();
            calendar.add(calendar.DATE,1);
            calendar.set(calendar.HOUR_OF_DAY,9);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        Timestamp timestamp = new DateTime(calendar.getTimeInMillis()).toTimestamp();
        return timestamp;
    }


    //判断是否超出查询时间范围
    private Boolean getaOverTimeBoolean() {
        String startTime = userReportService.getParameter("W4");
        String endTime = userReportService.getParameter("W5");

        //是否超时
        return BelongCalenderUtil.isBelong(startTime, endTime);
//        return true;
    }


    public void updateDealInfo(Long appDealId,Long tableID, Integer state, String option, Integer Result) {
        DateTime dateTime = new DateTime();
        Timestamp timestamp = dateTime.toTimestamp();
        Dealinfo dealinfo = new Dealinfo();
        dealinfo.setId(appDealId);
        dealinfo.setAppdealDealstate(state);// 1:表示该条记录已被执行过
        dealinfo.setAppdealContent(option);//详细的操作过程
        dealinfo.setAppdealResult(Result);//物流结果回填
        dealinfo.setAppdealDate(timestamp);//处理时间
        dealinfo.setAppdealResulttext("物流查询");//Result的中文对照
        dealinfoService.updateIgnore(dealinfo);

        TableInfo tableInfo = new TableInfo();
        tableInfo.setId(tableID);
        tableInfo.setTableinfo_tabid(dealinfo.getAppdealTabid());
        tableInfo.setTableinfo_content("物流查询");
        tableInfoService.updateIgnore(tableInfo);
    }

    public void closeFlowTask(String option, Long taskId) {
        taskOpinion = new TaskOpinion(option);
        decorator.start(taskId);
        decorator.complete(taskId, taskOpinion);
    }

}
