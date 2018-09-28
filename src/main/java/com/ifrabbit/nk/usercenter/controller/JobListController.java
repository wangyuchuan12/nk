package com.ifrabbit.nk.usercenter.controller;

import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.usercenter.domain.JobList;
import com.ifrabbit.nk.usercenter.domain.JobListDTO;
import com.ifrabbit.nk.usercenter.service.JobListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usercenter/joblist")
public class JobListController extends AbstractPageableController<JobListService, JobList, JobListDTO, Long> {
    @Override
    @GetMapping
    @Transactional(readOnly = true)
    protected Page<JobList> list(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            JobListDTO condition) {
        Page<JobList> list = super.list(pageable, condition);
        return list;
    }
}