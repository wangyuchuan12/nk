package com.ifrabbit.nk.usercenter.controller;

import com.ifrabbit.nk.annotation.Auth;
import com.ifrabbit.nk.usercenter.domain.Staff;
import com.ifrabbit.nk.usercenter.domain.StaffDTO;
import com.ifrabbit.nk.usercenter.domain.UserReport;
import com.ifrabbit.nk.usercenter.domain.UserReportDTO;
import com.ifrabbit.nk.usercenter.service.StaffService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author: SunJiaJian
 * @Description:
 * @Date: Created in 10:50 2018/3/14
 */

@RequestMapping("usercenter/system")
@RestController
public class UserReportController {

    @Autowired
    private UserReportService userReportService;
    @Autowired
    private StaffService staffService;


    /**
     * 分页
     * @param pageable
     * @param condition
     * @return
     */
    @GetMapping
    Page<UserReport> list(@PageableDefault(size = 20) Pageable pageable,
                          UserReportDTO condition) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("进入了pageable...............");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Page<UserReport> page = userReportService.findAll(pageable, condition);
        return page;
    }


    //查询电话线程的系统参数
    @RequestMapping("telephoneThread")
    Page<UserReport> findThreadOfTelephone(@PageableDefault(size = 20) Pageable pageable,
                                           UserReportDTO condition) {
        condition.setFuzzyName("线程");
        Page<UserReport> page = userReportService.findAll(pageable, condition);
        return page;
    }


    /**
     * 新增
     * @param userReport
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Long create(
            @RequestBody UserReport userReport) {
        //获取到用户id后，再查询获取公司名字
        //TODO
        //参数状态
//        Integer state = userReport.getState();
//        if (state == 1) {
            userReport.setState(1);//1 有效
            userReport.setStates("有效");
//        } else if (state == 0) {
//            userReport.setStates("无效");
//        } else if (state == 9) {
//            userReport.setStates("删除");
//            System.out.println("当前id的值为: " + userReport.getId());
//            System.out.println("state的状态值为9，调用删除程序.........");
//            delete(userReport.getId());
//        } else {
//            userReport.setStates("出错");
//        }

        //参数类型
//        Integer type = userReport.getType();
//        if (type == 1) {
            userReport.setTypes("1");
            userReport.setTypes("系统参数");
//        } else if (type == 2) {
//            userReport.setTypes("用户自定义参数");
//        } else {
//            userReport.setTypes("出错");
//        }
//        List<Staff> users = userReport.getUsers();
//        StringBuffer cup = new StringBuffer();
//        users.forEach(staff ->{
//            Staff one = staffService.get(staff.getId());
//            cup.append(
//                    one.getStaffUsername()+"&");
//                }
//        );
//        cup.deleteCharAt(cup.length()-1);
//        userReport.setStaffname(cup.toString());
        userReportService.insert(userReport);
        return userReport.getId();
    }


    /**
     * 查询全部列表
     * @param condition
     * @return
     */
    @GetMapping("list")
    List<UserReport> list(UserReportDTO condition) {
        return userReportService.findAll(condition);
    }

    /**
     * 根据id查询列表
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @Auth(inner = true)
    UserReport get(@PathVariable("id") Long id) {
        return userReportService.get(id);
    }

    /**
     * 修改
     * @param id
     * @param userReport
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void modify(
            @PathVariable("id") Long id, @RequestBody UserReport userReport) {
//        userReport.setId(id);
//        Integer modifyState = userReport.getState();
//        System.out.println("进入修改程序...................");
//        System.out.println("获取到的id是: " + modifyState);
        //参数状态
//        if (modifyState == 1) {
//            userReport.setStates("有效");
//        } else if (modifyState == 0) {
//            userReport.setStates("无效");
//        } else if (modifyState == 9) {
//            userReport.setStates("删除");
//        } else {
//            userReport.setStates("出错");
//        }
        //参数类型
//        Integer type = userReport.getType();
//        if (type == 1) {
//            userReport.setTypes("系统参数");
//        } else if (type == 2) {
//            userReport.setTypes("用户自定义参数");
//        } else {
//            userReport.setTypes("出错");
//        }
        userReport.setId(id);
        userReportService.updateIgnore(userReport);
    }

    /**
     * 根据id删除
     *
     * @param id
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable("id") Long id) {
        userReportService.delete(id);
    }

}
