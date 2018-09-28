package com.ifrabbit.nk.express.service;

import com.ifrabbit.nk.express.domain.Problem;
import org.springframework.data.support.CrudService;

import java.util.List;
import java.util.Map;

/**
 * @author lishaomiao
 * @date 2018/3/14 11:05
 */

public interface ProblemService extends CrudService<Problem,Long>{
    Problem getByProblemId(Long problemId);
    List<Map<String, Object>> findCountNum(int staffId);
    List<Map<String, Object>> findCountByDay(Map<String, Object> params);
    List<Map<String, Object>> findCountByDayTime(int staffId);
    List<Map<String, Object>> findCountByDayMonth(int staffId);
}
