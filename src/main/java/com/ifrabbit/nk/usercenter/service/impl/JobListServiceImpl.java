package com.ifrabbit.nk.usercenter.service.impl;

import com.ifrabbit.nk.usercenter.domain.JobList;
import com.ifrabbit.nk.usercenter.repository.JobListRepository;
import com.ifrabbit.nk.usercenter.service.JobListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class JobListServiceImpl
        extends AbstractCrudService<JobListRepository, JobList, Long>
        implements JobListService {
    /**
     * 构造函数.
     *
     * @param repository 注入的Repository
     */
    @Autowired
    public JobListServiceImpl(JobListRepository repository) {
        super(repository);
    }


}
