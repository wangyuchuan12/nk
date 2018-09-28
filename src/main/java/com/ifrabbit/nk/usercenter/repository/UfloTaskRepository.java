package com.ifrabbit.nk.usercenter.repository;

import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.domain.UfloTask;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mybatis.repository.annotation.Query.Operation.select_one;

public interface UfloTaskRepository extends MybatisRepository<UfloTask,Long> {
    @Transactional(readOnly = true)
    @Query
    UfloTask findById(@Param("id") Long id);

    @Transactional(readOnly = true)
    @Query
    List<Map<String,Object>> findByProcessId(@Param("id") Long id);
}
