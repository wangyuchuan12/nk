package com.ifrabbit.nk.express.repository.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/5
 * Time:13:42
 */
public class DealTypeRepositoryImpl extends SqlSessionRepositorySupport{
    protected DealTypeRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    protected String getNamespace() {
        return null;
    }
}
