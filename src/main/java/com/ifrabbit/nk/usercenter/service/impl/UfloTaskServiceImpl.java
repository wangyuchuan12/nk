package com.ifrabbit.nk.usercenter.service.impl;

import com.ifrabbit.nk.usercenter.domain.Staff;
import com.ifrabbit.nk.usercenter.domain.UfloTask;
import com.ifrabbit.nk.usercenter.repository.StaffRepository;
import com.ifrabbit.nk.usercenter.repository.UfloTaskRepository;
import com.ifrabbit.nk.usercenter.service.StaffService;
import com.ifrabbit.nk.usercenter.service.UfloTaskService;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/7/5
 * Time:15:37
 */
@Service
@Transactional(readOnly = true)
public class UfloTaskServiceImpl  extends AbstractCrudService<UfloTaskRepository, UfloTask, Long> implements UfloTaskService {
    public UfloTaskServiceImpl(UfloTaskRepository repository) {
        super(repository);
    }
}
