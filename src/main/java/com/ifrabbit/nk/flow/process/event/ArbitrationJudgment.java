package com.ifrabbit.nk.flow.process.event;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.NodeEventHandler;
import com.bstek.uflo.process.node.Node;
import org.springframework.stereotype.Component;

/**
 * @Auther: WangYan
 * @Date: 2018/4/18 15:54
 * @Description:人工判断是否需要仲裁,以及仲裁結果
 */
@Component("人工判断是否需要仲裁")
public class ArbitrationJudgment implements NodeEventHandler {
    @Override
    public void enter(Node node, ProcessInstance processInstance, Context context) {
        System.out.println("客服美眉进来啦!");
    }

    @Override
    public void leave(Node node, ProcessInstance processInstance, Context context) {
        System.out.println("客服美眉离开啦!");
    }
}
