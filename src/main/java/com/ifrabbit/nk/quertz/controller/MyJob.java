package com.ifrabbit.nk.quertz.controller;


import com.ifrabbit.nk.quertz.domain.Balance;
import com.ifrabbit.nk.quertz.domain.JobConfig;
import com.ifrabbit.nk.quertz.domain.OriginalDate;
import com.ifrabbit.nk.quertz.service.BalanceService;
import com.ifrabbit.nk.quertz.service.JobConfigService;
import com.ifrabbit.nk.quertz.service.impl.BalanceServiceImpl;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.domain.CompanyDTO;
import com.ifrabbit.nk.usercenter.domain.JobList;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import com.ifrabbit.nk.usercenter.service.JobListService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RestController
public class MyJob implements Job {
    @Autowired
    private JobConfigService jobConfigService;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private CompanyService companyService;
    @Transactional
    public void execute(JobExecutionContext context) {
        System.out.println();
        System.out.println();
        System.out.println(context.getJobDetail().getDescription());
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(this.toString() + ":" + f.format(new Date()) + "正在执行Job   executing...");
        List<JobConfig> configs = jobConfigService.findAllByStatus(1);
        for (JobConfig config : configs) {
            System.out.println(config.toString());
        }
        Balance balance = new Balance();
        balance.setBalance_tabid(2);
        balance.setBalance_companyid(4);
        balance.setBalance_companyname("杭州");
        balance.setBalance_companycode("425368");
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String dt = s.format(new Date());
        balance.setBalance_date(dt);
        balance.setBalance_amount(new BigDecimal(200000));
        Company com = companyService.findOneByID(Long.valueOf(4));
        BigDecimal originalown = com.getCompany_own();
        balance.setBalance_originalown(originalown);
        BigDecimal originalsum = com.getCompany_paid();
        balance.setBalance_originalsum(originalsum);
        BigDecimal own = new BigDecimal(com.getCompany_own().intValue()-com.getCompany_paid().intValue());
        balance.setBalance_own(own);
        BigDecimal credit = new BigDecimal(com.getCompany_originalcredit().intValue()-(com.getCompany_own().intValue()-com.getCompany_paid().intValue()));
        balance.setBalance_credit(credit);
        balance.setBalance_state(1);
        balance.setBalance_memo("测试应用");

        Company company = new Company();
        company.setId(Long.valueOf(4));
        company.setCompany_own(own);
        System.out.println(company);
        companyService.updateIgnore(company);

        balanceService.insert(balance);
        if(balance!=null){
            System.out.println("添加成功");
        }

    }
}