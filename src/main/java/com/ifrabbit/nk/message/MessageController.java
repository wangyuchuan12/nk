package com.ifrabbit.nk.message;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.service.TaskOpinion;
import com.ifrabbit.nk.express.domain.*;
import com.ifrabbit.nk.express.service.DealinfoService;
import com.ifrabbit.nk.express.service.TableInfoService;
import com.ifrabbit.nk.flow.repository.VariableRepository;
import com.bstek.uflo.service.TaskService;
import com.ifrabbit.nk.message.domain.ParamVo;
import com.ifrabbit.nk.message.domain.SmsMiddleTable;
import com.ifrabbit.nk.message.repository.SmsMiddleTableRepository;
import com.ifrabbit.nk.message.service.MessageService;
import com.ifrabbit.nk.usercenter.repository.UfloTaskRepository;
import com.ifrabbit.nk.usercenter.service.UfloTaskService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.annotations.Source;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/6/19
 * Time:8:49
 */
@RestController
@RequestMapping("message")
public class MessageController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private MessageService messageService;

    @Autowired
    private UfloTaskService ufloTaskService;

    @Qualifier("uflo.taskService")
    @Autowired
    private TaskService decorator;

    @Autowired
    private SmsMiddleTableRepository smsMiddleTableRepository;

    @Autowired
    private VariableRepository variableRepository;
    @Autowired
    private UfloTaskRepository taskRepository;
    @Autowired
    private TableInfoService tableInfoService;
    @Autowired
    private DealinfoService dealinfoService;

    private static TaskOpinion taskOpinion;
    @RequestMapping(value = "data", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void getData(Model model, HttpServletRequest request, HttpServletResponse response, ProcessInstance processInstance, Context context) {
        logger.debug("接收消息");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String data = null;
            StringBuffer input = new StringBuffer();
            while ((data = br.readLine()) != null) {
                input.append(data);
            }
            br.close();
            String body = input.toString();
            logger.info("BODY: +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            logger.info(body);
            logger.info("BODY: +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            if (StringUtils.isNotBlank(body)) {
                ParamVo param=this.analyticParameter(body);
                logger.info("+++++++++++++++++++++++++++++++++++++++++++++"+param.toString()+"++++++++++++++++++++++++++++++++++++++++++++");
                //根据param处理系统业务
                String content = param.getContent();
//               String status = param.getStatus();//获取状态值，0表示收到
                String phone = param.getFromNum();//获取客户的电话
                String recvTime = param.getRecvTime();//客户接收短信的时间
                Long smsType = Long.valueOf(param.getSmsType());

                //创建变量实体来存放用户输入的值
                TempVariable tempVariable1 = new TempVariable();
                TempVariable tempVariable2 = new TempVariable();
                TempVariable tempVariable3 = new TempVariable();

                //根据电话号来获取中间表的流程taskId
                List<SmsMiddleTable> smsMiddleTables = smsMiddleTableRepository.findByPhoneNumber(phone);
                int sm = smsMiddleTables.size()-1;
                SmsMiddleTable smsMiddleTable = smsMiddleTables.get(sm);
                Long taskId = smsMiddleTable.getTask_id();
                String business_id = smsMiddleTable.getBusiness_id();

                Long businessId = Long.valueOf(business_id);
                TableInfoDTO ti = new TableInfoDTO();
                ti.setTableinfo_bussinessid(businessId);
                TableInfo tableInfo = tableInfoService.findOne(ti);
                Long dealid = tableInfo.getTableinfo_dealid();

                //存入变量实体
                tempVariable1.setProcessId(Long.valueOf(business_id));
                tempVariable2.setProcessId(Long.valueOf(business_id));
                tempVariable3.setProcessId(Long.valueOf(business_id));

                List<TempVariable> all = variableRepository.findAll(tempVariable1);
                int size = all.size();

                tempVariable1.setT_key("U_phone");
                tempVariable1.setT_value(phone);

                tempVariable2.setT_key("U_recvTime");
                tempVariable2.setT_value(recvTime);

                tempVariable3.setT_key("U_content");
                tempVariable3.setT_value(content);

                if (size == 0) {
                    try{
                        logger.info("+++++++++++++++++++++++++++++++++++++插入新的短信键值对++++++++++++++++++++++++++++++");
                        variableRepository.insert(tempVariable1);
                        variableRepository.insert(tempVariable2);
                        variableRepository.insert(tempVariable3);
                        logger.info("+++++++++++++++++++++++++++++++++++++插入完成++++++++++++++++++++++++++++++");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        logger.info("+++++++++++++++++++++++++++++++++++++更新短信键值对++++++++++++++++++++++++++++++");
                        variableRepository.deleteVariable(tempVariable1.getProcessId());
                        variableRepository.insert(tempVariable1);
                        variableRepository.insert(tempVariable2);
                        variableRepository.insert(tempVariable3);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                    try {
                        List<TempVariable> phoneNumber = variableRepository.findByProcessId(Long.valueOf(business_id));
                        int numberSize = phoneNumber.size() - 1;
                        TempVariable tempVariable = phoneNumber.get(numberSize);
                        String t_key = tempVariable.getT_key();

                        //解析接受的时间
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = "yyyyMMddHHmmss";
                        Date date = new SimpleDateFormat(format).parse(recvTime);
                        String time = dateFormat.format(date);

                        if(t_key.equalsIgnoreCase("U_content")){
                            String t_value = tempVariable.getT_value();
                            decorator.start(taskId);
                            if(t_value.equals("1")){
                                taskOpinion = new TaskOpinion("客户已收到短信并回复了1");
                                logger.info("客户已收到短信并回复了1");
                                updateDealInfo(businessId,dealid,1,new String("客户："+phone+",已收到短信.在："+time+"回复了1"),5);
                                sendEndMessage(phone);
                                decorator.complete(taskId,"是",taskOpinion);
                            }else if(t_value.equals("2")){
                                taskOpinion = new TaskOpinion("客户已收到短信并回复了2");
                                logger.info("客户已收到短信并回复了2");
                                updateDealInfo(businessId,dealid,1,new String("客户电话："+phone+",已收到短信.在："+time+"回复了2"),5);
                                sendEndMessage(phone);
                                decorator.complete(taskId,"是",taskOpinion);
                            }else{
                                taskOpinion = new TaskOpinion("客户回复的信息不符合要求，提示输入错误，并重新发送短信");
                                logger.info("回复的信息不符合要求可以再发一条提醒客户");
                                updateDealInfo(businessId,dealid,1,new String("客户电话："+phone+",在："+time+",回复的信息不符合要求,再发一条提醒客户重新输入"),6);
                                decorator.complete(taskId,"转",taskOpinion);
                            }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                logger.warn("【参数解析】Exception：包体为空");
            }
            response.getWriter().append("ok");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("【参数解析】Exception:"+ e.getMessage() );
        }
    }

    //发送结束语
    private void sendEndMessage(String phone) {
        String[] dataEnd = new String[2];
        dataEnd[0] = "";
        dataEnd[1] = "";
        messageService.sendMessage(phone,"256800",dataEnd);
        logger.info("发送结束语");
    }


    private ParamVo analyticParameter(String body) {
        ParamVo param = null;
        logger.info("【参数解析】xml:" + body);
        try {
            Document doc = DocumentHelper.parseText(body);
            Element root = doc.getRootElement();
            String action = root.elementText("action"); // 功能操作标识 ，SMSArrived
            String smsType = root.elementText("smsType"); // 短信类型，0：上行短信，1：手机接收状态报告
            String recvTime = root.elementText("recvTime"); // 收到上行短信/短信送达手机时间，格式：YYYYMMDDHHMMSS
            String apiVersion = root.elementText("apiVersion"); // REST API版本号，格式：YYYY-MM-DD
            String fromNum = root.elementText("fromNum"); // 发送/接收短信的手机号码，以13等开头的11位号码
            //content 短信内容，当smsType=1时，该字段的值为短信ID，即下行短信请求响应的smsMessageSid。当smsType=0时，该字段值手机回复的短信内容，utf8格式
            String content = root.elementText("content");
            String appendCode = null, subAppend = null, status = null, dateSent = null, deliverCode = null;
            if (action.equals("SMSArrived")) {
                if (smsType.equals("0")) {
                    logger.debug("上行包体");
                    //上行
                    appendCode = root.elementText("appendCode");//短信扩展码，由数字组成，对应不同的下行短信签名，smsType=0时有效。以此区分不同的下行短信签名
                    subAppend = root.elementText("subAppend");//自定义短信扩展码，对应下行时传递的subAppend，smsType=0时有效。

                } else if (smsType.equals("1")) {
                    //状态报告
                    logger.debug("状态报告包体");
                    status = root.elementText("status");  //短信到达状态, 0为接收成功，其它值为接收失败，smsType=1时有效
                    dateSent = root.elementText("dateSent");//短信发送时间，格式：YYYYMMDDHHMMSS，smsType=1时有效
                    deliverCode = root.elementText("deliverCode"); //到达状态描述，即运营商网关状态码。当status非0且smsType=1时有效
                }
            }
            param = new ParamVo(action, smsType, recvTime, apiVersion, fromNum, content, appendCode, subAppend, status, dateSent, deliverCode);
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.error("【参数解析-xml】Exception：" + e.getMessage());
        }
        return param;
    }

    public void updateDealInfo (Long business_id_,Long appDealId, Integer state,String option,Integer Result) {
        Dealinfo dealinfo =  new Dealinfo();
        dealinfo.setId(appDealId);
        dealinfo.setAppdealDealstate(state);// 1:表示该条记录已被执行过
        dealinfo.setAppdealContent(option);//详细的操作过程
        dealinfo.setAppdealResult(Result);//1:表示打通 2:表示没打通 5：收到正确短信回复 6：短信回复的信息不符合要求
        String Resulttext ="";
        if(Result == 5){
            Resulttext = "收到正确短信回复";
        }else if(Result == 6){
            Resulttext = "短信回复的信息不符合要求";
        }else{
            Resulttext = "其他异常";
        }
        dealinfo.setAppdealResulttext(Resulttext);//Result的中文对照
        dealinfoService.updateIgnore(dealinfo);

        TableInfoDTO cond = new TableInfoDTO();
        cond.setTableinfo_bussinessid(business_id_);
        TableInfo tableInfo = tableInfoService.findBasicOne(cond);
        tableInfo.setTableinfo_dealid(dealinfo.getId());
        tableInfo.setTableinfo_dealstate(dealinfo.getAppdealDealstate());
        tableInfo.setTableinfo_uptabid(dealinfo.getId());
        tableInfoService.updateIgnore(tableInfo);
    }
}
