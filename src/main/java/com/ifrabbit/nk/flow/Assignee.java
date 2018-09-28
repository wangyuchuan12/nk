package com.ifrabbit.nk.flow;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.assign.AssigneeProvider;
import com.bstek.uflo.process.assign.Entity;
import com.bstek.uflo.process.assign.PageQuery;
import com.ifrabbit.nk.usercenter.domain.Staff;
import com.ifrabbit.nk.usercenter.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Assignee implements AssigneeProvider {
    @Autowired
    private StaffService userService;


    @Override
    public boolean isTree() {
        return false;
    }

    @Override
    public String getName() {
        return "用户中心";
    }

    @Override
    public void queryEntities(PageQuery<Entity> pageQuery, String parentId) {

        Pageable pageable = new PageRequest(pageQuery.getPageIndex() - 1,
                pageQuery.getPageSize());
        Page<Staff> page = userService.findBasicAll(pageable, null);
        if (page.hasContent()) {
            List<Entity> entities = page.getContent().stream()
                    .map(u -> new Entity(u.getStaffUsername(), u.getStaffName()))
                    .collect(Collectors.toList());
            pageQuery.setResult(entities);
        }
    }

    @Override
    public Collection<String> getUsers(String entityId, Context context,
                                       ProcessInstance processInstance) {
        List<String> list = new ArrayList<>();
        list.add(entityId);
        return list;
    }

    @Override
    public boolean disable() {
        return false;
    }
}
