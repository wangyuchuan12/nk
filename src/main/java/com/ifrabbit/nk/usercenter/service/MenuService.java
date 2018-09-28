package com.ifrabbit.nk.usercenter.service;

import com.ifrabbit.nk.usercenter.domain.Menu;
import com.ifrabbit.nk.usercenter.domain.MenuDTO;
import java.util.List;
import org.springframework.data.support.CrudService;

public interface MenuService extends CrudService<Menu, Long> {

	List<Menu> findTree(MenuDTO condition);

	List<Menu> importMenus(List<Menu> allMenu);
}
