package com.ifrabbit.nk.flow.process.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: WangYan
 * @Date: 2018/8/23 09:46
 * @Description:
 */
@Component
public class ThreadPoolUtil {
    public static ForkJoinPool forkJoinPool;
    final static long waitTime = 5 * 1000;
    private  static FindDataThread threadA;
    private  static GetResultThread threadB;
    private  static FindExpressThread threadExpress;

    @Autowired
    private AutowireCapableBeanFactory factory;

    public void startQueryExpress(){
        if (threadExpress == null){
            FindExpressThread findExpressThread = new FindExpressThread();
            factory.autowireBean(findExpressThread);
            threadExpress = findExpressThread;
            threadExpress.setName("创建查询物流");
            threadExpress.start();
        }
    }


    @PostConstruct
    public void init() {
        forkJoinPool = ForkJoinPool.commonPool();
    }
    public static void createPool() {
        if (forkJoinPool == null){
            forkJoinPool = ForkJoinPool.commonPool(); }
 }
   public void startA(){
       if (threadA == null){
           FindDataThread thread1 = new FindDataThread();
           factory.autowireBean(thread1);
           threadA = thread1;
           threadA.setName("创建通话任务");
           threadA.start();

       }
   }
   public  void startB(){
       if (threadB == null){
           GetResultThread thread2 = new GetResultThread();
           factory.autowireBean(thread2);
           threadB = thread2;
           threadB.setName("获取通话结果信息");
           threadB.start();
       }
   }



   public static String closePool(){
       if (forkJoinPool == null){
            return  "智能电话为关闭状态!请勿重复提交";
        }else{
            try {
                // 向学生传达“问题解答完毕后请举手示意！”
                forkJoinPool.shutdown();
                // 向学生传达“XX分之内解答不完的问题全部带回去作为课后作业！”后老师等待学生答题
                // (所有的任务都结束的时候，返回TRUE)
                if(!forkJoinPool.awaitTermination(waitTime, TimeUnit.MILLISECONDS)){
                    // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                    forkJoinPool.shutdownNow();
                }
            } catch (InterruptedException e) {
                // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
                System.out.println("awaitTermination interrupted: " + e);
                forkJoinPool.shutdownNow();
            }
           return  "智能电话已关闭!";
        }
   }
   public static void closeThreadAB(){
        if (threadA != null){threadA.exit = true;}
        if (threadB != null){threadB.exit = true;}
       if (threadExpress != null){threadExpress.exit = true;}
   }
}
