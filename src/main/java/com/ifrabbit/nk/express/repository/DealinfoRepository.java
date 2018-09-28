package com.ifrabbit.nk.express.repository;


import com.ifrabbit.nk.express.domain.Dealinfo;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Transactional
public interface DealinfoRepository extends MybatisRepository<Dealinfo, Long>{

    @Query
    List<Dealinfo> findTaskDetail(@Param("processId")String processId);


}
