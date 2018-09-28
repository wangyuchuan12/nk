package com.ifrabbit.nk.express.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/16
 * Time:18:43
 */
@Slf4j
public class ExpressInfoTestRepositoryImpl extends SqlSessionRepositorySupport {

    public ExpressInfoTestRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    protected String getNamespace() {
        return null;
    }
}
