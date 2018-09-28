package com.ifrabbit.nk.usercenter.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifrabbit.nk.annotation.Auth;
import com.ifrabbit.nk.context.UserContext;
import com.ifrabbit.nk.usercenter.domain.*;
import com.ifrabbit.nk.usercenter.service.MenuService;
import com.ifrabbit.nk.usercenter.service.RoleService;
import ir.nymph.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping("usercenter/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @GetMapping("session")
    List<Menu> session() {
        Staff staff = UserContext.get();
        if (null == staff) {
            return Collections.emptyList();
        }
        MenuDTO cond = new MenuDTO();
        cond.setUserId(staff.getId());
        List<Menu> tree = menuService.findTree(cond);
        return tree;
    }

    @GetMapping
    List<Menu> list(MenuDTO condition) {
        List<Menu> tree = menuService.findTree(condition);
        if (!CollectionUtil.isEmpty(tree) && null != condition.getContainRole()
                && condition.getContainRole().booleanValue()) {
            this.setRoles(tree);
        }
        return tree;
    }

    private void setRoles(List<Menu> menus) {
        menus.forEach(item -> {
            RoleDTO cond = new RoleDTO();
            cond.setMenuId(item.getId());
            List<Role> roles = this.roleService.findAll(cond);
            item.setRoles(roles);
            if (!CollectionUtil.isEmpty(item.getChildren())) {
                this.setRoles(item.getChildren());
            }
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Auth(inner = true)
    Long create(
            @RequestBody Menu menu) {
        if (null == menu.getPid()) {
            menu.setPid(Menu.ROOT_PID);
        }
        menuService.insert(menu);
        return menu.getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void modify(
            @PathVariable("id") Long id, @RequestBody Menu menu) {
        menu.setId(id);
        if (null == menu.getPid()) {
            menu.setPid(Menu.ROOT_PID);
        }
        menuService.updateIgnore(menu);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable("id") Long id) {
        menuService.delete(id);
    }

    @GetMapping("export")
    List<Menu> exportMenus() {
        return this.menuService.findTree(new MenuDTO());
    }

    @PostMapping("import")
    public Map<String, List<Menu>> importMenus(
            @RequestParam("file") MultipartFile[] files) {
        Map<String, List<Menu>> resMap = new HashMap<>();
        if (files != null && files.length > 0) {
            List<Menu> allMenu = new ArrayList<>();
            //循环获取file数组中得文件
            try {
                BufferedReader reader = null;
                for (int i = 0; i < files.length; i++) {
                    String jsonString = "";
                    MultipartFile file = files[i];
                    InputStreamReader inputStreamReader = new InputStreamReader(
                            file.getInputStream(), "UTF-8");
                    reader = new BufferedReader(inputStreamReader);
                    String tempString = null;
                    while ((tempString = reader.readLine()) != null) {
                        jsonString += tempString;
                    }
                    List<Menu> menus = new ObjectMapper()
                            .readValue(jsonString, new TypeReference<List<Menu>>() {
                            });
                    allMenu.addAll(menus);
                }
                reader.close();
                List<Menu> failed = this.menuService.importMenus(allMenu);
                if (CollectionUtil.isEmpty(failed)) {
                    resMap.put("OK", failed);
                } else {
                    resMap.put("warn", failed);
                }
                return resMap;
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof RuntimeException) {
                    resMap.put("error", new ArrayList<>());
                    return resMap;
                }
                resMap.put("error", new ArrayList<>());
                return resMap;
            }
        } else {
            resMap.put("error", new ArrayList<>());
            return resMap;
        }

    }

    @GetMapping("exist/{menuCode}")
    @Auth(mustAuthentication = false)
    public Boolean menuExits(@PathVariable("menuCode") String menuCode) {
        MenuDTO condition = new MenuDTO();
        condition.setCode(menuCode);
        List<Menu> menus = this.menuService.findAll(condition);
        if (CollectionUtil.isNotEmpty(menus)) {
            return true;
        } else {
            return false;
        }
    }
}

