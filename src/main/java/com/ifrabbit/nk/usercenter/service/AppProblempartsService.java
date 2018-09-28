package com.ifrabbit.nk.usercenter.service;

import com.ifrabbit.nk.usercenter.domain.AppProblemparts;
import org.springframework.data.support.CrudService;

import java.util.List;
import java.util.Map;

public interface AppProblempartsService extends CrudService<AppProblemparts, Long> {

    List<Map<String, Object>> finduflotaskByParams(Map<String, Object> params);
}
