package com.ifrabbit.nk.usercenter.service;

import com.ifrabbit.nk.usercenter.domain.Company;
import org.springframework.data.support.CrudService;

import java.util.List;
import java.util.Map;

public interface CompanyService extends  CrudService<Company,Long> {
    Company getByUsername(String username);

    List<Map<String, Object>> selectTelById(Long id);

    List<Map<String, Object>> selcetAllColumnByCompanyid(Long aLong);

    Company findOneByID(Long id);


    List<Company> findByName(String companyName);

    List<Company> findAll();

    Company getCompany(String  callBody,String expressNumber);

}

