package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.MessagesService;
import com.ifrabbit.nk.express.service.ProblemService;
import com.ifrabbit.nk.express.service.TableInfoService;
import ir.nymph.date.DateTime;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("发送系统消息")
public class MessageSend implements NodeEventHandler {
    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    MessagesService messagesService;
    @Autowired
    DealinfoService dealinfoService;
    @Autowired
    private ProblemService problemService;
    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {
        ProblemDTO cond = new ProblemDTO();
        cond.setId(Long.valueOf(processInstance.getBusinessId()));
        Problem problem = problemRepository.findOne(cond);
        String problemparts_expressnumber = String.valueOf(problem.getProblemparts_expressnumber());
//        problem.setProblemparts_type(4);//设置为4，走签收未收到
        String type = "未定义";//问题件类型
        Integer problemPartsType = problem.getProblemparts_type();
        String InfoResult = "未定义";
        switch (problemPartsType){
            case 1:
                type = "签收未收到";
                InfoResult = problem.getProblemparts_finish();
                break;
            case 2:
                type = "破损";
                break;
            case 3:
                type = "改地址";
                break;
            case 4:
                type = "退回";
                break;
            case 5:
                type = "催单";
                break;
        }
        String content = "运单号为(" + problemparts_expressnumber + ")的快递，问题类型是: " + type + ",处理结果:" + InfoResult;
        insertMessages(problem.getProblemparts_staffname(), content,Long.valueOf(problemparts_expressnumber),problemPartsType);
    }

    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {
        String nodeName = node.getName();
        if(StringUtils.isNotBlank(nodeName) && nodeName.contains("结束")){
            ProblemDTO cond = new ProblemDTO();
            cond.setId(Long.valueOf(processInstance.getBusinessId()));
            cond.setProblemparts_tasktype(1);
            problemRepository.updateIgnoreNull(cond);
        }
    }

    private void insertMessages(String staffname, String content,long expressNumber,int type) {
        DateTime dateTime = new DateTime();
        String time = dateTime.toString();
        MessagesDTO messagesDTO = new MessagesDTO();
        messagesDTO.setMessageContent(content);
        messagesDTO.setType(1);
        messagesDTO.setUser(staffname);
        messagesDTO.setCreateDate(time);
        messagesDTO.setExpressNumber(expressNumber);
        messagesDTO.setProblemPartsType(type);
        messagesService.insert(messagesDTO);
    }
}
