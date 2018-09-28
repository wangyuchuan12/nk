package com.ifrabbit.nk.express.controller;

import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.Customer;
import com.ifrabbit.nk.express.domain.CustomerDTO;
import com.ifrabbit.nk.express.service.CustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("express/customer")
public class CustomerController
		extends AbstractPageableController<CustomerService, Customer, CustomerDTO, Long> {


}
