package com.ifrabbit.nk.express.repository.impl;

import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import com.ifrabbit.nk.express.domain.Problem;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/16
 * Time:18:43
 */
@Slf4j
public class ExpressInfoRecordRepositoryImpl extends SqlSessionRepositorySupport {

    public ExpressInfoRecordRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    protected String getNamespace() {
        return null;
    }
}
