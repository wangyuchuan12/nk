package com.ifrabbit.nk.express.repository;

import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/16
 * Time:17:23
 */
public interface ExpressInfoDetailRepository extends MybatisRepository<ExpressInfoDetail,Long> {
    @Transactional(readOnly = true)
    @Query
    List<ExpressInfoDetail> findExpressNumber(@Param("expressNumber") String expressNumber);

    @Transactional(readOnly = false)
    @Query
    void deleteExpressNumber(@Param("expressNumber") String expressNumber);
}
