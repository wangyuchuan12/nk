package com.ifrabbit.nk.quertz.controller;

import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.quertz.domain.Balance;
import com.ifrabbit.nk.quertz.domain.BalanceDTO;
import com.ifrabbit.nk.quertz.service.BalanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("express/balance")
public class BalanceController
		extends AbstractPageableController<BalanceService, Balance, BalanceDTO, Long> {

}
