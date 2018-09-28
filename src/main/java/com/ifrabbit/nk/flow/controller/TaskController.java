package com.ifrabbit.nk.flow.controller;

import com.ifrabbit.nk.context.UserContext;
import com.ifrabbit.nk.flow.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.uflo.model.task.Task;


@RestController
@RequestMapping("flow/task")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping
	protected Page<Task> list(Pageable pageable) {
		return taskService.find(pageable, UserContext.get().getStaffUsername());

	}

}
