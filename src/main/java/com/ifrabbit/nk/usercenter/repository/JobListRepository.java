package com.ifrabbit.nk.usercenter.repository;

import com.ifrabbit.nk.express.domain.Customer;
import com.ifrabbit.nk.usercenter.domain.JobList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JobListRepository extends MybatisRepository<JobList, Long> {
    //@Query
    //Page<JobList> findAllByMySelf(Pageable pageable);
}
