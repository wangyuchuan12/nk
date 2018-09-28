package com.ifrabbit.nk.express.service;

import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.data.support.CrudService;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/15
 * Time:16:24
 */
public interface ExpressInfoRecordService extends CrudService<ExpressInfoRecord,Long> {
    List<ExpressInfoRecord> findByExpressNumber(String expressNumber);
    List<ExpressInfoRecord> findAllExpressTasks(ExpressInfoRecord expressInfoRecord, PageRequest page);
    void deleteExpressNumber(String expressNumber);
    List<ExpressInfoRecord> selectExpressTasks(ExpressInfoRecord expressInfoRecord,PageRequest pageRequest);
}
