package com.ifrabbit.nk.express.repository;

import com.ifrabbit.nk.express.domain.TableInfo;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;

/**
 * @Auther: WangYan
 * @Date: 2018/7/5 11:34
 * @Description:
 */
public interface TableInfoRepository extends MybatisRepository<TableInfo, Long> {

    @Query
    TableInfo findCurrentTask(@Param("processInstanceId")String processId);

}
