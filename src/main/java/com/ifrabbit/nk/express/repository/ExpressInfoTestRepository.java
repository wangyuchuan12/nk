package com.ifrabbit.nk.express.repository;

import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import com.ifrabbit.nk.express.domain.ExpressInfoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/15
 * Time:16:27
 */
public interface ExpressInfoTestRepository extends MybatisRepository<ExpressInfoTest,Long> {

}
