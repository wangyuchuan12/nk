package com.ifrabbit.nk.usercenter.repository;

import static org.springframework.data.mybatis.repository.annotation.Query.Operation.delete;
import static org.springframework.data.mybatis.repository.annotation.Query.Operation.insert;

import com.ifrabbit.nk.usercenter.domain.Operate;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;

public interface OperateRepository extends MybatisRepository<Operate, Long> {
	@Query(operation = insert) int insertRoleOperate(@Param("roleId") Long roleId,
                                                     @Param("operateId") Long operateId);

	@Query(operation = delete) int deleteRoleOperateByOperateId(
            @Param("operateId") Long operateId);
}
