package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.Waybill;
import com.ifrabbit.nk.express.repository.WaybillRepository;
import com.ifrabbit.nk.express.service.WaybillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class WaybillServiceImpl
		extends AbstractCrudService<WaybillRepository, Waybill, Long>
		implements WaybillService {
	/**
	 * 构造函数.
	 *
	 * @param repository 注入的Repository
	 */
	@Autowired public WaybillServiceImpl(WaybillRepository repository) {
		super(repository);
	}
}
