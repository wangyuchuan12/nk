package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.SystemLog;
import com.ifrabbit.nk.express.repository.SystemLogRepository;
import com.ifrabbit.nk.express.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/8/14
 * Time:14:47
 */
@Service@Transactional(readOnly=true)
public class SystemLogServiceImpl extends AbstractCrudService<SystemLogRepository, SystemLog,Long> implements SystemLogService {
    @Autowired
    public SystemLogServiceImpl(SystemLogRepository repository) {
        super(repository);
    }
}
