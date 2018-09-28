package com.ifrabbit.nk.message.repository;

import com.ifrabbit.nk.message.domain.SmsMiddleTable;
import groovy.transform.TailRecursive;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/7/4
 * Time:18:11
 */
public interface SmsMiddleTableRepository extends MybatisRepository<SmsMiddleTable, Long> {

    @Transactional(readOnly = true)
    @Query
    List<SmsMiddleTable> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
