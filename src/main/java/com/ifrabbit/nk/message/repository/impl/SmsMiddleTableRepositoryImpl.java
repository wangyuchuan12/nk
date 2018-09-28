package com.ifrabbit.nk.message.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;
/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/7/4
 * Time:18:12
 */
@Slf4j
public class SmsMiddleTableRepositoryImpl extends SqlSessionRepositorySupport{

    protected SmsMiddleTableRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    protected String getNamespace() {
        return null;
    }
}
