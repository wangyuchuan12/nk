package com.ifrabbit.nk.express.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/8/14
 * Time:14:50
 */
@Slf4j
public class SystemLogRepositoryImpl extends SqlSessionRepositorySupport {
    public SystemLogRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    protected String getNamespace() {
        return null;
    }
}
