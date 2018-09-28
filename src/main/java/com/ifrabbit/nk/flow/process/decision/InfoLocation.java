package com.ifrabbit.nk.flow.process.decision;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import com.ifrabbit.nk.express.domain.Problem;
import com.ifrabbit.nk.express.repository.ExpressInfoDetailRepository;
import com.ifrabbit.nk.express.repository.ExpressInfoRecordRepository;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
import com.ifrabbit.nk.express.service.ProblemService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/4/20
 * Time:16:54
 */

/**
 * 破损：判断物流位置
 * 三种位置：A已签收  B派件中  C其他所有状态
 */
@Component("破损：判断物流位置")
public class InfoLocation implements DecisionHandler {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(InfoLocation.class);

    @Autowired
    ExpressInfoDetailService expressInfoDetailService;

    @Autowired
    public ExpressInfoDetailRepository expressInfoDetailRepository;

    @Autowired
    public ExpressInfoRecordRepository expressInfoRecordRepository;


    @Autowired
    ProblemRepository problemRepository;


    @Override
    public String handle(Context context, ProcessInstance processInstance) {
        System.out.println();
        System.out.println();
        System.out.println();

        logger.info("判断物流位置.......");

        String test = test(context,processInstance);
        return test;
    }

    public String test(Context context, ProcessInstance processInstance){

        //解析后开始调用数据库查询
        Object express = context.getProcessService().getProcessVariable("express_type",
                processInstance.getId());
        Integer express_type = (int)(express);
        logger.info("查询物流是否签收 " + express_type);
        String type = "无状态";

        switch (express_type) {
            case 20:
                type = "A状态";
                break;
            case 30:
                type = "B状态";
                break;
            case 40:
                type = "C状态";
                break;
            case 50:
                type = "C状态";
                break;
        }
        return type;
    }
}
