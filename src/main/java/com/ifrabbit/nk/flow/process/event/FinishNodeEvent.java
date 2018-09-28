package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import com.ifrabbit.nk.express.domain.TableInfo;
import com.ifrabbit.nk.express.domain.TableInfoDTO;
import com.ifrabbit.nk.express.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/26
 * Time:14:07
 */
@Component("关闭前更新tableInfo表状态")
public class FinishNodeEvent implements NodeEventHandler {
    @Autowired
    TableInfoService tableInfoService;

    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {
        long processInstanceId = processInstance.getId();
        TableInfoDTO dto = new TableInfoDTO();
        dto.setTableinfo_tabid(processInstanceId);//根据processInstanceId找到对应的表单
        TableInfo tableInfo = tableInfoService.findOne(dto);
        tableInfo.setTableinfo_dealstate(0);//设置状态为0，表示该表单结束
        tableInfoService.updateIgnore(tableInfo);
    }

    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {

    }
}
