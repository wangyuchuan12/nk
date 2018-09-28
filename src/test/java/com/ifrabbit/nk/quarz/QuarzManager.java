//package com.ifrabbit.ksg.quarz;
//
//import ir.nymph.date.DateTime;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.quartz.SchedulerException;
//import org.quartz.Trigger;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @Auther: WangYan
// * @Date: 2018/6/27 10:36
// * @Description:
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class QuarzManager {
//    @Test
//    public  void test1(Trigger callTasksJobTrigger) throws SchedulerException {
//        SchedulerFactoryBean schedulerFactoryBean = QuartzConfig.schedulerFactory(null, callTasksJobTrigger);
//        schedulerFactoryBean.getScheduler().start();
//        //        JobConfig x = new JobConfig();
////        x.setFullEntity(CallTasks.class.getName());
////        x.setGroupName("CR");
////        x.setCronTime("* 0/5 * * * *");
////        x.setCreateAt(new DateTime());
////        x.setStatus(1);
////        SchedulerUtil.createScheduler(x, applicationContext);
////        System.out.println("测试等待"+new DateTime());
////
////        try {
////            taskService.wait(6000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////
////        System.out.println("now="+new DateTime());
////        System.out.println("!111");
//    }
//}
