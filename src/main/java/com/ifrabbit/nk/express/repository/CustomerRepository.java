package com.ifrabbit.nk.express.repository;

import com.ifrabbit.nk.express.domain.Customer;
import java.util.List;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository
		extends MybatisRepository<Customer, Long>, CustomerRepositoryCustom {

	@Transactional(readOnly = true)
	@Query
	List<Customer> countCustomersWaybill(@Param("customerIds") List<Long> customerIds);

}
