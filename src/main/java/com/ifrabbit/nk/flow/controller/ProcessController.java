package com.ifrabbit.nk.flow.controller;

import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.query.ProcessQuery;
import com.bstek.uflo.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author Jarvis Song
 */
@RestController
@RequestMapping("flow/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @GetMapping
    protected Page<ProcessDefinition> list(Pageable pageable) {
        ProcessQuery query = processService.createProcessQuery();
        int count = query.count();
        List<ProcessDefinition> list = query.addOrderDesc("id").page(pageable.getOffset(), pageable.getPageSize()).list();
        for (int i=0 ; i<list.size()-1;i++){
            for (int j=list.size()-1;j>i;j--){
                ProcessDefinition processDefinition = list.get(i);
                ProcessDefinition processDefinition1 = list.get(j);
                if  (processDefinition1.getName().equals(processDefinition.getName())){
                    if  (processDefinition1.getVersion()>processDefinition.getVersion()){
                        list.remove(i);
                    }else{
                        list.remove(j);
                    }
                }
            }
        }
        Page<ProcessDefinition> page = new PageImpl<>(list, pageable, count);
        return page;
    }
}
