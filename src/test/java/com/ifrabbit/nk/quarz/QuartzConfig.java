//package com.ifrabbit.ksg.quarz;
//
//import org.quartz.Trigger;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
///**
// * @Auther: WangYan
// * @Date: 2018/6/26 17:40
// * @Description:
// */
//@Configuration
//public class QuartzConfig {
//    /**
//     * 调度器工厂Bean
//     */
//    @Bean(name = "schedulerFactory")
//    public static SchedulerFactoryBean schedulerFactory(
//            @Qualifier("myFirstExerciseJobTrigger") Trigger myFirstExerciseJobTrigger,
//            @Qualifier("callTasksJobTrigger") Trigger callTasksJobTrigger) {
//        SchedulerFactoryBean bean = new SchedulerFactoryBean();
//        // 覆盖已存在的任务
//        bean.setOverwriteExistingJobs(true);
//        // 延时启动定时任务，避免系统未完全启动却开始执行定时任务的情况
//        bean.setStartupDelay(15);
//        // 注册触发器
//        bean.setTriggers( myFirstExerciseJobTrigger,callTasksJobTrigger);
//        return bean;
//    }
//}
