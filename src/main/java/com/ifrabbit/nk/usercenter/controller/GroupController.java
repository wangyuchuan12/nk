package com.ifrabbit.nk.usercenter.controller;

import com.ifrabbit.nk.usercenter.domain.Group;
import com.ifrabbit.nk.usercenter.domain.GroupDTO;
import com.ifrabbit.nk.usercenter.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usercenter/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    List<Group> list(GroupDTO condition) {
        return groupService.findTree(condition);
    }

    @GetMapping("flatList")
    List<Group> flatList(GroupDTO condition) {
        return this.groupService.findAll(condition);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Long create(
            @RequestBody Group group) {
        if (null == group.getPid()) {
            group.setPid(Group.ROOT_PID);
        }
        groupService.insert(group);
        return group.getId();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void modify(
            @PathVariable("id") Long id, @RequestBody Group group) {
        group.setId(id);
        if (null == group.getPid()) {
            group.setPid(Group.ROOT_PID);
        }
        groupService.updateIgnore(group);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable("id") Long id) {
        groupService.delete(id);
    }

}
