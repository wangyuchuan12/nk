package com.ifrabbit.nk.express.service;

import com.ifrabbit.nk.express.domain.TmanageBalance;
import org.springframework.data.support.CrudService;

import java.util.List;
import java.util.Map;

/**
 * @author lishaomiao
 * @date 2018/3/14 11:05
 */

public interface TmanageBalanceService extends CrudService<TmanageBalance,Long>{
        List<String> search(String str);


        List<Map<String, Object>> findBalanceByParams(Map<String, Object> params);
}
