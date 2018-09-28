package com.ifrabbit.nk.flow.repository;

import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import com.ifrabbit.nk.express.domain.TempVariable;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: WangYan
 * @Date: 2018/5/18 15:41
 * @Description:
 */
public interface VariableRepository
        extends MybatisRepository<TempVariable, Long>, VariableRepositoryCustom {

    @Transactional(readOnly = true)
    @Query
    List<TempVariable> findByProcessId(@Param("id") Long id);

    @Transactional(readOnly = false)
    @Query
    void updateVariable(@Param("variable") TempVariable variable);

    @Transactional(readOnly = false)
    @Query
    void deleteVariable(@Param("phoneNumber") Long phoneNumber);
}
