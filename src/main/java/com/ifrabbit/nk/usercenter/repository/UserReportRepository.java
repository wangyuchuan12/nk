package com.ifrabbit.nk.usercenter.repository;

import com.ifrabbit.nk.usercenter.domain.UserReport;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/14
 * Time:13:07
 */

@Transactional
public interface UserReportRepository extends MybatisRepository<UserReport,Long> {

    @Query
    List<UserReport> findByAnswer();
    @Query
    List<UserReport> findByUserReport(@Param("mainId")Long mainId);
    @Query
    void deleteSysRLtnId(@Param("mainId")Long mainId);

}
