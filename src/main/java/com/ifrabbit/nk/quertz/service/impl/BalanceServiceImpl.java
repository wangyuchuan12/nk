package com.ifrabbit.nk.quertz.service.impl;


import com.ifrabbit.nk.quertz.domain.Balance;
import com.ifrabbit.nk.quertz.repository.BalanceRepository;
import com.ifrabbit.nk.quertz.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @Authod: chenyu
 * @Date 2018/6/10 19:58
 * Content:
 */
@Service
@Transactional
public class BalanceServiceImpl
        extends AbstractCrudService<BalanceRepository, Balance, Long>
        implements BalanceService {
    @Autowired
    public BalanceServiceImpl(BalanceRepository repository) {
        super(repository);
    }
//    @Override
//    public void save(Balance balance) {
//        Assert.notNull(balance, "Entity can not be null. [" + balance.getClass() + "]");
//
//    }
}
