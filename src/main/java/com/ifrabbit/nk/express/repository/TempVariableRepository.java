package com.ifrabbit.nk.express.repository;

import com.ifrabbit.nk.express.domain.TempVariable;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

/**
 * @author lishaomiao
 * @date 2018/3/14 11:05
 *
 */
public interface TempVariableRepository extends MybatisRepository<TempVariable,Long>,TempVariableRepositoryCustom {

}
