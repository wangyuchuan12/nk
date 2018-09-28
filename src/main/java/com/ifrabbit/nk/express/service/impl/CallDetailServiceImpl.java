package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.express.repository.CallDetailRepository;
import com.ifrabbit.nk.express.service.CallDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Auther: WangYan
 * @Date: 2018/8/20 17:31
 * @Description:
 */
@Service @Transactional(readOnly = true)
public class CallDetailServiceImpl
        extends AbstractCrudService<CallDetailRepository, CallDetail, Long>
        implements CallDetailService {
    @Autowired
    public CallDetailServiceImpl(CallDetailRepository repository) {
        super(repository);
    }
    @Override
    public List<CallDetail> findAllCallTasks(CallDetail callDetail, PageRequest page) {
        return repository.selectCallTasks(callDetail,page);}
}
