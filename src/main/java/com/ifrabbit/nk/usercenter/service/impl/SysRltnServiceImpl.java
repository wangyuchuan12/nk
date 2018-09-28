package com.ifrabbit.nk.usercenter.service.impl;




/**
 * @Auther: lishaomiao
 */
import com.ifrabbit.nk.usercenter.domain.SysRltn;
import com.ifrabbit.nk.usercenter.repository.SysRltnRepository;
import com.ifrabbit.nk.usercenter.service.SysRltnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(readOnly = true)
public class SysRltnServiceImpl
        extends AbstractCrudService<SysRltnRepository, SysRltn, Long>
        implements SysRltnService {


    @Autowired
    public SysRltnServiceImpl(SysRltnRepository repository) {
        super(repository);
    }


}
