package com.ifrabbit.nk.usercenter.service.impl;

import com.ifrabbit.nk.usercenter.domain.Menu;
import com.ifrabbit.nk.usercenter.domain.MenuDTO;
import com.ifrabbit.nk.usercenter.domain.Operate;
import com.ifrabbit.nk.usercenter.domain.OperateDTO;
import com.ifrabbit.nk.usercenter.repository.OperateRepository;
import com.ifrabbit.nk.usercenter.service.MenuService;
import com.ifrabbit.nk.usercenter.service.OperateService;
import ir.nymph.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
public class OperateServiceImpl extends AbstractCrudService<OperateRepository, Operate, Long> implements OperateService {

    /**
     * 构造函数.
     *
     * @param repository 注入的Repository
     */
    @Autowired
    public OperateServiceImpl(OperateRepository repository) {
        super(repository);
    }

    @Autowired
    private MenuService menuService;

    @Override
    public List<Operate> importOperate(List<Operate> allOperate) {
        List<Operate> failedList = new ArrayList<>();
        for (Operate op : allOperate) {
            OperateDTO condition = new OperateDTO();
            condition.setName(op.getName());
            if (CollectionUtil.isEmpty(getRepository().findAll(condition))) {
                condition.setName(null);
                condition.setCode(op.getCode());
                if (CollectionUtil.isEmpty(getRepository().findAll(condition))) {
                    // 查看对应的菜单是否存在
                    MenuDTO menuDTO = new MenuDTO();
                    menuDTO.setCode(op.getMenu().getCode());
                    List<Menu> menus = this.menuService.findAll(menuDTO);
                    if (!CollectionUtil.isEmpty(menus)) {
                        Menu menu = new Menu();
                        menu.setId(menus.get(0).getId());
                        op.setId(null);
                        op.setMenu(menu);
                        this.insert(op);
                    } else {
                        failedList.add(op);
                    }
                } else {
                    failedList.add(op);
                }
            } else {
                failedList.add(op);
            }
        }
        return failedList;
    }

    @Override
    @Transient
    public void delete(Long id) {
        super.delete(id);
        this.getRepository().deleteRoleOperateByOperateId(id);
    }
}
