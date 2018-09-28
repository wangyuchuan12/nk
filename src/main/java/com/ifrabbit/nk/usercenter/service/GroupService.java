package com.ifrabbit.nk.usercenter.service;

import com.ifrabbit.nk.usercenter.domain.Group;
import com.ifrabbit.nk.usercenter.domain.GroupDTO;
import java.util.List;
import org.springframework.data.support.CrudService;

public interface GroupService extends CrudService<Group, Long> {

	List<Group> findTree(GroupDTO condition);

}
