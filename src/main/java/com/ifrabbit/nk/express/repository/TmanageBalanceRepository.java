package com.ifrabbit.nk.express.repository;

import com.ifrabbit.nk.express.domain.TmanageBalance;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author lishaomiao
 * @date 2018/6/1 11:05
 */
public interface TmanageBalanceRepository extends MybatisRepository<TmanageBalance,Long> {
    @Transactional(readOnly = true)
    @Query
    List<String> search(@Param("str") String str);

    @Query
    List<Map<String, Object>> findBalanceByParams(@Param("params") Map<String, Object> params);


}

