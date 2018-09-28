package com.ifrabbit.nk.express.repository.impl;

import com.ifrabbit.nk.express.repository.CallDetailRepositoryCall;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

/**
 * @Auther: WangYan
 * @Date: 2018/8/20 17:47
 * @Description:
 */
public class CallDetailRepositoryImpl extends SqlSessionRepositorySupport implements CallDetailRepositoryCall {
    @Autowired
    protected CallDetailRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    public void testCallDetail() {

    }

    @Override
    protected String getNamespace() {
        return null;
    }
}
