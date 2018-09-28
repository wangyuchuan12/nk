//package com.ifrabbit.nk.flow.process.utils;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ifrabbit.nk.express.domain.CallDetail;
//import com.ifrabbit.nk.express.service.CallDetailService;
//import com.ifrabbit.nk.express.service.impl.CallDetailServiceImpl;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.concurrent.RecursiveAction;
///**
// * @Auther: WangYan
// * @Date: 2018/8/23 09:37
// * @Description:
// */
//public class CallTask extends RecursiveAction {
//    private CallDetailService callDetailService;
//    private static final int THRESHOLD = 100; //最多只能打印50个数
//    private int size;
//    private String IVRid;
//    private List<CallDetail> list;
//
//    public CallTask(String id, List<CallDetail> list) {
//        super();
//        this.size = list.size();
//        this.list = list;
//        this.IVRid = id;
//        this.callDetailService = (CallDetailService) SpringBeanUtil.getBeanByName("CallDetailService");
//    }
//
//
//    @Override
//    protected void compute() {
//        if(size < THRESHOLD){
//            JSONArray array = new JSONArray();
//            for (CallDetail callDetail : list) {
//                JSONObject json = new JSONObject();
//                json.put("U_phone", callDetail.getCalldetail_callphonenumber());
//                json.put("U_name", callDetail.getCalldetail_name());
//                json.put("U_number", callDetail.getCalldetail_expressnumber());
//                json.put("U_type", callDetail.getCalldetail_inside_item());
//                json.put("U_vendor", callDetail.getId());
//                callDetail.setCalldetail_calltype(1);
//                array.add(json);
//            }
//            String result = CallUtil.duyanKeys(IVRid, array);
//            if ("1".equals(result)){
//                for (CallDetail callDetail : list) {
//                    callDetailService.update(callDetail);
//                }
//            }
//        }else {
//            int middle =size/2;
//            List<CallDetail> l = list.subList(0, middle);
//            List<CallDetail> r = list.subList(middle,size);
//            CallTask left = new CallTask(IVRid,l);
//            CallTask right = new CallTask(  IVRid,r);
//            //并行执行两个“小任务”
//            left.fork();
//            right.fork();
//        }
//    }
//}
