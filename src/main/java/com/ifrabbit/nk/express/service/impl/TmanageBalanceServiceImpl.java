package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.TmanageBalance;
import com.ifrabbit.nk.express.repository.TmanageBalanceRepository;
import com.ifrabbit.nk.express.service.TmanageBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lishaomiao
 * @date 2018/3/14 11:05
 */
@Service@Transactional(readOnly = true)
public class TmanageBalanceServiceImpl
        extends AbstractCrudService<TmanageBalanceRepository,TmanageBalance,Long>
        implements TmanageBalanceService {

    @Autowired
    public TmanageBalanceServiceImpl(TmanageBalanceRepository repository) {
        super(repository);
    }

    @Autowired
    public TmanageBalanceRepository tmanageBalanceRepository;

    @Override
    public List<String> search(String str) {
        List<String> search = tmanageBalanceRepository.search(str);
        return search;
    }


    @Override
    public List<Map<String, Object>> findBalanceByParams(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("balance_companyname", params.get("balance_companyname"));
        map.put("beginTime", params.get("beginTime"));
        map.put("endTime", params.get("endTime"));
        List<Map<String, Object>> list = tmanageBalanceRepository.findBalanceByParams(map);
        return list;
    }
}
