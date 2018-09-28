package com.ifrabbit.nk.usercenter.controller;

import com.bstek.uflo.utils.EnvironmentUtils;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.domain.CompanyDTO;
import com.ifrabbit.nk.usercenter.domain.Staff;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import com.ifrabbit.nk.usercenter.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usercenter")
public class ShowCompanyController {
	@Autowired
	private StaffService staffService;
	@Autowired
	private CompanyService companyService;

	@RequestMapping("/showcompany")
	public Company getCompany(){
		String loginUser = EnvironmentUtils.getEnvironment().getLoginUser();
		Staff staff = staffService.getByUsername(loginUser);
		String companyName = staff.getStaffVarparama();
		if(companyName == null){
			return null;
		}else {
			Company company = companyService.getByUsername(companyName);
			System.out.println("company==================="+company);
			return company;
		}
	}
}
