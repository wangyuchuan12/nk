package com.ifrabbit.nk.mq.producer;

import com.ifrabbit.nk.constant.Constant;
import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.flow.process.utils.TaskUtil;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/7/24
 * Time:19:18
 */
@Component
public class CallBackProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(CallDetail callDetail, long time) {
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);

        System.out.println("DelayedSender 发送时间: " + LocalDateTime.now() + " callDetail内容：" + callDetail);
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay", time);
                return message;
            }
        };
        this.rabbitTemplate.convertAndSend("delayedExchange", Constant.EXPRESS_QUEUE_NAME, callDetail, messagePostProcessor);


    }

    /**
     * 发送后的回调函数
     *
     * @param correlationData
     * @param b
     * @param s
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("回调函数:" + "b=" + b);
    }

    /**
     * 消息发送失败的回调函数(未测试)
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("发送消息失败");
    }
}
