package com.ifrabbit.nk.usercenter.repository;

import com.ifrabbit.nk.usercenter.domain.AppProblemparts;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mybatis.repository.annotation.Query.Operation.select_one;

public interface AppProblempartsRepository
		extends MybatisRepository<AppProblemparts, Long> {

//	@Transactional(readOnly = true)
//	@Query
//	List<BaseAddress> countCustomersWaybill(@Param("customerIds") List<Long> customerIds);

	@Query
	List<Map<String, Object>> finduflotaskByParams(@Param("params") Map<String, Object> params);

}
