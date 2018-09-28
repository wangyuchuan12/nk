//package com.ifrabbit.nk.mq.config;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
///**
// * Created with IDEA
// * author:SunJiaJian
// * Date:2018/7/24
// * Time:19:17
// */
//@Configuration
//public class CallbackConfig {
//    final static String CALLBACK = "back";
//
//    @Bean
//    public Queue callBackQueue() {
//        return new Queue(CALLBACK);
//    }
//
//
//
//
//    @Autowired
//    private ConnectionFactory connectionFactory;
//
//    @Bean
//    /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public RabbitTemplate rabbitTemplatenew() {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        return template;
//    }
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setAddresses("localhost"+":"+"5672");//本地安装mq服务则用localhost
//        connectionFactory.setUsername("guest");
//        connectionFactory.setPassword("guest");
//        connectionFactory.setVirtualHost("/");
//        /** 如果要进行消息回调，则这里必须要设置为true */
//        connectionFactory.setPublisherConfirms(true);
//        return connectionFactory;
//    }
//
//}
