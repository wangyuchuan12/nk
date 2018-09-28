package com.ifrabbit.nk.quertz.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

/**
 * @Authod: chenyu
 * @Date 2018/6/11 10:34
 * Content:
 */
@Slf4j
public class OriginalDateRepositoryImpl extends SqlSessionRepositorySupport {
    @Autowired
    protected OriginalDateRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }
    @Override
    protected String getNamespace() {
        return null;
    }
    @Override
    protected int insert(String statement) {
        return super.insert(statement);
    }
    @Override
    protected int insert(String statement, Object parameter) {
        return super.insert(statement, parameter);
    }
}
