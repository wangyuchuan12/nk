package com.ifrabbit.nk.quertz.repository;

import com.ifrabbit.nk.quertz.domain.Balance;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;

import static org.springframework.data.mybatis.repository.annotation.Query.Operation.insert;

/**
 * @Authod: chenyu
 * @Date 2018/6/11 9:58
 * Content:
 */
public interface BalanceRepository extends MybatisRepository<Balance,Long> {
    @Query(operation = insert)
    int insertBalance(Balance balance);
}
