package com.ifrabbit.nk.express.repository.impl;
import com.ifrabbit.nk.express.repository.TempVariableRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.mybatis.repository.support.SqlSessionRepositorySupport;

/**
 * @Auther: WangYan
 * @Date: 2018/5/18 15:43
 * @Description:
 */
@Slf4j
public class VariableRepositoryImpl extends SqlSessionRepositorySupport implements TempVariableRepositoryCustom {
    protected VariableRepositoryImpl(SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
    }

    @Override
    protected String getNamespace() {
        return null;
    }

}
