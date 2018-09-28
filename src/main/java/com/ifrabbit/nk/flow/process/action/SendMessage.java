package com.ifrabbit.nk.flow.process.action;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.ActionHandler;
import com.bstek.uflo.process.handler.DecisionHandler;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.MessagesService;
import com.ifrabbit.nk.express.service.TableInfoService;
import com.ifrabbit.nk.flow.process.utils.TaskUtil;
import com.ifrabbit.nk.message.domain.SmsMiddleTable;
import com.ifrabbit.nk.message.repository.SmsMiddleTableRepository;
import com.ifrabbit.nk.message.service.MessageService;
import com.ifrabbit.nk.usercenter.domain.UfloTask;
import com.ifrabbit.nk.usercenter.repository.UfloTaskRepository;
import ir.nymph.date.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/7/5
 * Time:14:24
 */
@Component("发送短信")
public class SendMessage implements ActionHandler{

    private static Logger logger = LoggerFactory.getLogger(SendMessage.class);

    @Autowired
    MessageService messageService;
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private DealinfoService dealinfoService;
    @Autowired
    MessagesService messagesService;
    @Override
    public void handle(ProcessInstance processInstance, Context context) {
        ProblemDTO cond = new ProblemDTO();
        String business_id = processInstance.getBusinessId();
        cond.setId(Long.valueOf(business_id));
        Problem problem = problemRepository.findOne(cond);
        String type = "未定义";//问题件类型
        Integer problemPartsType = problem.getProblemparts_type();
        switch (problemPartsType){
            case 1:
                type = "签收未收到";
                break;
            case 2:
                type = "破损";
                break;
            case 3:
                type = "改地址";
                break;
            case 4:
                type = "退回";
                break;
            case 5:
                type = "催单";
                break;
        }
        String receivename = problem.getProblemparts_receivename();//收件人名字
        String receivephone = problem.getProblemparts_receivephone();//收件人电话

        Long expressnumber = problem.getProblemparts_expressnumber();//运单号
        String sendphone = problem.getProblemparts_sendphone();//发件人电话
        String sendname = problem.getProblemparts_sendname();//发件人姓名
        String insideItem = problem.getProblemparts_insideitem();//内物
        String staffname = problem.getProblemparts_staffname();//发起人


        String[] data = new String[8];//[0发件人姓名][1运单号][2问题类型][3收件人姓名][4内物]
//        data[0] = sendname ;
//        data[1] = String.valueOf(expressnumber);
//        data[2] = type;
//        data[3] = receivename;
//        data[4] = insideItem;

        String messageNumber = "";
        String currentNode = processInstance.getCurrentNode();
        String modal = "";
        String messageType="";
        if(currentNode.contains("通知")){
            if(currentNode.contains("向收件人发送通知短信")){
                logger.info("++++++++++++++++++++++++++++++++++向收件人发送超出时效的通知短信+++++++++++++++++++++++++++++++++");
                modal = "262800";
                messageNumber = receivephone;
                messageType="向收件人发送超出时效的通知短信";
                data[0] = sendname ;
                data[1] = String.valueOf(expressnumber);
                data[2] = type;
                data[3] = receivename;
                data[4] = insideItem;
            }else if(currentNode.contains("告诉发件人收件人已拨通")){
                logger.info("++++++++++++++++++++++++++++++++++收件人已拨通的通知短信+++++++++++++++++++++++++++++++++");
                messageNumber = sendphone;
                modal = "262764";//通知发件人，收件人拨通
                messageType="提示发件人,收件人已拨通的通知短信";
                data[0] = sendname ;
                data[1] = String.valueOf(expressnumber);
                data[2] = type;
                data[3] = receivename;
                data[4] = insideItem;

                String content = "运单号为(" + expressnumber + ")的快递，问题类型是: " + type + "，我们已拨通收件人: " + receivename + "的电话(" + receivephone + ")";
                insertMessages(staffname, content);

            }else if(currentNode.contains("告知收件人电话核实")){
                logger.info("++++++++++++++++++++++++++++++++++告知收件人电话核实+++++++++++++++++++++++++++++++++");
                messageNumber = sendphone;
                modal = "263911";
                messageType="告知收件人会有智能语音客服来电话核实情况";
                data[0] = receivename ;
                data[1] = insideItem;
                data[2] = String.valueOf(expressnumber);
            }else if(currentNode.contains("短信通知发件人")){
                logger.info("++++++++++++++++++++++++++++++++++告知发件人暂无揽收记录+++++++++++++++++++++++++++++++++");
                messageNumber = sendphone;
                modal = "299872";//待定
                messageType="告知发件人暂无揽收记录";
                data[0] = type ;
                data[1] = String.valueOf(expressnumber);
            }else{
                logger.info("++++++++++++++++++++++++++++++++++告知发件人收件人无法打通的通知短信+++++++++++++++++++++++++++++++++");
                modal = "260776";//告知发件人收件人无法打通
                messageNumber = sendphone;
                messageType="告知发件人收件人无法打通的通知短信";
                data[0] = sendname ;
                data[1] = String.valueOf(expressnumber);
                data[2] = type;
                data[3] = receivename;
                data[4] = insideItem;

                String content = "运单号为(" + expressnumber + ")的快递，问题类型是: " + type + "，我们无法拨通收件人: " + receivename + "的电话(" + receivephone + ")";
                insertMessages(staffname, content);
            }
        }else if(currentNode.contains("免责短信")){
            logger.info("++++++++++++++++++++++++++++++++++发送免责短信+++++++++++++++++++++++++++++++++");
            messageNumber = sendphone;
            modal = "260775";//发送免责声明
            messageType="免责短信";
            data[0] = sendname ;
            data[1] = String.valueOf(expressnumber);
            data[2] = type;
            data[3] = receivename;
            data[4] = insideItem;
        }else if(currentNode.contains("结束短信")){
            logger.info("++++++++++++++++++++++++++++++++++发送结束短信+++++++++++++++++++++++++++++++++");
            if(currentNode.contains("收件人")){
                messageNumber = receivephone;
            }else{
                messageNumber = sendphone;
            }
            modal = "256800";//结束语
            messageType="结束短信";
        }else if(currentNode.contains("提示错误输入")){
            logger.info("++++++++++++++++++++++++++++++++++发送输入有误短信+++++++++++++++++++++++++++++++++");
            if(currentNode.contains("收件人")){
                messageNumber = receivephone;
            }else{
                messageNumber = sendphone;
            }
            modal = "259193";//输入有误
            messageType="提示错误输入的短信";
        }
            sendMessage(messageNumber,data,modal);

        // TODO: 2018/7/23 动作短信是否需要记录日志
//        Long businessId = Long.valueOf(business_id);
//        TableInfoDTO ti = new TableInfoDTO();
//        ti.setFuzzyBusinessId(businessId);
//        TableInfo tableInfo = tableInfoService.findOne(ti);
//        Long dealid = tableInfo.getTableinfo_dealid();
//
//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = simpleDateFormat.format(date);
//        insertDealInfo(businessId,dealid,1,new String(time+"向客户电话为："+messageNumber+",发送了一条"+messageType),7,problemPartsType);
        }

    private void insertMessages(String staffname, String content) {
        DateTime dateTime = new DateTime();
        String time = dateTime.toString();
        MessagesDTO messagesDTO = new MessagesDTO();
        messagesDTO.setMessageContent(content);
        messagesDTO.setType(1);
        messagesDTO.setUser(staffname);
        messagesDTO.setCreateDate(time);
        messagesService.insert(messagesDTO);
    }

    public void insertDealInfo (Long business_id_,Long appDealId, Integer state,String option,Integer Result,Integer problemPartsType) {
        Dealinfo dealinfo =  new Dealinfo();
        dealinfo.setAppdealTabid(business_id_);
//        dealinfo.setAppdealDealtype(1);
        dealinfo.setAppdealProblemtype(problemPartsType);
        dealinfo.setAppdealDealername("zhinengkefu");
        dealinfo.setId(appDealId);
        dealinfo.setAppdealDealstate(state);// 1:表示该条记录已被执行过
        dealinfo.setAppdealContent(option);//详细的操作过程
        dealinfo.setAppdealResult(Result);//1:表示打通 2:表示没打通 7:短信发送成功
        dealinfo.setAppdealVarparamb("0");
        String Resulttext ="";
        if(Result == 7){
            Resulttext = "已发送短信";
        }else{
            Resulttext = "发送错误";
        }
        DateTime dateTime = new DateTime();
        dealinfo.setAppdealDealcreatedate(dateTime.toTimestamp());
        dealinfo.setAppdealResulttext(Resulttext);//Result的中文对照
        dealinfoService.insert(dealinfo);
    }



    private void sendMessage(String messageNumber, String[] data,String modal) {
        boolean flag = true;
        while (flag) {
            String s = messageService.sendMessage(messageNumber, modal, data);
            if (s.equals("0")) {
                //发送请求成功
                logger.info("+++++++++++++++++短信请求已成功发送给容联公司+++++++++++++++++");
                flag = false;
            }else{
                //发错失败，过1分钟重新发送
                try{
                    Thread.sleep(1000l);
                }catch (Exception e){
                    e.printStackTrace();
                }
                sendMessage(messageNumber,data,modal);
            }
        }
    }

}
