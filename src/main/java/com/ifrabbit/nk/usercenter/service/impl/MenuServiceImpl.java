package com.ifrabbit.nk.usercenter.service.impl;

import com.ifrabbit.nk.usercenter.domain.Menu;
import com.ifrabbit.nk.usercenter.domain.MenuDTO;
import com.ifrabbit.nk.usercenter.domain.Role;
import com.ifrabbit.nk.usercenter.domain.RoleDTO;
import com.ifrabbit.nk.usercenter.repository.MenuRepository;
import com.ifrabbit.nk.usercenter.service.MenuService;
import com.ifrabbit.nk.usercenter.service.RoleService;
import ir.nymph.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class MenuServiceImpl extends AbstractCrudService<MenuRepository, Menu, Long> implements MenuService {

    @Autowired
    private RoleService roleService;

    /**
     * 构造函数.
     *
     * @param repository 注入的Repository
     */
    @Autowired
    public MenuServiceImpl(MenuRepository repository) {
        super(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findTree(
            MenuDTO condition) {
        List<Menu> list = findAll(new Sort(ASC, "lft"), condition);
        return treeList(list, condition);
    }

    @Override
    @Transactional
    public List<Menu> importMenus(List<Menu> allMenu) {
        List<Menu> failedList = this.doImport(allMenu, 0L);
        //重新设置菜单左右值
        rebuild();
        return failedList;
    }

    /**
     * 递归导入
     */
    private List<Menu> doImport(List<Menu> menus, Long pid) {
        List<Menu> failedList = new ArrayList<>();
        for (Menu menu : menus) {
            MenuDTO condition = new MenuDTO();
            condition.setName(menu.getName());
            if (CollectionUtil.isEmpty(getRepository().findAll(condition))) {
                condition.setName(null);
                condition.setCode(menu.getCode());
                if (CollectionUtil.isEmpty(getRepository().findAll(condition))) {
                    menu.setId(null);
                    menu.setPid(pid);
                    this.insert(menu);
                    if (!CollectionUtil.isEmpty(menu.getChildren())) {
                        failedList
                                .addAll(this.doImport(menu.getChildren(), menu.getId()));
                    }
                } else {
                    failedList.add(menu);
                }
            } else {
                failedList.add(menu);
            }
        }
        return failedList;
    }

    private List<Menu> treeList(List<Menu> list, MenuDTO condition) {
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        if (null != condition.getContainRole() && condition.getContainRole()) {
            list.forEach(menu -> {
                RoleDTO cond = new RoleDTO();
                cond.setMenuId(menu.getId());
                List<Role> roles = roleService.findAll(cond);
                menu.setRoles(roles);
            });
        }

        // 构造一个虚拟根节点
        Menu root = new Menu();
        root.setId(0L);
        root.setLft(0L);
        root.setRgt(Long.MAX_VALUE);
        list.add(0, root);
        return buildTree(list).getChildren();
    }

    private Menu buildTree(List<Menu> list) {
        Stack<Menu> stack = new Stack<>();
        list.forEach(menu -> {
            if (stack.isEmpty()) {
                stack.push(menu);
            } else {
                while (!stack.isEmpty() && stack.lastElement().getRgt() < menu.getRgt()) {
                    stack.pop();
                }
                if (!stack.isEmpty()) {
                    stack.lastElement().addChild(menu);
                }
                stack.push(menu);
            }
        });
        return stack.firstElement();
    }

    private void rebuild() {

        List<Menu> list = findAll(new Sort(DESC, "sort"), null);
        if (CollectionUtil.isEmpty(list)) {
            return;
        }

        // 构造一个虚拟根节点
        Menu root = new Menu();
        root.setId(0L);
        root.setLft(0L);
        root.setRgt(list.size() * 2L + 1L);
        list.add(0, root);

        convertAdjacencyToPreorderTraversal(list, root, 0);
        list.forEach(m -> {
            if (m.getId() == 0) {
                return;
            }
            super.updateIgnore(m);
        });
    }

    private long convertAdjacencyToPreorderTraversal(List<Menu> all, Menu parent,
                                                     long lft) {
        long rgt = lft + 1;
        List<Menu> children = searchChildren(all, parent.getId());
        if (CollectionUtil.isNotEmpty(children)) {
            for (Menu child : children) {
                rgt = convertAdjacencyToPreorderTraversal(all, child, rgt);
            }
        }
        parent.setLft(lft);
        parent.setRgt(rgt);
        return rgt + 1;
    }

    private List<Menu> searchChildren(List<Menu> list, Long pid) {
        return list.stream().filter(m -> pid.equals(m.getPid()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void insert(Menu menu) {
        super.insert(menu);
        rebuild();
        if (CollectionUtil.isNotEmpty(menu.getRoles())) {
            menu.getRoles().forEach(role -> {
                getRepository().insertRoleMenu(role.getId(), menu.getId());
            });
        }
    }

    @Override
    @Transactional
    public void updateIgnore(Menu menu) {
        super.updateIgnore(menu);
        rebuild();
        getRepository().deleteRoleMenuByMenuId(menu.getId());
        if (CollectionUtil.isNotEmpty(menu.getRoles())) {
            menu.getRoles().forEach(role -> {
                getRepository().insertRoleMenu(role.getId(), menu.getId());
            });
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        super.delete(id);
        rebuild();
        getRepository().deleteRoleMenuByMenuId(id);
    }
}
