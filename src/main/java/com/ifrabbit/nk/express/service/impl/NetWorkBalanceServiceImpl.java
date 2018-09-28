package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.NetWorkBalance;
import com.ifrabbit.nk.express.repository.NetWorkBalanceRepository;
import com.ifrabbit.nk.express.service.NetWorkBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lishaomiao
 * @date 2018/6/1 11:05
 */
@Service@Transactional(readOnly = true)
public class NetWorkBalanceServiceImpl
        extends AbstractCrudService<NetWorkBalanceRepository,NetWorkBalance,Long>
        implements NetWorkBalanceService {

    @Autowired
    public NetWorkBalanceServiceImpl(NetWorkBalanceRepository repository) {
        super(repository);
    }


}
