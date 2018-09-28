package com.ifrabbit.nk.usercenter.service;

import com.ifrabbit.nk.usercenter.domain.Operate;
import java.util.List;
import org.springframework.data.support.CrudService;

public interface OperateService extends CrudService<Operate, Long> {

	List<Operate> importOperate(List<Operate> allOperate);
}
