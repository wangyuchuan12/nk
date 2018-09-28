package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.domain.ProblemDTO;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.ProblemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lishaomiao
 * @date 2018/3/14 11:05
 */
@Service@Transactional(readOnly = true)
public class ProblemServiceImpl
        extends AbstractCrudService<ProblemRepository,Problem,Long>
        implements ProblemService {

    @Autowired
    public ProblemRepository ProblemRepository;

    @Autowired
    public ProblemServiceImpl(ProblemRepository repository) {
        super(repository);
    }

    @Override
    @Transactional(readOnly = true)
    public Problem getByProblemId(Long problemId) {
        ProblemDTO condition = new ProblemDTO();
        condition.setId(problemId);
        return getRepository().findOne(condition);
    }
    @Override
    public List<Map<String, Object>> findCountNum(int staffId) {
        if(StringUtils.isNotBlank(String.valueOf(staffId))){
            return ProblemRepository.findCountNum(staffId);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> findCountByDay(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("staffId", params.get("staffId"));
        map.put("createTime", params.get("createTime"));
        List<Map<String, Object>> list = ProblemRepository.findCountByDay(map);
        return list;
    }


    @Override
    public List<Map<String, Object>> findCountByDayTime(int staffId) {
        if(StringUtils.isBlank(String.valueOf(staffId))){
            return null;
        }
        return ProblemRepository.findCountByDayTime(staffId);
    }

    @Override
    public List<Map<String, Object>> findCountByDayMonth(int staffId) {
        if(StringUtils.isBlank(String.valueOf(staffId))){
            return null;
        }
        return ProblemRepository.findCountByDayMonth(staffId);
    }


}
