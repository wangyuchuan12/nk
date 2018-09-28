package com.ifrabbit.nk.quertz.repository;

import com.ifrabbit.nk.quertz.domain.Account;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

/**
 * @Authod: chenyu
 * @Date 2018/6/11 9:56
 * Content:
 */
public interface AccountRepository extends MybatisRepository<Account,Long> {

}
