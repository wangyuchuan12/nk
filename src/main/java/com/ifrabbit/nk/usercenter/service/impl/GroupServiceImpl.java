package com.ifrabbit.nk.usercenter.service.impl;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import com.ifrabbit.nk.usercenter.domain.Group;
import com.ifrabbit.nk.usercenter.domain.GroupDTO;
import com.ifrabbit.nk.usercenter.repository.GroupRepository;
import com.ifrabbit.nk.usercenter.service.GroupService;
import ir.nymph.collection.CollectionUtil;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service public class GroupServiceImpl
		extends AbstractCrudService<GroupRepository, Group, Long>
		implements GroupService {

	/**
	 * 构造函数.
	 *
	 * @param repository 注入的Repository
	 */
	@Autowired public GroupServiceImpl(GroupRepository repository) {
		super(repository);
	}

	@Override @Transactional(readOnly = true) public List<Group> findTree(
			GroupDTO condition) {
		List<Group> list = findAll(new Sort(ASC, "lft"), condition);

		if (CollectionUtil.isEmpty(list)) {
			return Collections.emptyList();
		}

		// 构造一个虚拟根节点
		Group root = new Group();
		root.setId(0L);
		root.setLft(0L);
		root.setRgt(list.size() * 2L + 1L);
		list.add(0, root);
		return buildTree(list).getChildren();
	}

	private Group buildTree(List<Group> list) {
		Stack<Group> stack = new Stack<>();
		list.forEach(group -> {
			if (stack.isEmpty()) {
				stack.push(group);
			}
			else {
				while (!stack.isEmpty() && stack.lastElement().getRgt() < group
						.getRgt()) {
					stack.pop();
				}
				if (!stack.isEmpty()) {
					stack.lastElement().addChild(group);
				}
				stack.push(group);
			}
		});
		return stack.firstElement();
	}

	private void rebuild() {

		List<Group> list = findAll(new Sort(DESC, "sort"), null);
		if (CollectionUtil.isEmpty(list)) {
			return;
		}

		// 构造一个虚拟根节点
		Group root = new Group();
		root.setId(Group.ROOT_PID);
		root.setLft(0L);
		root.setRgt(list.size() * 2L + 1L);
		list.add(0, root);

		convertAdjacencyToPreorderTraversal(list, root, 0);
		list.forEach(g -> {
			if (g.getId() == 0) {
				return;
			}
			super.updateIgnore(g);
		});
	}

	private long convertAdjacencyToPreorderTraversal(List<Group> all, Group parent,
			long lft) {
		long rgt = lft + 1;
		List<Group> children = searchChildren(all, parent.getId());
		if (CollectionUtil.isNotEmpty(children)) {
			for (Group child : children) {
				rgt = convertAdjacencyToPreorderTraversal(all, child, rgt);
			}
		}
		parent.setLft(lft);
		parent.setRgt(rgt);
		return rgt + 1;
	}

	private List<Group> searchChildren(List<Group> list, Long pid) {
		return list.stream().filter(g -> pid.equals(g.getPid()))
				.collect(Collectors.toList());
	}

	@Override @Transactional public void insert(Group entity) {
		super.insert(entity);
		rebuild();
	}

	@Override @Transactional public void updateIgnore(Group entity) {
		super.updateIgnore(entity);
		rebuild();
	}

	@Override @Transactional public void delete(Long id) {
		super.delete(id);
		rebuild();
	}
}
