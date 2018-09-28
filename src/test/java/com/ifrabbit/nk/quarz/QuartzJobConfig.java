//package com.ifrabbit.ksg.quarz;
//
//import jdk.nashorn.internal.codegen.CompilerConstants;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
//import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
//
///**
// * @Auther: WangYan
// * @Date: 2018/6/26 17:39
// * @Description:
// */
//@Configuration
//public class QuartzJobConfig {
//    /**
//     * 方法调用任务明细工厂Bean
//     */
//    @Bean(name = "myFirstExerciseJobBean")
//    public MethodInvokingJobDetailFactoryBean myFirstExerciseJobBean(CallTasks mySecondExerciseJob) {
//        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
//        jobDetail.setConcurrent(false); // 是否并发
//        jobDetail.setName("general-myFirstExerciseJob"); // 任务的名字
//        jobDetail.setGroup("general"); // 任务的分组
//        jobDetail.setTargetObject(mySecondExerciseJob); // 被执行的对象
//        jobDetail.setTargetMethod("myJobBusinessMethod"); // 被执行的方法
//        return jobDetail;
//    }
//
//    /**
//     * 表达式触发器工厂Bean
//     */
//    @Bean(name = "myFirstExerciseJobTrigger")
//    public CronTriggerFactoryBean myFirstExerciseJobTrigger(@Qualifier("myFirstExerciseJobBean") MethodInvokingJobDetailFactoryBean myFirstExerciseJobBean) {
//        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
//        tigger.setJobDetail(myFirstExerciseJobBean.getObject());
//        tigger.setCronExpression("0/10 * * * * ?"); // 什么是否触发，Spring Scheduler Cron表达式
//        tigger.setName("general-myFirstExerciseJobTrigger");
//        return tigger;
//    }
//
//    /**
//     * 方法调用任务明细工厂Bean
//     */
//    @Bean(name = "callTasksJobBean")
//    public MethodInvokingJobDetailFactoryBean callTasksJobBean(CallTasks mySecondExerciseJob) {
//        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
//        jobDetail.setConcurrent(false); // 是否并发
//        jobDetail.setName("general-mySecondExerciseJob"); // 任务的名字
//        jobDetail.setGroup("general"); // 任务的分组
//        jobDetail.setTargetObject(mySecondExerciseJob); // 被执行的对象
//        jobDetail.setTargetMethod("myJobBusinessMethod"); // 被执行的方法
//        return jobDetail;
//    }
//
//    /**
//     * 表达式触发器工厂Bean
//     */
//    @Bean(name = "callTasksJobTrigger")
//    public CronTriggerFactoryBean callTasksJobTrigger(@Qualifier("callTasksJobBean") MethodInvokingJobDetailFactoryBean mySecondExerciseJobDetailFactoryBean) {
//        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
//        tigger.setJobDetail(mySecondExerciseJobDetailFactoryBean.getObject());
//        tigger.setCronExpression("* 0/5 * * * ?"); // 什么是否触发，Spring Scheduler Cron表达式
//        tigger.setName("general-mySecondExerciseJobTrigger");
//        return tigger;
//    }
//}
