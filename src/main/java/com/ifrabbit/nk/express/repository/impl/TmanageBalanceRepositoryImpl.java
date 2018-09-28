package com.ifrabbit.nk.express.repository.impl;

import com.ifrabbit.nk.express.domain.TmanageBalance;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

/**
 * @author lishaomiao
 * @date 2018/3/14 11:05
 */


@Slf4j
public class TmanageBalanceRepositoryImpl extends SqlSessionRepositorySupport {

    @Autowired
    protected TmanageBalanceRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    protected String getNamespace() {
        return TmanageBalance.class.getName();
    }
}
