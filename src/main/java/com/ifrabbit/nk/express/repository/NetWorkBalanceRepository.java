package com.ifrabbit.nk.express.repository;

import com.ifrabbit.nk.express.domain.NetWorkBalance;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

/**
 * @author lishaomiao
 * @date 2018/6/1/11:05
 *
 */
public interface NetWorkBalanceRepository extends MybatisRepository<NetWorkBalance,Long>,NetWorkBalanceRepositoryCustom {

}
