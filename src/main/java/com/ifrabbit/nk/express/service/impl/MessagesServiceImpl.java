package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.Messages;
import com.ifrabbit.nk.express.repository.MessagesRepository;
import com.ifrabbit.nk.express.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service @Transactional(readOnly = true)
public class MessagesServiceImpl
		extends AbstractCrudService<MessagesRepository, Messages, Long>
		implements MessagesService {
	/**
	 * 构造函数.
	 * @param repository 注入的Repository
	 */
	@Autowired public MessagesServiceImpl(MessagesRepository repository) {
		super(repository);
	}
}
