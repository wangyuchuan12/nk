package com.ifrabbit.nk.flow.controller;

import com.bstek.uflo.command.impl.jump.JumpNode;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.query.ProcessInstanceQuery;
import com.bstek.uflo.query.TaskQuery;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.TaskService;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.domain.ProblemDTO;
import com.ifrabbit.nk.express.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: WangYan
 * @Date: 2018/5/2 14:06
 * @Description:
 */
@RestController
@RequestMapping("flow/processInstance")
public class ProcessInstanceController {

    @Autowired
    private ProcessService processService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private TaskService taskService;



    @GetMapping
    protected Page<ProcessInstance> list(Pageable pageable) {
        ProcessInstanceQuery query = processService.createProcessInstanceQuery();
        int count = query.count();
        List<ProcessInstance> list = query.addOrderDesc("id").page(pageable.getOffset(), pageable.getPageSize()).list();
        list.forEach(processInstance -> {
            String businessId = processInstance.getBusinessId();
            ProblemDTO cond = new ProblemDTO();
            cond.setId(Long.valueOf(businessId));
            Problem one = problemService.findOne(cond);
            processInstance.setSubject(one.getProblemparts_type().toString());
        });
        Page<ProcessInstance> page = new PageImpl<>(list, pageable, count);
        return page;
    }
    @RequestMapping("findType")
    public String findType(@Param("businessId")Long businessId ){
        ProblemDTO cond = new ProblemDTO();
        cond.setId(businessId);
        Problem problem = problemService.findOne(cond);
        Integer type = problem.getProblemparts_type();
        switch (type){
            case  1:
                return "签收未收到";
            case  2:
                return  "破损";
            case  3:
                return "改地址";
            case  4:
                return "退回";
            case  5:
                return "催单";
        }
        return  "boom";
    }
    @RequestMapping("complete")
    public String complete(@Param("id")Long id,@Param("currentNode") String currentNode){
        try {
            TaskQuery taskQuery = taskService.createTaskQuery();
            taskQuery.processInstanceId(id).nodeName(currentNode);
            List<Task> list = taskQuery.list();
            Task task = list.get(0);
            taskService.complete(task.getId());
            return "已完成";
        }catch (Exception e){
            throw  e;
        }
    }
    @RequestMapping("jump")
    public List<JumpNode> jump(@Param("id")Long id ,@Param("currentNode") String currentNode){
        try {
            TaskQuery taskQuery = taskService.createTaskQuery();
            taskQuery.processInstanceId(id).nodeName(currentNode);
            List<Task> list = taskQuery.list();
            Task task = list.get(0);
            List<JumpNode> nodes = taskService.getAvaliableForwardTaskNodes(task.getId());
            return  nodes;
        }catch (Exception e){
            throw  e;
        }
    }
    @RequestMapping("show")
    public Long show(@Param("id")Long id ,@Param("currentNode") String currentNode){
        try {
            TaskQuery taskQuery = taskService.createTaskQuery();
            taskQuery.processInstanceId(id).nodeName(currentNode);
            List<Task> list = taskQuery.list();
            Task task = list.get(0);
            return task.getId();
        }catch (Exception e){
            throw  e;
        }
    }
}
