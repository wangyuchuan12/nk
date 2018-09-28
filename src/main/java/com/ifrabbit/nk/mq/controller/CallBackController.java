//package com.ifrabbit.ksg.mq.controller;
//
//import com.ifrabbit.nk.express.domain.Dealinfo;
//import com.ifrabbit.nk.express.domain.DealinfoDTO;
//import com.ifrabbit.nk.express.service.DealinfoService;
//import com.ifrabbit.nk.mq.domain.Contain;
//import com.ifrabbit.nk.mq.producer.CallBackProducer;
//import com.ifrabbit.nk.usercenter.service.AppProblempartsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created with IDEA
// * author:SunJiaJian
// * Date:2018/7/24
// * Time:19:20
// */
//
///**
// * 实现这个CommandLineRunner,就可以在boot启动后执行该类
// */
//
//@Component
//@Order(1)
//public class CallBackController implements CommandLineRunner {
//    @Autowired
//    private CallBackProducer callBackProducer;
//    @Autowired
//    private AppProblempartsService appProblempartsService;
//    @Autowired
//    private DealinfoService dealinfoService;
//
//
//    @Override
//    public void run(String... strings) throws Exception {
//                while(true){
//            System.out.println("先执行队列===============================================》");
//            Map<String, Object> map;
//            map = new HashMap<>();
//            map.put("name", "zhinengkefu");
//            try {
//                InetAddress address = InetAddress.getLocalHost();
//                String hostAddress = address.getHostAddress();
//                map.put("ip", hostAddress);
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
//            List<Map<String, Object>> taskList = this.appProblempartsService.finduflotaskByParams(map);
//
//            int size = taskList.size();
//
//            for (int i = 0; i < size; i++) {
//                Contain contain = new Contain();
//                Map<String, Object> maps = taskList.get(i);
//                Long taskId = (Long) maps.get("ID_");
//                contain.setTaskId(taskId);
//                contain.setDealType((Integer) maps.get("appdeal_dealtype"));
//                contain.setBusinessId(Long.valueOf(String.valueOf(maps.get("BUSINESS_ID_"))));
//                contain.setDealId(Long.valueOf(String.valueOf(maps.get("appdeal_id"))));
//                contain.setExpressNumber(String.valueOf(maps.get("problemparts_expressnumber")));
//                contain.setInsideItem((String) maps.get("problemparts_insideitem"));
//                contain.setNodeCallTimes(Integer.parseInt(String.valueOf(maps.get("nodeCallTimes"))));
//                contain.setNodeName((String) maps.get("NODE_NAME_"));
//                contain.setProblempartsReceivename((String) maps.get("problemparts_receivename"));
//                contain.setProblempartsReceivephone((String) maps.get("problemparts_receivephone"));
//                contain.setProblempartsSendname((String) maps.get("problemparts_sendname"));
//                contain.setProblempartsSendphone((String) maps.get("problemparts_sendphone"));
//                contain.setProblemType(Integer.parseInt(String.valueOf(maps.get("problemparts_type"))));
//                contain.setProcessInstanceId(String.valueOf(maps.get("PROCESS_INSTANCE_ID_")));
//                contain.setAssignee(String.valueOf(maps.get("ASSIGNEE_")));
//                Integer problemparts_callcompanycount = (Integer) maps.get("problemparts_callcompanycount");
//                Integer problemparts_uncollecteddays = (Integer) maps.get("problemparts_uncollecteddays");
//                contain.setProblempartsCallCompanyCount(problemparts_callcompanycount);
//                contain.setUncollectedDays(problemparts_uncollecteddays);
//
//                DealinfoDTO dealinfoDTO = new DealinfoDTO();
//                dealinfoDTO.setAppdealRecorderid(taskId);
//                Dealinfo dealinfo = dealinfoService.findOne(dealinfoDTO);
//                dealinfo.setAppdealDealstate(3);
//                dealinfoService.updateIgnore(dealinfo);
//
//                callBackProducer.send(contain,6000);
//            }
//        }
//    }
//}
