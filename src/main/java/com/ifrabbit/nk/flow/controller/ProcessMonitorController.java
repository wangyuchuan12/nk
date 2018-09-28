package com.ifrabbit.nk.flow.controller;

import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.service.TaskService;
import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.usercenter.domain.UfloTask;
import com.ifrabbit.nk.usercenter.domain.UfloTaskDTO;
import com.ifrabbit.nk.usercenter.service.UfloTaskService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * @Auther: lishaomiao
 * @Date: 2018/7/8 14:06
 * @Description: 流程监测页
 */

@RestController
@RequestMapping("flow/processMonitor")
public class ProcessMonitorController  extends AbstractPageableController<UfloTaskService,UfloTask,UfloTaskDTO,Long> {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ProcessMonitorController.class);
    @Autowired
    private TaskService taskService;
    @Autowired
    private UfloTaskService ufloTaskService;

    @GetMapping
    @Transactional(readOnly = true)
    protected Page<UfloTask> list(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            UfloTaskDTO condition) {
        return ufloTaskService.findAll(pageable, condition);
    }


    @RequestMapping("task")
    public void music(@RequestParam("taskId") Long taskId, @RequestParam("type") String type) {
        if(StringUtils.isBlank(String.valueOf(taskId)) || StringUtils.isBlank(type)){
            return;
        }
        Task task = taskService.getTask(taskId);
        TaskState taskState = task.getState();
        if(taskState.equals(TaskState.Ready)||taskState.equals(TaskState.Reserved)||taskState.equals(TaskState.InProgress)){
            if(type.equals("1")){
                taskService.suspend(taskId);//任务挂起
                logger.info("=============任务挂起成功=============");
            }
        }else if (taskState.equals(TaskState.Suspended) && type.equals("2")){
                taskService.resume(taskId);//任务恢复
                logger.info("=============任务恢复=============");
        }
    }

}
