package com.ifrabbit.nk.express.repository;

import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/15
 * Time:16:27
 */
public interface ExpressInfoRecordRepository extends MybatisRepository<ExpressInfoRecord,Long>,ExpressInfoRecordRepositoryCustom{
    @Transactional(readOnly = true)
    @Query
    List<ExpressInfoRecord> findByExpressNumber(@Param("expressNumber") String expressNumber);

    @Transactional(readOnly = false)
    @Query
    void deleteExpressNumber(@Param("expressNumber") String expressNumber);

    @Query@Transactional(readOnly = true)
    List<ExpressInfoRecord> selectExpressTasks(@Param("expressInfoRecord")ExpressInfoRecord expressInfoRecord, @Param("page") PageRequest pageRequest);
}
