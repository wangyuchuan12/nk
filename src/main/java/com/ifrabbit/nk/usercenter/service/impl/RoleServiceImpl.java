package com.ifrabbit.nk.usercenter.service.impl;

import com.ifrabbit.nk.redisService.RoleRedisService;
import com.ifrabbit.nk.usercenter.domain.Menu;
import com.ifrabbit.nk.usercenter.domain.MenuDTO;
import com.ifrabbit.nk.usercenter.domain.Role;
import com.ifrabbit.nk.usercenter.repository.RoleRepository;
import com.ifrabbit.nk.usercenter.service.MenuService;
import com.ifrabbit.nk.usercenter.service.RoleService;
import ir.nymph.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 *
 *
 * @author sjj
 * @date  16:41
 * @param
 * @return
 */
@Service
public class RoleServiceImpl extends AbstractCrudService<RoleRepository, Role, Long> implements RoleService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleRedisService roleRedisService;
    /**
     * 构造函数.
     *
     * @param repository 注入的Repository
     */
    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public void updateIgnore(Role role) {

        super.updateIgnore(role);
        //更新角色对应的用户
        getRepository().deleteRoleUserByRoleId(role.getId());
        if (CollectionUtil.isNotEmpty(role.getStaffs())) {
            role.getStaffs().forEach(user -> {
                getRepository().insertRoleUser(role.getId(), user.getId());
            });
        }
        //更新角色对应用户组
        getRepository().deleteRoleGroupByRoleId(role.getId());
        if (CollectionUtil.isNotEmpty(role.getGroups())) {
            role.getGroups().forEach(group -> {
                getRepository().insertRoleGroup(role.getId(), group.getId());
            });
        }
        roleRedisService.doCache(role);

        //    //获取所有菜单数据
        //    List<Menu> allMenus = this.menuService.findAll(new MenuDTO());
        //    if(!CollectionUtil.isEmpty(role.getMenus())){
        //      Set<Long> parentMenuId = new HashSet<>();
        //      Set<Long> menuIds = role.getMenus().stream().map(Menu::getId).collect(Collectors.toSet());
        //      menuIds.forEach(item -> {
        //        Menu target = allMenus.stream().filter(it -> it.getId().longValue() == item).collect(Collectors.toList()).get(0);
        //        if(target.getPid().longValue() != 0){
        //          parentMenuId.add(target.getPid());
        //        }
        //      });
        //      menuIds.addAll(parentMenuId);
        //      //更新角色对应的菜单数据
        //      getRepository().deleteRoleMenuByRoleId(role.getId());
        //      menuIds.forEach(item -> getRepository().insertRoleMenu(role.getId(), item));
        //    }
    }

    private void checkParent(Set<Long> parentMemuSet, List<Menu> allMenus, Long menuId) {
        Menu target = allMenus.stream().filter(it -> it.getId().longValue() == menuId)
                .collect(Collectors.toList()).get(0);
        if (target.getPid().longValue() != 0) {
            parentMemuSet.add(target.getPid());
            this.checkParent(parentMemuSet, allMenus, target.getPid());
        }
    }

    @Override
    @Transactional
    public void assignMenus(Role role) {
        //获取所有菜单数据
        List<Menu> allMenus = this.menuService.findAll(new MenuDTO());
        getRepository().deleteRoleMenuByRoleId(role.getId());
        if (!CollectionUtil.isEmpty(role.getMenus())) {
            Set<Long> parentMenuId = new HashSet<>();
            Set<Long> menuIds = role.getMenus().stream().map(Menu::getId)
                    .collect(Collectors.toSet());
            menuIds.forEach(item -> {
                this.checkParent(parentMenuId, allMenus, item);
            });
            menuIds.addAll(parentMenuId);
            //更新角色对应的菜单数据
            menuIds.forEach(item -> getRepository().insertRoleMenu(role.getId(), item));
        }
        //设置操作数据
        getRepository().deleteRoleOperateByRoleId(role.getId());
        if (!CollectionUtil.isEmpty(role.getOperates())) {
            role.getOperates().forEach(item -> {
                getRepository().insertRoleOperate(role.getId(), item.getId());
            });
        }
    }

    @Override
    @Transactional
    public void insert(Role role) {
        super.insert(role);
        //更新角色对应的用户
        if (CollectionUtil.isNotEmpty(role.getStaffs())) {
            role.getStaffs().forEach(user -> {
                getRepository().insertRoleUser(role.getId(), user.getId());
            });
        }
        //插入角色对应的用户组
        if (CollectionUtil.isNotEmpty(role.getGroups())) {
//			//测试
//			System.out.println("获取到的group: "+ role.getGroups().toString());
//			System.out.println();
//			System.out.println("获取到的user: "+ role.getUsers().toString());
            role.getGroups().forEach(group -> {
                getRepository().insertRoleGroup(role.getId(), group.getId());
            });
        }
        roleRedisService.doCache(role);
        //获取所有菜单数据
        //    List<Menu> allMenus = this.menuService.findAll(new MenuDTO());
        //    if(!CollectionUtil.isEmpty(role.getMenus())){
        //      Set<Long> parentMenuId = new HashSet<>();
        //      Set<Long> menuIds = role.getMenus().stream().map(Menu::getId).collect(Collectors.toSet());
        //      menuIds.forEach(item -> {
        //        Menu target = allMenus.stream().filter(it -> it.getId().longValue() == item).collect(Collectors.toList()).get(0);
        //        if(target.getPid().longValue() != 0){
        //          parentMenuId.add(target.getPid());
        //        }
        //      });
        //      menuIds.addAll(parentMenuId);
        //      //更新角色对应的菜单数据
        //      menuIds.forEach(item -> getRepository().insertRoleMenu(role.getId(), item));
        //    }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        super.delete(id);
        getRepository().deleteRoleGroupByRoleId(id);
        getRepository().deleteRoleUserByRoleId(id);
        getRepository().deleteRoleMenuByRoleId(id);
        roleRedisService.doDelCache(String.valueOf(id));
    }
}
