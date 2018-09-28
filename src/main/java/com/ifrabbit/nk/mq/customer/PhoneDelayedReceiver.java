package com.ifrabbit.nk.mq.customer;

import com.ifrabbit.nk.constant.Constant;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.repository.ExpressInfoRecordRepository;
import com.ifrabbit.nk.express.service.*;
import com.ifrabbit.nk.flow.process.utils.GetLocalHost;
import com.ifrabbit.nk.flow.process.utils.HandlerUtil;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.domain.JobList;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import ir.nymph.date.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/21
 * Time:11:05
 */

@Component
@RabbitListener(queues = Constant.PHONE_QUEUE_NAME)
public class PhoneDelayedReceiver {
    private static Logger logger = LoggerFactory.getLogger(PhoneDelayedReceiver.class);
    @Autowired
    private CallDetailService callDetailService;
    @Autowired
    DealinfoService dealinfoService;
    @Autowired
    TableInfoService tableInfoService;
    @Autowired
    ProblemService problemService;
    @Autowired
    ExpressInfoDetailService expressInfoDetailService;
    @Autowired
    UserReportService userReportService;
    @Autowired
    ExpressInfoRecordRepository expressInfoRecordRepository;
    @Autowired
    ExpressInfoRecordService expressInfoRecordService;
    @Autowired
    private CompanyService companyService;

    @RabbitHandler
    //用户处理joblist的解析
    public void process(JobList jobList) {
        //取参
        try {
            Long joblistProblemid = jobList.getJoblistProblemid();
            String nodeName = jobList.getJoblistDealname();
            Integer dealType = jobList.getJoblistDealtype();
            DateTime dateTime = new DateTime();
            Problem problem = problemService.get(joblistProblemid);
            Timestamp timestamp = null;
            // TODO: 2018/9/16 getJoblistRecord的长度需要固化
            String record = jobList.getJoblistRecord();
            if (dealType == 101) {
                String ts = "";
                String ivRid = "";
                String words = "";
                Integer callType = null;
                String callName = "";
                String phone = "";
                String callNum = "";
                if (record != null) {
                    //T1/R2/S2/N2    day-D3/count-C1/modal-M1
                    String[] records = record.split("/");
                    String[] day = records[0].split("-");
                    String[] count = records[1].split("-");
                    String[] modal = records[2].split("-");
                    String param = day[1] + count[1] + modal[1];
                    String s = modal[1];
                    String netWorkType = s.substring(1);
                    callType = Integer.parseInt(netWorkType);
                    switch (param) {
                        //不同type 对应不同参数
                        case "D1C1M1":
                            ts = userReportService.getParameter("T1");
                            ivRid = userReportService.getParameter("R1");
                            words = userReportService.getParameter("S1");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "D1C2M1":
                            ts = userReportService.getParameter("T2");
                            ivRid = userReportService.getParameter("R1");
                            words = userReportService.getParameter("S1");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "D2C1M1":
                            ts = userReportService.getParameter("T1");
                            ivRid = userReportService.getParameter("R1");
                            words = userReportService.getParameter("S1");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "D2C2M1":
                            ts = userReportService.getParameter("T2");
                            ivRid = userReportService.getParameter("R1");
                            words = userReportService.getParameter("S1");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "D3C1M1":
                            ts = userReportService.getParameter("T1");
                            ivRid = userReportService.getParameter("R1");
                            words = userReportService.getParameter("S1");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "D3C2M1":
                            ts = userReportService.getParameter("T2");
                            ivRid = userReportService.getParameter("R1");
                            words = userReportService.getParameter("S1");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "C1A1M2":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A1M2 = companyService.getCompany("M2", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A1M2.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A1M2);
                            break;
                        case "C1A2M2":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A2M2 = companyService.getCompany("M2", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A2M2.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A2M2);
                            break;
                        case "C1A3M2":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A3M2 = companyService.getCompany("M2", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A3M2.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A3M2);
                            break;
                        case "C2A1M2":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A1M2 = companyService.getCompany("M2", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A1M2.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A1M2);
                            break;
                        case "C2A2M2":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A2M2 = companyService.getCompany("M2", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A2M2.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A2M2);
                            break;
                        case "C2A3M2":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A3M2 = companyService.getCompany("M2", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A3M2.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A3M2);
                            break;
                        case "C3A1M2":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A1M2 = companyService.getCompany("M2", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A1M2.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A1M2);
                            break;
                        case "C3A2M2":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A2M2 = companyService.getCompany("M2", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A2M2.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A2M2);
                            break;
                        case "C3A3M2":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A3M2 = companyService.getCompany("M2", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A3M2.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A3M2);
                            break;
                        case "C1A1M3":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A1M3 = companyService.getCompany("M3", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A1M3.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A1M3);
                            break;
                        case "C1A2M3":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A2M3 = companyService.getCompany("M3", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A2M3.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A2M3);
                            break;
                        case "C1A3M3":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A3M3 = companyService.getCompany("M3", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A3M3.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A3M3);
                            break;
                        case "C2A1M3":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A1M3 = companyService.getCompany("M3", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A1M3.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A1M3);
                            break;
                        case "C2A2M3":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A2M3 = companyService.getCompany("M3", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A2M3.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A2M3);
                            break;
                        case "C2A3M3":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A3M3 = companyService.getCompany("M3", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A3M3.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A3M3);
                            break;
                        case "C3A1M3":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A1M3 = companyService.getCompany("M3", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A1M3.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A1M3);
                            break;
                        case "C3A2M3":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A2M3 = companyService.getCompany("M3", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A2M3.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A2M3);
                            break;
                        case "C3A3M3":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A3M3 = companyService.getCompany("M3", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A3M3.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A3M3);
                            break;
                        case "C1A1M4":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A1M4 = companyService.getCompany("M4", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A1M4.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A1M4);
                            break;
                        case "C1A2M4":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A2M4 = companyService.getCompany("M4", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A2M4.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A2M4);
                            break;
                        case "C1A3M4":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A3M4 = companyService.getCompany("M4", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A3M4.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A3M4);
                            break;
                        case "C2A1M4":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A1M4 = companyService.getCompany("M4", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A1M4.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A1M4);
                            break;
                        case "C2A2M4":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A2M4 = companyService.getCompany("M4", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A2M4.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A2M4);
                            break;
                        case "C2A3M4":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A3M4 = companyService.getCompany("M4", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A3M4.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A3M4);
                            break;
                        case "C3A1M4":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A1M4 = companyService.getCompany("M4", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A1M4.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A1M4);
                            break;
                        case "C3A2M4":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A2M4 = companyService.getCompany("M4", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A2M4.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A2M4);
                            break;
                        case "C3A3M4":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A3M4 = companyService.getCompany("M4", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A3M4.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A3M4);
                            break;
                        case "C1A1M5":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A1M5 = companyService.getCompany("M5", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A1M5.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A1M5);
                            break;
                        case "C1A2M5":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A2M5 = companyService.getCompany("M5", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A2M5.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A2M5);
                            break;
                        case "C1A3M5":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A3M5 = companyService.getCompany("M5", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A3M5.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A3M5);
                            break;
                        case "C2A1M5":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A1M5 = companyService.getCompany("M5", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A1M5.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A1M5);
                            break;
                        case "C2A2M5":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A2M5 = companyService.getCompany("M5", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A2M5.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A2M5);
                            break;
                        case "C2A3M5":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A3M5 = companyService.getCompany("M5", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A3M5.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A3M5);
                            break;
                        case "C3A1M5":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A1M5 = companyService.getCompany("M5", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A1M5.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A1M5);
                            break;
                        case "C3A2M5":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A2M5 = companyService.getCompany("M5", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A2M5.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A2M5);
                            break;
                        case "C3A3M5":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A3M5 = companyService.getCompany("M5", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A3M5.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A3M5);
                            break;
                        case "C1A1M6":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A1M6 = companyService.getCompany("M6", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A1M6.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A1M6);
                            break;
                        case "C1A2M6":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A2M6 = companyService.getCompany("M6", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A2M6.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A2M6);
                            break;
                        case "C1A3M6":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c1A3M6 = companyService.getCompany("M6", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A3M6.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A3M6);
                            break;
                        case "C2A1M6":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A1M6 = companyService.getCompany("M6", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A1M6.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A1M6);
                            break;
                        case "C2A2M6":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A2M6 = companyService.getCompany("M6", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A2M6.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A2M6);
                            break;
                        case "C2A3M6":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c2A3M6 = companyService.getCompany("M6", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A3M6.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A3M6);
                            break;
                        case "C3A1M6":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A1M6 = companyService.getCompany("M6", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A1M6.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A1M6);
                            break;
                        case "C3A2M6":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A2M6 = companyService.getCompany("M6", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A2M6.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A2M6);
                            break;
                        case "C3A3TM6":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R2");
                            words = userReportService.getParameter("S2");
                            Company c3A3TM6 = companyService.getCompany("M6", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A3TM6.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A3TM6);
                            break;
                        //============================================================
                        //破损专用通道
                        case "D1C1M11":
                            ts = userReportService.getParameter("T1");
                            ivRid = userReportService.getParameter("R3");
                            words = userReportService.getParameter("S3");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "D1C2M11":
                            ts = userReportService.getParameter("T2");
                            ivRid = userReportService.getParameter("R3");
                            words = userReportService.getParameter("S3");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "D2C1M11":
                            ts = userReportService.getParameter("T1");
                            ivRid = userReportService.getParameter("R3");
                            words = userReportService.getParameter("S3");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "D2C2M11":
                            ts = userReportService.getParameter("T2");
                            ivRid = userReportService.getParameter("R3");
                            words = userReportService.getParameter("S3");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "D3C1M11":
                            ts = userReportService.getParameter("T1");
                            ivRid = userReportService.getParameter("R3");
                            words = userReportService.getParameter("S3");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "D3C2M11":
                            ts = userReportService.getParameter("T2");
                            ivRid = userReportService.getParameter("R3");
                            words = userReportService.getParameter("S3");
                            words = words + "/" + problem.getProblemparts_insideitem();
                            callName = problem.getProblemparts_receivename();
                            phone = problem.getProblemparts_receivephone();
                            break;
                        case "C1A1M12":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A1M12 = companyService.getCompany("M12", String.valueOf(problem.getProblemparts_expressnumber()));
                            //nodename + upTableInfoId
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A1M12.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A1M12);
                            break;
                        case "C1A2M12":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A2M12 = companyService.getCompany("M12", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A2M12.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A2M12);
                            break;
                        case "C1A3M12":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A3M12 = companyService.getCompany("M12", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A3M12.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A3M12);
                            break;
                        case "C2A1M12":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A1M12 = companyService.getCompany("M12", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A1M12.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A1M12);
                            break;
                        case "C2A2M12":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A2M12 = companyService.getCompany("M12", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A2M12.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A2M12);
                            break;
                        case "C2A3M12":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A3M12 = companyService.getCompany("M12", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A3M12.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A3M12);
                            break;
                        case "C3A1M12":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A1M12 = companyService.getCompany("M12", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A1M12.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A1M12);
                            break;
                        case "C3A2M12":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A2M12 = companyService.getCompany("M12", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A2M12.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A2M12);
                            break;
                        case "C3A3M12":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A3M12 = companyService.getCompany("M12", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A3M12.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A3M12);
                            break;
                        case "C1A1M13":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A1M13 = companyService.getCompany("M13", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A1M13.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A1M13);
                            break;
                        case "C1A2M13":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A2M13 = companyService.getCompany("M13", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A2M13.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A2M13);
                            break;
                        case "C1A3M13":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A3M13 = companyService.getCompany("M13", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A3M13.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A3M13);
                            break;
                        case "C2A1M13":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A1M13 = companyService.getCompany("M13", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A1M13.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A1M13);
                            break;
                        case "C2A2M13":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A2M13 = companyService.getCompany("M13", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A2M13.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A2M13);
                            break;
                        case "C2A3M13":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A3M13 = companyService.getCompany("M13", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A3M13.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A3M13);
                            break;
                        case "C3A1M13":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A1M13 = companyService.getCompany("M13", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A1M13.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A1M13);
                            break;
                        case "C3A2M13":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A2M13 = companyService.getCompany("M13", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A2M13.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A2M13);
                            break;
                        case "C3A3M13":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A3M13 = companyService.getCompany("M13", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A3M13.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A3M13);
                            break;
                        case "C1A1M14":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A1M14 = companyService.getCompany("M14", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A1M14.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A1M14);
                            break;
                        case "C1A2M14":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A2M14 = companyService.getCompany("M14", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A2M14.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A2M14);
                            break;
                        case "C1A3M14":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A3M14 = companyService.getCompany("M14", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A3M14.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A3M14);
                            break;
                        case "C2A1M14":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A1M14 = companyService.getCompany("M14", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A1M14.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A1M14);
                            break;
                        case "C2A2M14":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A2M14 = companyService.getCompany("M14", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A2M14.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A2M14);
                            break;
                        case "C2A3M14":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A3M14 = companyService.getCompany("M14", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A3M14.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A3M14);
                            break;
                        case "C3A1M14":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A1M14 = companyService.getCompany("M14", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A1M14.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A1M14);
                            break;
                        case "C3A2M14":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A2M14 = companyService.getCompany("M14", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A2M14.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A2M14);
                            break;
                        case "C3A3M14":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A3M14 = companyService.getCompany("M14", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A3M14.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A3M14);
                            break;
                        case "C1A1M15":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A1M15 = companyService.getCompany("M15", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A1M15.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A1M15);
                            break;
                        case "C1A2M15":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A2M15 = companyService.getCompany("M15", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A2M15.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A2M15);
                            break;
                        case "C1A3M15":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A3M15 = companyService.getCompany("M15", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A3M15.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A3M15);
                            break;
                        case "C2A1M15":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A1M15 = companyService.getCompany("M15", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A1M15.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A1M15);
                            break;
                        case "C2A2M15":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A2M15 = companyService.getCompany("M15", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A2M15.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A2M15);
                            break;
                        case "C2A3M15":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A3M15 = companyService.getCompany("M15", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A3M15.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A3M15);
                            break;
                        case "C3A1M15":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A1M15 = companyService.getCompany("M15", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A1M15.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A1M15);
                            break;
                        case "C3A2M15":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A2M15 = companyService.getCompany("M15", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A2M15.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A2M15);
                            break;
                        case "C3A3M15":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A3M15 = companyService.getCompany("M15", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A3M15.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A3M15);
                            break;
                        case "C1A1M16":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A1M16 = companyService.getCompany("M16", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A1M16.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A1M16);
                            break;
                        case "C1A2M16":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A2M16 = companyService.getCompany("M16", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A2M16.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A2M16);
                            break;
                        case "C1A3M16":
                            ts = userReportService.getParameter("T1");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c1A3M16 = companyService.getCompany("M16", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c1A3M16.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c1A3M16);
                            break;
                        case "C2A1M16":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A1M16 = companyService.getCompany("M16", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A1M16.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A1M16);
                            break;
                        case "C2A2M16":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A2M16 = companyService.getCompany("M16", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A2M16.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A2M16);
                            break;
                        case "C2A3M16":
                            ts = userReportService.getParameter("T2");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c2A3M16 = companyService.getCompany("M16", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c2A3M16.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c2A3M16);
                            break;
                        case "C3A1M16":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P1");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A1M16 = companyService.getCompany("M16", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A1M16.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A1M16);
                            break;
                        case "C3A2M16":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P2");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A2M16 = companyService.getCompany("M16", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A2M16.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A2M16);
                            break;
                        case "C3A3M16":
                            ts = userReportService.getParameter("T3");
                            callNum = userReportService.getParameter("P3");
                            ivRid = userReportService.getParameter("R4");
                            words = userReportService.getParameter("S4");
                            Company c3A3TM16 = companyService.getCompany("M16", String.valueOf(problem.getProblemparts_expressnumber()));
                            words = words + "/" + tableInfoService.findReceicerResult(nodeName, jobList.getJoblistUptabid()) + "/" + HandlerUtil.parseType(problem.getProblemparts_type());
                            callName = c3A3TM16.getCompany_name();
                            phone = HandlerUtil.getLink(callNum, c3A3TM16);
                            break;
                    }
                    timestamp = HandlerUtil.parseTime(ts, dateTime);
                    //加入测试环境拨打网点的模拟电话
                    String qymndh = userReportService.getParameter("qymndh");
                    if (qymndh.equals("true")) {
                        if (param.contains("M1")) {
                            phone = problem.getProblemparts_sendphone();
                        } else {
                            phone = problem.getProblemparts_sendphone();
                        }
                    }
                    //向callDetail插入数据
                    this.insertCallDetail(jobList.getJoblistTabid(), jobList.getJoblistDealid(), problem.getId(), nodeName, ivRid, callType,
                            callName, problem.getProblemparts_expressnumber(),
                            0, words, phone, timestamp);
                }
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }
    private void insertCallDetail(Long tableId, Long dealid, Long problemId, String nodeName, String ivRid, Integer calltype, String callname, Long expressNumber, Integer state, String content, String phone, Timestamp timestamp) {
        CallDetail callDetail = new CallDetail();
        callDetail.setCalldetail_tableid(tableId);
        callDetail.setCalldetail_dealid(dealid);
        callDetail.setCalldetail_nodename(nodeName);
        callDetail.setCalldetail_problemid(problemId);
        callDetail.setCalldetail_ivrid(ivRid);
        callDetail.setCalldetail_calltype(calltype);
        callDetail.setCalldetail_name(callname);
        String x = ",";//定义一个单号的语音播报间隔
        String temp = String.valueOf(expressNumber);
        String voiceNumber = temp.substring(0,4)+x+temp.substring(4,8)+x+temp.substring(8,12)+x;
        callDetail.setCalldetail_expressnumber(voiceNumber);
        callDetail.setCalldetail_state(state);
        callDetail.setCalldetail_content(content);
        callDetail.setCalldetail_phonenumber(phone);
        callDetail.setCalldetail_time(timestamp);
        callDetail.setMwordlevel_varparama(GetLocalHost.getIp());
        callDetailService.insert(callDetail);
    }
}
