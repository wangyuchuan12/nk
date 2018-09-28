package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.ExpressInfoTest;
import com.ifrabbit.nk.express.repository.ExpressInfoTestRepository;
import com.ifrabbit.nk.express.service.ExpressInfoTestService;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/20
 * Time:14:42
 */
@Service
@Transactional(readOnly = true)
public class ExpressInfoTestServiceImpl extends AbstractCrudService<ExpressInfoTestRepository, ExpressInfoTest,Long>
        implements ExpressInfoTestService {
    public ExpressInfoTestServiceImpl(ExpressInfoTestRepository repository) {
        super(repository);
    }
}
