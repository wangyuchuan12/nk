package com.ifrabbit.nk.express.service;

import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import org.springframework.data.support.CrudService;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/16
 * Time:17:20
 */
public interface ExpressInfoDetailService extends CrudService<ExpressInfoDetail,Long> {

     ExpressInfoDetail findByType(Integer expressType, String expressNumber);

    String expressAnalysis (ExpressInfoRecord expressInfoRecord,String expressNumber,Long businessIdLong,Integer analysisType,Long taskID);
}
