package com.ifrabbit.nk.message.service.imp;



import com.ifrabbit.nk.message.domain.SmsMiddleTable;
import com.ifrabbit.nk.message.repository.SmsMiddleTableRepository;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/7/4
 * Time:18:09
 */
@Service
public class SmsMiddleTableImpl
        extends AbstractCrudService<SmsMiddleTableRepository,SmsMiddleTable,Long>
      {

     @Autowired
    public SmsMiddleTableImpl(SmsMiddleTableRepository repository) {
        super(repository);
    }



}
