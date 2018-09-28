package com.ifrabbit.nk.usercenter.repository;

import com.ifrabbit.nk.usercenter.domain.Staff;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.mybatis.repository.annotation.Query.Operation.delete;
import static org.springframework.data.mybatis.repository.annotation.Query.Operation.insert;

@Transactional
public interface StaffRepository extends MybatisRepository<Staff, String> {

    @Query(operation = insert)
    int insertRoleUser(@Param("roleId") Long roleId,
                       @Param("userId") String userId);

    @Query(operation = delete)
    int deleteRoleUserByUserId(@Param("userId") String userId);

    @Query(operation = insert)
    int insertGroupUser(@Param("groupId") Long groupId,
                        @Param("userId") String userId);

    @Query(operation = delete)
    int deleteGroupUserByUserId(
            @Param("userId") String userId);
}
