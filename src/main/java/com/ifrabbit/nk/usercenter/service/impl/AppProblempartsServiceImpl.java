package com.ifrabbit.nk.usercenter.service.impl;

import com.ifrabbit.nk.express.repository.TmanageBalanceRepository;
import com.ifrabbit.nk.usercenter.domain.AppProblemparts;
import com.ifrabbit.nk.usercenter.repository.AppProblempartsRepository;
import com.ifrabbit.nk.usercenter.service.AppProblempartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AppProblempartsServiceImpl extends AbstractCrudService<AppProblempartsRepository, AppProblemparts, Long> implements AppProblempartsService {

    @Autowired
    public AppProblempartsRepository appProblempartsRepository;

    /**
     * 构造函数.
     *
     * @param repository 注入的Repository
     */
    @Autowired
    public AppProblempartsServiceImpl(AppProblempartsRepository repository) {
        super(repository);
    }

    @Override
    public <X extends AppProblemparts> Page<AppProblemparts> findAll(Pageable pageable, X condition, String... columns) {
        Page<AppProblemparts> page = super.findAll(pageable, condition, columns);
        return page;
    }

    @Override
    public List<Map<String, Object>> finduflotaskByParams(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", params.get("name"));
//        map.put("beginTime", params.get("beginTime"));
//        map.put("endTime", params.get("endTime"));
        map.put("ip",params.get("ip"));
        List<Map<String, Object>> list = appProblempartsRepository.finduflotaskByParams(map);
        return list;
    }
}
