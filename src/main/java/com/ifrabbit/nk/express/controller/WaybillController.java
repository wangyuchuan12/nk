package com.ifrabbit.nk.express.controller;

import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.StartProcessInfo;
import com.bstek.uflo.service.TaskService;
import com.bstek.uflo.utils.EnvironmentUtils;
import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.Waybill;
import com.ifrabbit.nk.express.domain.WaybillDTO;
import com.ifrabbit.nk.express.service.WaybillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("express/waybill")
public class WaybillController extends AbstractPageableController<WaybillService, Waybill, WaybillDTO, Long> {

    @Autowired
    private ProcessService processService;
    @Autowired
    private TaskService taskService;

    @Override
    @GetMapping
    @Transactional(readOnly = true)
    protected Page<Waybill> list(
            @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable,
            WaybillDTO condition) {
        Page<Waybill> list = super.list(pageable, condition);

        if (list.hasContent()) {

            list.forEach(waybill -> {
                if (null != waybill.getProcessInstanceId()) {
                    List<Task> tasks = taskService.createTaskQuery()
                            .processInstanceId(waybill.getProcessInstanceId())
                            .addTaskState(TaskState.Created).addTaskState(TaskState.Ready)
                            .assignee(EnvironmentUtils.getEnvironment().getLoginUser())
                            .businessId(String.valueOf(waybill.getId())).list().stream()
                            .map(t -> {
                                Task task = new Task();
                                task.setId(t.getId());
                                task.setNodeName(t.getNodeName());
                                task.setAssignee(t.getAssignee());
                                task.setTaskName(t.getTaskName());
                                task.setUrl(t.getUrl());
                                task.setBusinessId(t.getBusinessId());
                                return task;
                            }).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(tasks)) {
                        waybill.setTask(tasks.get(0));
                    }

                }

            });

        }

        return list;
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    @Transactional
    protected Long create(@RequestBody Waybill waybill) {
        Long id = super.create(waybill);
        // StartProcessInfo info = new
        // StartProcessInfo(EnvironmentUtils.getEnvironment().getLoginUser());
        StartProcessInfo info = new StartProcessInfo(EnvironmentUtils.getEnvironment().getLoginUser());
        info.setBusinessId(String.valueOf(id));
        Map<String, Object> vars = new HashMap<>();
        vars.put("number", waybill.getNumber());
//		vars.put("waybill",waybill);
        info.setVariables(vars);
        ProcessInstance instance = processService.startProcessByName("测试流程", info);
        waybill.setProcessInstanceId(instance.getId());
        service.saveIgnoreNull(waybill);
        return id;
    }
}
