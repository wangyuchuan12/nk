package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.Customer;
import com.ifrabbit.nk.express.repository.CustomerRepository;
import com.ifrabbit.nk.express.service.CustomerService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service @Transactional(readOnly = true)
public class CustomerServiceImpl
		extends AbstractCrudService<CustomerRepository, Customer, Long>
		implements CustomerService {
	/**
	 * 构造函数.
	 *
	 * @param repository 注入的Repository
	 */
	@Autowired public CustomerServiceImpl(CustomerRepository repository) {
		super(repository);
	}

	@Override public <X extends Customer> Page<Customer> findAll(Pageable pageable,
			X condition, String... columns) {

		Page<Customer> page = super.findAll(pageable, condition, columns);
		if (null != page && page.hasContent()) {
			List<Long> customerIds = page.getContent().stream().map(c -> c.getId())
					.collect(Collectors.toList());
			List<Customer> customers = getRepository().countCustomersWaybill(customerIds);
			if (!CollectionUtils.isEmpty(customers)) {
				Map<Long, Long> map = customers.stream().collect(
						Collectors.toMap(c -> c.getId(), c -> c.getWaybillCount()));
				page.getContent().stream().forEach(c -> {
					c.setWaybillCount(map.get(c.getId()));
				});
			}
		}
		return page;
	}
}
