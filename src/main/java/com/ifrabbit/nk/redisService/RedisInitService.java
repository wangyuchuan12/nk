package com.ifrabbit.nk.redisService;

import com.ifrabbit.nk.usercenter.domain.*;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import com.ifrabbit.nk.usercenter.service.RoleService;
import com.ifrabbit.nk.usercenter.service.StaffService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import java.util.List;

//初始化缓存
@Service
public class RedisInitService implements CommandLineRunner{
    @Autowired
    private UserReportRedisService userReportRedisService;

    @Autowired
    private UserReportService userReportService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRedisService companyRedisService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRedisService roleRedisService;


    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffRedisService staffRedisService;

    //初始化方法
    public void init(){
        this.initUserReport();
        this.initCompany();
        this.initRole();
        this.initStaff();
    }

    //初始化网点缓存
    public void initCompany(){
        boolean isInit = companyRedisService.isInit();
        if(!isInit) {
            List<Company> companies = companyService.findAll();
            for (Company company : companies) {
                companyRedisService.doCache(company);
            }
        }
    }

    //初始化系统参数缓存
    public void initStaff(){
        boolean isInit = userReportRedisService.isInit();
        if(!isInit) {
            List<UserReport> userReports = userReportService.findAll();
            for (UserReport userReport : userReports) {
                userReportRedisService.doCache(userReport);
            }
        }
    }


    // 初始化角色缓存
    public void initRole(){
        boolean isInit = roleRedisService.isInit();
        if(!isInit) {
            List<Role> roles = roleService.findAll(new RoleDTO());
            for (Role role : roles) {
                roleRedisService.doCache(role);
            }
        }
    }


    // 初始化人员缓存
    public void initUserReport(){
        boolean isInit = staffRedisService.isInit();
        if(!isInit) {
            List<Staff> staffs = staffService.findAll(new StaffDTO());
            for (Staff staff : staffs) {
                staffRedisService.doCache(staff);
            }
        }
    }


    //初始化方法，程序启动时调用
    @Override
    public void run(String... strings) throws Exception {
        this.init();
    }
}
