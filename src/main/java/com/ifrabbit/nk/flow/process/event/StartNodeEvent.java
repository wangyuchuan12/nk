package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import com.bstek.uflo.service.ProcessService;
import com.ifrabbit.nk.express.domain.TableInfo;
import com.ifrabbit.nk.express.domain.TableInfoDTO;
import com.ifrabbit.nk.express.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/27
 * Time:16:08
 */
@Component("开始前插入tableInfo表")
public class StartNodeEvent implements NodeEventHandler {
    @Autowired
    TableInfoService tableInfoService;
    @Autowired
    ProcessService processService;

    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {
        long processInstanceId = processInstance.getId();
        //从流程中获取父流程的instanceID
        ProcessInstance processInstanceById = processService.getProcessInstanceById(processInstanceId);

        long parentId = processInstanceById.getParentId();

        TableInfoDTO dto = new TableInfoDTO();
        dto.setTableinfo_tabid(parentId);
        TableInfo infoServiceOne = tableInfoService.findOne(dto);
        Long upTabID = 0l;
        if(infoServiceOne != null){
            upTabID = infoServiceOne.getId();
        }

        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableinfo_tabid(processInstanceId);
        tableInfo.setTableinfo_uptabid(upTabID);
        tableInfoService.insert(tableInfo);
    }

    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {

    }
}
