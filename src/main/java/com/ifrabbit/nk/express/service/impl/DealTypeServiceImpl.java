package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.DealType;
import com.ifrabbit.nk.express.repository.DealTypeRepository;
import com.ifrabbit.nk.express.service.DealTypeService;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;


/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/5
 * Time:13:40
 */
@Service
public class DealTypeServiceImpl extends AbstractCrudService<DealTypeRepository, DealType,Long>
        implements DealTypeService {

    public DealTypeServiceImpl(DealTypeRepository repository) {
        super(repository);
    }
}
