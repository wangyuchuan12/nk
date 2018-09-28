package com.ifrabbit.nk.usercenter.repository;

import com.ifrabbit.nk.usercenter.domain.Menu;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;

import static org.springframework.data.mybatis.repository.annotation.Query.Operation.delete;
import static org.springframework.data.mybatis.repository.annotation.Query.Operation.insert;

public interface MenuRepository extends MybatisRepository<Menu, Long> {

    @Query(operation = insert)
    int insertRoleMenu(@Param("roleId") Long roleId,
                       @Param("menuId") Long menuId);

    @Query(operation = delete)
    int deleteRoleMenuByMenuId(@Param("menuId") Long menuId);

}
