package com.ifrabbit.nk.express.repository;


import com.ifrabbit.nk.express.domain.CallDetail;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: WangYan
 * @Date: 2018/8/20 17:32
 * @Description:
 */
public interface CallDetailRepository
        extends MybatisRepository<CallDetail, Long>, CallDetailRepositoryCall {
    @Query@Transactional(readOnly = true)
    List<CallDetail> selectCallTasks(@Param("callDeatil")CallDetail callDetail, @Param("page")PageRequest pageRequest);
}
