package com.ifrabbit.nk.express.service;


import com.ifrabbit.nk.express.domain.Dealinfo;
import org.springframework.data.support.CrudService;

import java.util.List;

public interface DealinfoService extends CrudService<Dealinfo, Long> {

   List<Dealinfo> findTaskDetail(String processId);




}
