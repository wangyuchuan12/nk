package com.ifrabbit.nk.express.repository;

import com.ifrabbit.nk.express.domain.Messages;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

public interface MessagesRepository extends MybatisRepository<Messages, Long> {

}
