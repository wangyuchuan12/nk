package com.ifrabbit.nk.express.service.impl;
import com.ifrabbit.nk.express.domain.TempVariable;
import com.ifrabbit.nk.express.repository.TempVariableRepository;
import com.ifrabbit.nk.express.service.TempVariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lishaomiao
 * @date 2018/4/23 11:05
 */
@Service@Transactional(readOnly = true)
public class TempTaskServiceImpl
        extends AbstractCrudService<TempVariableRepository,TempVariable,Long>
        implements TempVariableService {

    @Autowired
    public TempTaskServiceImpl(TempVariableRepository repository) {
        super(repository);
    }

}
