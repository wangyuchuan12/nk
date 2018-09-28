package com.ifrabbit.nk.express.controller;

import com.ifrabbit.nk.controller.AbstractPageableController;
import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.express.domain.CallDetailDTO;
import com.ifrabbit.nk.express.service.CallDetailService;
import com.ifrabbit.nk.flow.process.utils.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



/**
 * @Auther: WangYan
 * @Date: 2018/8/20 17:27
 * @Description:
 */
@RestController
@RequestMapping("express/calldetail")
public class CallDetailController
        extends AbstractPageableController<CallDetailService, CallDetail, CallDetailDTO, Long> {

    @Autowired
    ThreadPoolUtil threadPoolUtil;

    @RequestMapping("start")
    @ResponseBody
    public String startCallTasks( ){
        try {
            ThreadPoolUtil.createPool();
            threadPoolUtil.startA();
            threadPoolUtil.startB();
            threadPoolUtil.startQueryExpress();
            return "智能电话系统已启动";
        }catch (Exception e){
            throw e;
        }
    }
    @RequestMapping("end")
    @ResponseBody
    public String endCallTasks( ){
        try {
            ThreadPoolUtil.closePool();
            ThreadPoolUtil.closeThreadAB();
            threadPoolUtil = null;
            return "智能电话系统已关闭";
        }catch (Exception e){
            throw  e;
        }

    }
}
