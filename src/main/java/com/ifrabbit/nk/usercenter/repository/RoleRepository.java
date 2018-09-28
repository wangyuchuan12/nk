package com.ifrabbit.nk.usercenter.repository;

import static org.springframework.data.mybatis.repository.annotation.Query.Operation.delete;
import static org.springframework.data.mybatis.repository.annotation.Query.Operation.insert;

import com.ifrabbit.nk.usercenter.domain.Role;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
/*  
 *    
 *   
 * @author sjj
 * @date  17:35  
 * @param   
 * @return   
 */  
public interface RoleRepository extends MybatisRepository<Role, Long> {
	@Query(operation = insert) int insertRoleUser(@Param("roleId") Long roleId,
                                                  @Param("userId") String userId);

	@Query(operation = delete) int deleteRoleUserByRoleId(@Param("roleId") Long roleId);

	@Query(operation = insert) int insertRoleMenu(@Param("roleId") Long roleId,
                                                  @Param("menuId") Long menuId);

	@Query(operation = delete) int deleteRoleMenuByRoleId(@Param("roleId") Long roleId);

	@Query(operation = delete) int deleteRoleGroupByRoleId(@Param("roleId") Long roleId);

	@Query(operation = insert) int insertRoleGroup(@Param("roleId") Long roleId,
                                                   @Param("groupId") Long groupId);

	@Query(operation = delete) int deleteRoleOperateByRoleId(
            @Param("roleId") Long roleId);

	@Query(operation = insert) int insertRoleOperate(@Param("roleId") Long roleId,
                                                     @Param("operateId") Long operateId);

}
