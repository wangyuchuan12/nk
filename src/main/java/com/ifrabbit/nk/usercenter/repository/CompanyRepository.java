package com.ifrabbit.nk.usercenter.repository;

import com.ifrabbit.nk.usercenter.domain.Company;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mybatis.repository.annotation.Query.Operation.select_one;

public interface CompanyRepository extends MybatisRepository<Company,Long> {
    @Query(operation=select_one)
    String getMaxLayByParent(@Param("company_parentid") Long company_parentid);

    @Query
    List<Map<String, Object>> findByParams(Map<String, Object> params);

    @Query
    List<Company> findByName(@Param("companyName") String companyName);

    @Transactional(readOnly = true)
    @Query
    List<Company> findById(@Param("id") String id);
}
