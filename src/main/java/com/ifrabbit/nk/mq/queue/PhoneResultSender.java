package com.ifrabbit.nk.mq.queue;

import com.ifrabbit.nk.constant.Constant;
import com.ifrabbit.nk.express.domain.CallDetail;
import com.ifrabbit.nk.usercenter.domain.JobList;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneResultSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void phoneResultSender(CallDetail callDetail,Long time){
            MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setHeader("x-delay", time);
                    return message;
                }
            };
            this.rabbitTemplate.convertAndSend("delayedExchange", Constant.PHONERESULT_QUEUE_NAME, callDetail, messagePostProcessor);
        }
}
