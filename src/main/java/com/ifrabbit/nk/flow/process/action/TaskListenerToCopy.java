//package com.ifrabbit.nk.flow.process.action;
//
//import com.bstek.uflo.env.Context;
//import com.bstek.uflo.model.ProcessInstance;
//import com.bstek.uflo.model.task.Task;
//import com.bstek.uflo.process.listener.TaskListener;
//import com.bstek.uflo.process.node.TaskNode;
//import com.ifrabbit.nk.express.domain.Dealinfo;
//import com.ifrabbit.nk.express.domain.DealinfoDTO;
//import com.ifrabbit.nk.express.service.DealinfoService;
//import com.ifrabbit.nk.flow.process.utils.TaskUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @Auther: WangYan
// * @Date: 2018/7/4 18:10
// * @Description:
// */
//@Component("监听复制任务")
//public class TaskListenerToCopy implements TaskListener {
//
//    @Autowired
//    DealinfoService dealinfoService;
//
//    @Override
//    public boolean beforeTaskCreate(Context context, ProcessInstance processInstance, TaskNode taskNode) {
//        System.out.println("=========================beforeTaskCreateOfCopy====================");
//
//        return false;
//    }
//
//    @Override
//    public void onTaskCreate(Context context, Task task) {
//        System.out.println("=========================onTaskCreateOfCopy====================");
//        DealinfoDTO dealinfoDTO = new DealinfoDTO();
//        dealinfoDTO.setFuzzyExpressNum(task.getBusinessId());
//        dealinfoDTO.setFuzzyVarB("0");
//        dealinfoDTO.setFuzzyContent("等待处理");
//        List<Dealinfo> all = dealinfoService.findAll(dealinfoDTO);
//        int size = all.size();
//        for(int i=0; i<size; i++){
//            Dealinfo dealinfo = all.get(i);
//            //加入taskid,更新字段为3,3表示该任务下次不能再被生成任务
//            dealinfo.setAppdealIntparama(task.getId());
//            dealinfo.setAppdealVarparamb("3");
//            dealinfoService.updateIgnore(dealinfo);
//        }
//    }
//    @Override
//    public void onTaskComplete(Context context, Task task) {
//        System.out.println("=========================onTaskCompleteOfCopy====================");
//    }
//}
