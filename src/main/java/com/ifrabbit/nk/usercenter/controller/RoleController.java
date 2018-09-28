package com.ifrabbit.nk.usercenter.controller;

import com.ifrabbit.nk.usercenter.domain.*;
import com.ifrabbit.nk.usercenter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 *
 *
 * @author sjj
 * @date  17:43
 * @param
 * @return
 */
@RestController
@RequestMapping("usercenter/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private OperateService operateService;

    @GetMapping
    Page<Role> list(@PageableDefault(size = 20) Pageable pageable,
                    RoleDTO condition) {

        return roleService.findAll(pageable, condition);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Long create(@RequestBody Role role) {
        System.out.println("进入CREATE......");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("获取user的id： " + role.getStaffs().toString());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.println("设置角色状态所对应的中文值： 1 = '记录有效' 9 = '记录无效'");
//        Integer state = role.getState();
//        if (state == 1) {
//            role.setStates("记录有效");
//        } else if (state == 9) {
//            role.setStates("记录无效");
//        } else {
//            role.setStates("记录错误");
//        }

//        System.out.println("设置角色类型所对应的中文值： 1 = '内部人员' 2 = '外部人员' 3 = '人员小组'");
//        Integer type = role.getType();
//        if (type == 1) {
//            role.setTypes("内部角色");
//        } else if (type == 2) {
//            role.setTypes("外部角色");
//        } else if (type == 3) {
//            role.setTypes("人员小组");
//        } else {
//            role.setTypes("信息错误");
//        }
        role.setState(1);
        role.setStates("记录有效");
        role.setType(1);
        role.setTypes("内部角色");
        roleService.insert(role);
        return role.getId();
    }

    @GetMapping("{id}")
    Role getRole(@PathVariable("id") Long id) {
        System.out.println("进入GETRole.......");

        Role res = roleService.get(id);

        //获取角色对应的用户
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setRoleId(res.getId());
        List<Staff> staffs = this.staffService.findAll(staffDTO);
        res.setStaffs(staffs);
        //获取角色对应的菜单
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setRoleId(res.getId());
        List<Menu> menus = this.menuService.findAll(menuDTO);
        res.setMenus(menus);
        //获取角色对应的分组
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setRoleId(id);
        List<Group> groups = this.groupService.findAll(groupDTO);
        res.setGroups(groups);
        //获取角色对应的操作
        OperateDTO operateDTO = new OperateDTO();
        operateDTO.setRoleId(id);
        List<Operate> operates = this.operateService.findAll(operateDTO);
        res.setOperates(operates);

        return res;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void modify(
            @PathVariable("id") Long id, @RequestBody Role role) {
//
//        Integer state = role.getState();
//        if (state == 1) {
//            role.setStates("记录有效");
//        } else if (state == 9) {
//            role.setStates("记录无效");
//        } else {
//            role.setStates("记录错误");
//        }
//
//
//        System.out.println("设置角色类型所对应的中文值： 1 = '内部人员' 2 = '外部人员' 3 = '人员小组'");
//        Integer type = role.getType();
//        if (type == 1) {
//            role.setTypes("内部角色");
//        } else if (type == 2) {
//            role.setTypes("外部角色");
//        } else if (type == 3) {
//            role.setTypes("人员小组");
//        } else {
//            role.setTypes("信息错误");
//        }
//
        role.setStates("记录有效");
        role.setTypes("内部角色");
        role.setId(id);
        roleService.updateIgnore(role);
    }

    @PutMapping("assignMenu")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void assignMenu(
            @RequestBody Role role) {
        roleService.assignMenus(role);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "")
    void delete(
            @PathVariable("id") Long id) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("进入删除程序....................");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        roleService.delete(id);
    }

}
