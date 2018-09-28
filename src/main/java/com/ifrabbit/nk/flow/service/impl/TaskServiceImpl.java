package com.ifrabbit.nk.flow.service.impl;

import com.alibaba.druid.util.JdbcUtils;
import com.bstek.uflo.command.impl.SaveHistoryTaskCommand;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.model.task.TaskType;
import com.bstek.uflo.query.TaskQuery;
import com.ifrabbit.nk.context.UserContext;
import com.ifrabbit.nk.flow.service.TaskService;
import com.ifrabbit.nk.flow.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private DataSource dataSource;

    @Qualifier("uflo.taskService")
    @Autowired
    private com.bstek.uflo.service.TaskService decorator;

    @Override
    public List<Map<String, Object>>  findAllByProcessId(Long processId) throws SQLException {
        List<Map<String, Object>> list = JdbcUtils.executeQuery(dataSource,
                "select NODE_NAME_ nodeName from UFLO_PROCESS up where up.ID_ = ?",processId);
        return list;
    }

    @Override
    public void createHisTask(ProcessInstance processInstance,Context context,String taskName,String option,String uuid) {

        Task task=new Task();
        task.setSubject(uuid);
        task.setProgress(null);
        task.setCountersignCount(0);
        task.setProcessId(processInstance.getProcessId());
        task.setDescription("");
        task.setPriority(null);
        task.setPrevTask("未知");
        task.setPrevState(null);
        task.setOpinion(option);
        task.setOwner(processInstance.getPromoter());
        task.setEndDate(null);
        task.setDuedate(null);
        task.setDueActionDate(null);
        task.setDateUnit(null);
        task.setNodeName("呼叫收件人");
        task.setType(TaskType.Normal);
        task.setState(TaskState.Completed);
        task.setUrl(null);
        task.setBusinessId(processInstance.getBusinessId());
        task.setTaskName(taskName);
        task.setAssignee("智能客服");
        task.setCreateDate(new Date());
        task.setProcessInstanceId(processInstance.getId());
        task.setRootProcessInstanceId(processInstance.getRootId());
        context.getCommandService().executeCommand(new SaveHistoryTaskCommand(task,processInstance));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Task> find(Pageable pageable, String username) {
        TaskQuery taskQuery = decorator.createTaskQuery().addTaskState(TaskState.Created)
                .addTaskState(TaskState.Ready)
                .addAssignee(UserContext.get().getStaffUsername());
        int count = taskQuery.count();
        List<Task> list = taskQuery.page(pageable.getOffset(), pageable.getPageSize())
                .list();
        if (!CollectionUtils.isEmpty(list)) {
//            list.forEach(t -> {
//                t.getTaskParticipators().forEach(p -> {
//                    p.getId();
//                });
//            });
        }
        Page<Task> page = new PageImpl<>(list, pageable, count);
        return page;
    }
}
