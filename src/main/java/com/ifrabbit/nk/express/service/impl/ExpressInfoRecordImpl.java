package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import com.ifrabbit.nk.express.repository.ExpressInfoRecordRepository;
import com.ifrabbit.nk.express.service.ExpressInfoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/15
 * Time:16:25
 */
@Service
@Transactional(readOnly = true)
public class ExpressInfoRecordImpl extends AbstractCrudService<ExpressInfoRecordRepository,ExpressInfoRecord,Long>
        implements ExpressInfoRecordService{
    @Autowired
    public ExpressInfoRecordImpl(ExpressInfoRecordRepository repository) {
        super(repository);
    }

    @Override
    public List<ExpressInfoRecord> findByExpressNumber(String expressNumber) {
        return null;
    }

    @Override
    public void deleteExpressNumber(String expressNumber) {

    }

    @Override
    public List<ExpressInfoRecord> selectExpressTasks(ExpressInfoRecord expressInfoRecord, PageRequest pageRequest) {
        return null;
    }

    @Override
    public List<ExpressInfoRecord> findAllExpressTasks(ExpressInfoRecord expressInfoRecord, PageRequest page) {
        return repository.selectExpressTasks(expressInfoRecord,page);
    }
}
