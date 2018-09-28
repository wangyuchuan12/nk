package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.Dealinfo;
import com.ifrabbit.nk.express.repository.DealinfoRepository;
import com.ifrabbit.nk.express.service.DealinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service @Transactional(readOnly = true)
public class DealinfoServiceImpl
		extends AbstractCrudService<DealinfoRepository, Dealinfo, Long>
		implements DealinfoService {
	/**
	 * 构造函数.
	 *
	 * @param repository 注入的Repository
	 */
	@Autowired
	private DealinfoRepository dealinfoRepository;

	@Autowired public DealinfoServiceImpl(DealinfoRepository repository) {
		super(repository);
	}

	@Override public <X extends Dealinfo> Page<Dealinfo> findAll(Pageable pageable,
			X condition, String... columns) {

		Page<Dealinfo> page = super.findAll(pageable, condition, columns);

		return page;
	}

	@Override
	public  List<Dealinfo> findTaskDetail(String processId) { return dealinfoRepository.findTaskDetail(processId); }




}
