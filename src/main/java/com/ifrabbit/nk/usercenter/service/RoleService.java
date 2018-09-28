package com.ifrabbit.nk.usercenter.service;

import com.ifrabbit.nk.usercenter.domain.Role;
import org.springframework.data.support.CrudService;
/*  
 *
 * @author sjj
 * @date  16:41
 * @param   
 * @return   
 */
public interface RoleService extends CrudService<Role, Long> {

	/**
	 * 分配角色菜单
	 */
	void assignMenus(Role role);

}
