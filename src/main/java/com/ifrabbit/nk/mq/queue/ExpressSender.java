package com.ifrabbit.nk.mq.queue;

import com.ifrabbit.nk.constant.Constant;
import com.ifrabbit.nk.usercenter.domain.JobList;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void expressSender(JobList joblist){
            MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setHeader("x-delay", 1000);
                    return message;
                }
            };
            this.rabbitTemplate.convertAndSend("delayedExchange", Constant.EXPRESS_QUEUE_NAME, joblist, messagePostProcessor);
        }
}
