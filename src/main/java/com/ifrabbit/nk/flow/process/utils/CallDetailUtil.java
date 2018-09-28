package com.ifrabbit.nk.flow.process.utils;

import com.bstek.uflo.model.ProcessInstance;
import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.express.domain.TableInfo;
import com.ifrabbit.nk.express.domain.TableInfoDTO;
import com.ifrabbit.nk.express.service.CallDetailService;
import com.ifrabbit.nk.express.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: WangYan
 * @Date: 2018/8/25 19:36
 * @Description:
 */
@Service
public class CallDetailUtil {
    @Autowired
    private CallDetailService callDetailService;
    @Autowired
    private TableInfoService tableInfoService;

    public CallDetail getCallDetail(ProcessInstance processInstance){
        TableInfoDTO tableInfoDTO = new TableInfoDTO();
        tableInfoDTO.setTableinfo_bussinessid(Long.valueOf(processInstance.getBusinessId()));
        TableInfo one = tableInfoService.findOne(tableInfoDTO);
        Long taskId = one.getTableinfo_dealerid();
        CallDetail callDetail = new CallDetail();
//        callDetail.setCalldetail_taskid(taskId);
        callDetail = callDetailService.findOne(callDetail);
        return callDetail;
    }
}
