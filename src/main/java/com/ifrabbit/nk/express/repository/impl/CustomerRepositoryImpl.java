package com.ifrabbit.nk.express.repository.impl;

import com.ifrabbit.nk.express.domain.Customer;
import com.ifrabbit.nk.express.repository.CustomerRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

@Slf4j
public class CustomerRepositoryImpl extends SqlSessionRepositorySupport implements CustomerRepositoryCustom {

    @Autowired
    protected CustomerRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    protected String getNamespace() {
        return Customer.class.getName();
    }

    @Override
    public void testCustom() {
        log.debug("TestCustom run.");
    }
}
