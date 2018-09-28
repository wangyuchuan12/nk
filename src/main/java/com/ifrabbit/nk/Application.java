package com.ifrabbit.nk;

import com.ifrabbit.nk.constant.Constant;
import ir.nymph.lang.Snowflake;
import org.hibernate.SessionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@EnableCaching @SpringBootApplication @EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean Snowflake idWorker() {
		return new Snowflake(0, 0);
	}


	/**
	 * 自定义的交换机类型
	 * @return
	 */
	@Bean
	CustomExchange delayedExchange() {
		Map<String,Object> args = new HashMap<>();
		args.put("x-delayed-type", "direct");
		return new CustomExchange("delayedExchange","x-delayed-message",true,false,args);
	}

	/**
	 * 创建一个队列
	 * @return
	 */
	@Bean
	public Queue ExpressQueue() {
		return new Queue(Constant.EXPRESS_QUEUE_NAME,true);
	}


	@Bean
	public Queue PhoneQueue() {
		return new Queue(Constant.PHONE_QUEUE_NAME,true);
	}

	@Bean
	public Queue PhoneReusltQueue() {
		return new Queue(Constant.PHONERESULT_QUEUE_NAME,true);
	}
	/**
	 * 绑定队列到自定义交换机
	 * @return
	 */
	@Bean
	public Binding bindingNotify() {
		return BindingBuilder.bind(ExpressQueue()).to(delayedExchange()).with(Constant.EXPRESS_QUEUE_NAME).noargs();
	}

	@Bean
	public Binding bindingNotify1() {
		return BindingBuilder.bind(PhoneQueue()).to(delayedExchange()).with(Constant.PHONE_QUEUE_NAME).noargs();
	}

	@Bean
	public Binding bindingNotify2() {
		return BindingBuilder.bind(PhoneReusltQueue()).to(delayedExchange()).with(Constant.PHONERESULT_QUEUE_NAME).noargs();
	}


	@Bean
	//@ConditionalOnMissingBean(PlatformTransactionManager.class)
	public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}
}
