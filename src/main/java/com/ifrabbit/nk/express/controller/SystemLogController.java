package com.ifrabbit.nk.express.controller;

import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.SystemLog;
import com.ifrabbit.nk.express.domain.SystemLogDTO;
import com.ifrabbit.nk.express.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/8/14
 * Time:15:11
 */
@RestController
@RequestMapping("express/systemlog")
public class SystemLogController extends AbstractPageableController<SystemLogService, SystemLog, SystemLogDTO,Long> {
    @Autowired
    private SystemLogService systemLogService;

    @RequestMapping("detail")
    public SystemLog detail(@Param(value = "expressNumber") String expressNumber){
        SystemLogDTO systemLogDTO = new SystemLogDTO();
        systemLogDTO.setFuzzyExpressNumber(expressNumber);
        SystemLog systemLog = systemLogService.findOne(systemLogDTO);
        return systemLog;
    }
}
