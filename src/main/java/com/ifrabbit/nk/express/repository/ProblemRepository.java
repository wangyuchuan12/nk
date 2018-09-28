package com.ifrabbit.nk.express.repository;

import com.ifrabbit.nk.express.domain.Problem;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author lishaomiao
 * @date 2018/3/14 11:05
 */
public interface ProblemRepository extends MybatisRepository<Problem,Long>,ProblemRepositoryCustom {

    @Query
    @Transactional(readOnly = true)
    public List<Problem> findByNumber(@Param("number") Long number);

    @Query
    @Transactional(readOnly = false)
    public void deleteByNumber(@Param("number") Long number);

    @Query
    List<Map<String, Object>> findCountNum(@Param("str")int staffId);

    @Query
    List<Map<String, Object>> findCountByDay(@Param("params") Map<String, Object> params);

    @Query
    List<Map<String, Object>> findCountByDayTime(@Param("str")int staffId);

    @Query
    List<Map<String, Object>> findCountByDayMonth(@Param("str")int staffId);
}
