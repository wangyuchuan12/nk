package com.ifrabbit.nk.usercenter.controller;

import com.ifrabbit.nk.annotation.Auth;
import com.ifrabbit.nk.usercenter.domain.Role;
import com.ifrabbit.nk.usercenter.domain.RoleDTO;
import com.ifrabbit.nk.usercenter.domain.Staff;
import com.ifrabbit.nk.usercenter.domain.StaffDTO;
import com.ifrabbit.nk.usercenter.service.GroupService;
import com.ifrabbit.nk.usercenter.service.RoleService;
import com.ifrabbit.nk.usercenter.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("usercenter/user")
public class StaffController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private GroupService groupService;


    @GetMapping
    Page<Staff> LIST(@PageableDefault(size = 20) Pageable pageable, StaffDTO condition) {
        System.out.println("LIST方法");
        Page<Staff> page = staffService.findAll(pageable, condition);
        if (page.hasContent()) {
            page.getContent().stream().forEach(staff -> {
                RoleDTO cond = new RoleDTO();
                cond.setUserId(staff.getId());
                List<Role> roles = roleService.findBasicAll(cond, null);
                staff.setRoles(roles);
            });
        }
        return page;
    }

    @GetMapping("list")
    List<Staff> list(StaffDTO condition) {
        return staffService.findAll(condition);
    }


    @GetMapping("{id}")
    @Auth(inner = true)
    Staff get(@PathVariable("id") String id) {
        return staffService.get(id);
    }


    private void preProcessUser(@RequestBody Staff staff) {
//        if (StringUtils.hasText(user.getFullArea())) {
//            String[] areas = user.getFullArea().split(",");
//            user.setArea(areas[areas.length - 1]);
//        }
        if (StringUtils.hasText(staff.getStaffPassword())) {
            staff.setStaffPassword(staffService.encrypt(staff.getStaffPassword()));
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    String create(@RequestBody Staff staff) {
        System.out.println("进入CREATE......");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("用户信息： " + staff.getGroups().toString());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        staff.setStatus(normal);
        staff.setStaffState(1);
        staff.setStaffJobstate(1);
        staff.setStaffCreatedate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        System.out.println("创建日期" + staff.getStaffCreatedate());
        preProcessUser(staff);
        staffService.insert(staff);
        return staff.getId();
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void modify(@PathVariable("id") String id, @RequestBody Staff staff) {
        staff.setId(id);
        staff.setStaffState(null);
        preProcessUser(staff);
        staffService.updateIgnore(staff);
    }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") String id) {
        staffService.delete(id);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void bulkDelete(@RequestBody String[] ids) {
        staffService.batchDelete(ids);
    }






}
