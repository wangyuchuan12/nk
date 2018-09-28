package com.ifrabbit.nk.mq.producer;

import lombok.Data;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/8/20
 * Time:16:47
 */
@Component
public class DelayedSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String msg, long time) {
        System.out.println("DelayedSender 发送时间: " + LocalDateTime.now() + " msg内容：" + msg);
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setHeader("x-delay", time);
                return message;
            }
        };
        this.rabbitTemplate.convertAndSend("delayedExchange", "delayedQueue", msg, messagePostProcessor);
    }

}
