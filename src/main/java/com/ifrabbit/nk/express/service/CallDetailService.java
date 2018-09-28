package com.ifrabbit.nk.express.service;

import com.ifrabbit.nk.express.domain.CallDetail;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.CrudService;

import java.util.List;

/**
 * @Auther: WangYan
 * @Date: 2018/8/20 17:29
 * @Description:
 */
public interface CallDetailService extends CrudService<CallDetail,Long> {
    List<CallDetail> findAllCallTasks(CallDetail callDetail, PageRequest page);
}
