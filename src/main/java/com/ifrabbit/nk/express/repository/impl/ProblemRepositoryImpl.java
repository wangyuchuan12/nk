package com.ifrabbit.nk.express.repository.impl;

import com.ifrabbit.nk.express.domain.Problem;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

/**
 * @author lishaomiao
 * @date 2018/3/14 11:05
 */


@Slf4j
public class ProblemRepositoryImpl extends SqlSessionRepositorySupport {

    @Autowired
    protected ProblemRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    protected String getNamespace() {
        return Problem.class.getName();
    }
}
