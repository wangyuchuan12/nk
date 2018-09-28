//package com.ifrabbit.nk.test.express;
//
//import com.ifrabbit.nk.express.domain.CallDetail;
//import com.ifrabbit.nk.express.service.CallDetailService;
//import com.ifrabbit.nk.flow.process.utils.TaskUtil;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Random;
//
//@RestController
//@RequestMapping("test")
//public class CallDetailTest{
//    @Autowired(required = false)
//    private CallDetailService callDetailService;
//
//    private static Logger logger = LoggerFactory.getLogger(CallDetailTest.class);
//
//    @RequestMapping("callDetail")
//    public void testCallDetail(final SimulationTest simulationTest){
//        String[] names = simulationTest.getNames();
//
//        int count = Integer.parseInt(names[0]);    //插入个数
//        int circulate = Integer.parseInt(names[1]);    //循环次数
//        int sleepTime = Integer.parseInt(names[2]) * 1000;   //间隔时间
//        String[] phoneNumbers = names[3].split(",");   //获取电话号码
//
//        CallDetail callDetailDTO = new CallDetail();
//        String x = ",";//定义一个单号的语音播报间隔
//        Integer answerId = 1;
//        Integer contentId = 1;
////        String ivrId = "10000204";
//        String insideItem = "您好，中通快递,请问您购买的衣服有没有收到";
//        callDetailDTO.setCalldetail_answerid(answerId);
//        callDetailDTO.setCalldetail_contentid(contentId);
//        callDetailDTO.setCalldetail_state(0);
//        callDetailDTO.setCalldetail_inside_item(insideItem);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for (int t = 0; t < circulate; t++){
//                        long startTime = System.currentTimeMillis();
//                        logger.info("第" + (t+1) + "次," + "开始插入数据时间:" + startTime);
//                        for(int i = 0; i < count; i++){
//                            String expressNumber = getExpressNumber();
//                            String voiceNumber = expressNumber.substring(0,4)+x+expressNumber.substring(4,8)+x+expressNumber.substring(8,12)+x;
//                            Random random = new Random();//定义random，产生随机数
//                            int temp;
//                            String phoneNumber = "";
//                            if(null != phoneNumbers && phoneNumbers.length > 0){
//                                temp = random.nextInt(phoneNumbers.length);  //随机电话号码
//                                phoneNumber = phoneNumbers[temp];
//                            }
//                            String nPhoneNumber = TaskUtil.FilterPhoneNumber(phoneNumber);
//                            callDetailDTO.setCalldetail_tableid((long)(Math.random()*100000 + 100000));
//                            callDetailDTO.setCalldetail_calltime((int)(Math.random()*10));
//                            callDetailDTO.setCalldetail_calldate((int)(Math.random()*10));
//                            callDetailDTO.setCalldetail_companyid((long)(Math.random()*100000));
//                            callDetailDTO.setCalldetail_calltype((i % 2) + 1);
//                            callDetailDTO.setCalldetail_phonenumber(phoneNumber);
//                            callDetailDTO.setCalldetail_callphonenumber(nPhoneNumber);
//                            callDetailDTO.setCalldetail_expressnumber(expressNumber);
//                            callDetailDTO.setCalldetail_callexpressnumber(voiceNumber);
//                            callDetailDTO.setCalldetail_name(getName());
//                            callDetailDTO.setCalldetail_dealid((long)(Math.random()*100000));
//                            callDetailDTO.setCalldetail_ivrid(getIvrId());
//                            callDetailService.insert(callDetailDTO);
//                        }
//                        long endTime = System.currentTimeMillis();
//                        logger.info("第" + (t+1) + "次," +"插入数据完毕时间:" + endTime);
//                        long time = endTime - startTime;
//                        logger.info("第" + (t+1) + "次," + "插入数据耗时:" + time + "ms");
//                        Thread.sleep(sleepTime);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    //随机产生运单号
//    private static String getExpressNumber() {
//
//        String expressNumber ="";
//
//        for (int i = 0; i < 12; i++) {//产生12个随机号码
//            Random random = new Random();//定义random，产生随机数
//            expressNumber += (random.nextInt(9) + 1);  //nextInt返回值介于[0,n)的区间
//        }
//
//        return expressNumber;
//    }
//
//    //随机产生名称
//    private static String getPhoneNumber() {
//
//        String[] phoneNumber ={""};
//        int temp;
//        Random random = new Random();//定义random，产生随机数
//        temp = random.nextInt(phoneNumber.length);
//        return phoneNumber[temp];
//    }
//
//    private static String getName(){
//
//        String[] string = {"壹","贰","叁","肆","伍","陆","柒","捌","玖","拾"};
//        String name = "网点";
//        int temp;
//        Random random = new Random();
//        temp = random.nextInt(string.length);
//        for(int i = 0; i <= temp; i++){
//            name = string[i] + name;
//        }
//        return name;
//    }
//
//    private static String getIvrId(){
//
//        String[] ivrIds = {"10000204","10000187"};
//        int temp;
//        Random random = new Random();//定义random，产生随机数
//        temp = random.nextInt(ivrIds.length);
//        return ivrIds[temp];
//    }
//}
//
//
//
