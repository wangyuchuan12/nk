package com.ifrabbit.nk.flow.service;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bstek.uflo.model.task.Task;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TaskService {

	Page<Task> find(Pageable pageable, String username);

	void createHisTask(ProcessInstance processInstance, Context context, String taskName, String option, String uuid);

	public List<Map<String, Object>> findAllByProcessId(Long processId) throws SQLException;

}
