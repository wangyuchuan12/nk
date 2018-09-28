package com.ifrabbit.nk.express.repository.impl;

import com.ifrabbit.nk.express.domain.Customer;
import com.ifrabbit.nk.express.repository.MessagesRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

@Slf4j
public class MessagesRepositoryImpl extends SqlSessionRepositorySupport implements MessagesRepositoryCustom {

    @Autowired
    protected MessagesRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    protected String getNamespace() {
        return Customer.class.getName();
    }

}
