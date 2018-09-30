package com.ifrabbit.nk.express.service.impl;

import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import com.ifrabbit.nk.express.domain.ExpressInfoRecord;
import com.ifrabbit.nk.express.domain.ExpressInfoRecordDTO;
import com.ifrabbit.nk.express.repository.ExpressInfoDetailRepository;
import com.ifrabbit.nk.express.repository.ExpressInfoRecordRepository;
import com.ifrabbit.nk.express.repository.ProblemRepository;
import com.ifrabbit.nk.express.service.ExpressInfoDetailService;
import com.ifrabbit.nk.express.service.ExpressInfoRecordService;
import com.ifrabbit.nk.express.utils.HttpUtils;
import com.ifrabbit.nk.usercenter.domain.Company;
import com.ifrabbit.nk.usercenter.domain.CompanyDTO;
import com.ifrabbit.nk.usercenter.repository.CompanyRepository;
import com.ifrabbit.nk.usercenter.service.CompanyService;
import com.ifrabbit.nk.usercenter.service.UserReportService;
import ir.nymph.date.DateTime;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.support.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.ifrabbit.nk.express.utils.TimeUtil.getDistanceTime;


/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/16
 * Time:17:21
 */

/**
  *   info的值: {"LogisticCode":"494479590608",
  *   "ShipperCode":"ZTO",
  *   "Traces":[{"AcceptStation":"【滁州市】  【滁州定远县】（0550-4499171） 的 占信公司 （13955035953） 已揽收","AcceptTime":"2018-05-15 15:07:25"},
  *   {"AcceptStation":"【滁州市】  快件离开 【滁州定远县】 发往 【杭州中转部】","AcceptTime":"2018-05-15 18:36:47"},
  *   {"AcceptStation":"【蚌埠市】  快件到达 【蚌埠中转部】","AcceptTime":"2018-05-15 20:57:59"},
  *   {"AcceptStation":"【蚌埠市】  快件离开 【蚌埠中转部】 发往 【杭州中转部】","AcceptTime":"2018-05-15 20:59:31"},
  *   {"AcceptStation":"【嘉兴市】  快件到达 【杭州中转部】","AcceptTime":"2018-05-16 05:25:22"},
  *   {"AcceptStation":"【嘉兴市】  快件离开 【杭州中转部】 发往 【杭州城西一部】","AcceptTime":"2018-05-16 08:15:42"},
  *   {"AcceptStation":"【杭州市】  快件已到达 【杭州城    西一部】（0571-87621738、15381028189）,业务员 中天（15558189129） 正在第1次派件, 请保持电话畅通,并耐心等待","AcceptTime":"2018-05-16 13:20:14"},
  *   {"AcceptStation":"【杭州市】  快件已在 【杭州城西一部】 签收,签收人: 同事\/同学, 感谢使用中通快递,期待再次为您服务!","AcceptTime":"2018-05-16 18:10:18"}],
  *   "State":"3",
  *   "Success":true}
  */

/**
 *  info的值: {"LogisticCode":"780098068058",
 *  "ShipperCode":"ZTO",
 *  "Traces":[{"AcceptStation":"【广州市】  【广州花都】（020-37738523） 的 马溪 （18998345739） 已揽收","AcceptTime":"2018-03-07 00:01:55"},
 *  {"AcceptStation":"【广州市】  快件离开 【广州花都】 发往 【石家庄中转】","AcceptTime":"2018-03-07 00:40:57"},
 *  {"AcceptStation":"【广州市】  快件到达 【广州中心】","AcceptTime":"2018-03-07 01:36:53"},
 *  {"AcceptStation":"【广州市】  快件离开 【广州中心】 发往 【石家庄】","AcceptTime":"2018-03-07 01:38:45"},
 *  {"AcceptStation":"【石家庄市】  快件到达 【石家庄】","AcceptTime":"2018-03-08 21:00:44"},
 *  {"AcceptStation":"【石家庄市】  快件离开 【石家庄】 发往 【长安三部】","AcceptTime":"2018-03-08 23:43:44"},
 *  {"AcceptStation":"【石家庄市】  快件已到达 【长安三部】（0311-85344265）,业务员 容晓光（13081105270） 正在第1次派件, 请保持电话畅通,并耐心等待","AcceptTime":"2018-03-09 09:03:10"},
 *  {"AcceptStation":"【石家庄市】  快件已在 【长安三部】 签收,签收人: 本人, 感谢使用中通快递,期待再次为您服务!","AcceptTime":"2018-03-09 11:59:26"}],
 *  "State":"3",
 *  "Success":true}
 */

/**
 * {"LogisticCode":"454244690951",
 * "ShipperCode":"ZTO",
 * "Traces":[{"AcceptStation":"[武汉市]  [中吉武汉仓] 的 鑫源泽数码专营店 (17742032635) 已收件","AcceptTime":"2017-09-17 13:52:50"},
 * {"AcceptStation":"[武汉市]  快件到达 [武汉中转部]","AcceptTime":"2017-09-17 19:56:01"},
 * {"AcceptStation":"[武汉市]  快件离开 [武汉中转部] 发往 [成都中转]","AcceptTime":"2017-09-17 21:41:00"},
 * {"AcceptStation":"[荆州市]  快件到达 [荆州中转部]","AcceptTime":"2017-09-18 03:42:33"},
 * {"AcceptStation":"[南充市]  快件到达 [南充中转站]","AcceptTime":"2017-09-18 18:28:08"},
 * {"AcceptStation":"[成都市]  快件离开 [成都中转] 发往 [成都华阳]","AcceptTime":"2017-09-19 04:30:04"},
 * {"v":"[成都市]  快件已到达 [成都华阳],业务员 雍-何淋(18190713228) 正在第2次派件, 请保持电话畅通,并耐心等待","AcceptTime":"2017-09-19 14:13:45"},
 * {"AcceptStation":"[成都市]  快件已在 [成都华阳] 签收,签收人: 本人, 感谢使用中通快递,期待再次为您服务!","AcceptTime":"2017-09-19 18:01:22"}],
 * "State":"3",
 * "Success":true}
 */
@Service("expressService")
@Transactional
public class ExpressInfoDetailServiceImpl
        extends AbstractCrudService<ExpressInfoDetailRepository,ExpressInfoDetail,Long> implements ExpressInfoDetailService{

    private static Logger logger = LoggerFactory.getLogger(ExpressInfoDetailServiceImpl.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    public ExpressInfoDetailServiceImpl(ExpressInfoDetailRepository repository) {
        super(repository);
    }

    @Autowired
    private ExpressInfoDetailRepository expressInfoDetailRepository;

    @Autowired
    private ExpressInfoRecordService expressInfoRecordService;

    @Autowired
    UserReportService userReportService;


    private ExpressInfoRecord expressInfoRecord = new ExpressInfoRecord();

    @Override
    public ExpressInfoDetail findByType(Integer expressType,String expressNumber) {
        try{
            List<ExpressInfoDetail> express = expressInfoDetailRepository.findExpressNumber(expressNumber);
            List<ExpressInfoDetail> ex = new LinkedList<ExpressInfoDetail>();
            if(!express.isEmpty() || express.size() != 0){
                int size = express.size();
                for(int i=0; i<size; i++){
                    ExpressInfoDetail expressInfoDetail = express.get(i);
                    System.out.println("当前是第 " + i + "个物流信息");
                    if(expressInfoDetail.getExpress_detailtype() == expressType) {
                        logger.info("找到了物流类型是"+expressType+"的物流信息： " + expressInfoDetail.toString());
                        ex.add(expressInfoDetail);
                    }
                }
                if(ex != null){
                    return ex.get(ex.size()-1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ExpressInfoDetail();
    }

    @Override
    public String expressAnalysis (ExpressInfoRecord expressInfoRecord,String expressNumber,Long businessIdLong,Integer analysisType,Long taskID) {
        logger.info("开始解析物流");
        String host = "https://wdexpress.market.alicloudapi.com";
        String path = "/gxali";
        String method = "GET";
        String appcode = "12f6063fc9454f85a865f88900c5b418";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("n", expressNumber);
        querys.put("t", "ZTO");


        expressInfoRecord.setExpress_number(expressNumber);

        String info = null;
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            logger.info(response.toString());
            //获取response的body
            info = EntityUtils.toString(response.getEntity());
            logger.info("info的值: " + info);
        } catch (Exception e) {
            e.printStackTrace();
        }


        logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        logger.info("查找.........................");
        logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        JSONObject json = JSONObject.fromObject(info);

        logger.info("\n");
        logger.info("\n");
        logger.info("++++++++++++++++++++++++++++++运单号是从前台获取到+++++++++++++++++++++++++++++++");
        /**
         * 运单号,是从前台获取到
         */
        /**
         * state:
         *         0:无物流信息
         *         3：已签收
         * 先判断state的状态值，根据状态值的不同，改变操作
         */
        String errorCompanyName = "";
        Integer match =0;
        List<String> errorCompanyList = new ArrayList<>();
        String state_firstDo = json.getString("State");
        if (state_firstDo.equalsIgnoreCase("0")) {
            logger.info("state的状态是0，开始查询Reason");
            //是0，就有Reason
            String reason = json.getString("Reason");
            if (reason.equals("暂无轨迹信息")) {
                if(analysisType != 1) {
                    logger.info("暂无轨迹信息");
                    //设置物流状态为10，无物流
                    expressInfoRecord.setExpress_type(10);
                    //设置物流节点为20，异常
                    expressInfoRecord.setExpress_updatestate(20);
                    //设置最末物流时间,没有物流信息也就没有时间，那暂时设置未当前查询的时间
                    expressInfoRecord.setExpress_lastdate(new Date().toString());
                    //设置物流接口状态10，完整
                    expressInfoRecord.setInterface_state(10);
                }else{
                    return errorCompanyName="无揽收记录";
                }
            }
        } else {
            if(analysisType != 1) {
                if (analysisType == 4) {
                    if (state_firstDo.equalsIgnoreCase("3")) {
                        errorCompanyName = "20";
                    }
                } else if (analysisType == 2) {//2类型=查询物流信息是否更新
                    JSONArray jsonArray = json.getJSONArray("Traces");
                    int size = jsonArray.size();
                    return size + "";
                } else if (analysisType == 6) {//6类型=查询物流信息是否退回
                    JSONArray jsonArray = json.getJSONArray("Traces");
                    int size = jsonArray.size();
                    //判断是否停滞
                    isUpdate(expressInfoRecord,jsonArray,size);
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String acceptStation = jsonObject.getString("AcceptStation");
                        if (acceptStation.contains("被退回")) {
                            return "是";
                        }
                    }
                    return "否";
                } else if (analysisType == 7) {//7类型=查询物流信息是否退回到发件网点
                    JSONArray jsonArray = json.getJSONArray("Traces");
                    int size = jsonArray.size() - 1;
                    JSONObject beginJson = jsonArray.getJSONObject(0);
                    String acceptStation = beginJson.getString("AcceptStation");

                    String beginCompanyName = GetBeginComany(acceptStation);

                    JSONObject jsonObject = jsonArray.getJSONObject(size);
                    String acceptStation1 = jsonObject.getString("AcceptStation");
                    String endCompanyName = GetEndComany(acceptStation1);

                    //判断是否停滞
                    isUpdate(expressInfoRecord,jsonArray,size);
                    if (beginCompanyName.equalsIgnoreCase(endCompanyName)) {
                        return "1";
                    }
                    return "0";
                } else if (analysisType == 8) {//8类型=查询物流信息是否有改地址关键字
                    JSONArray jsonArray = json.getJSONArray("Traces");
                    int size = jsonArray.size();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String acceptStation = jsonObject.getString("AcceptStation");
                        if (acceptStation.contains("改地址")) {
                            return "是";
                        }
                    }
                    //判断是否停滞
                    isUpdate(expressInfoRecord,jsonArray,size);
                    return "否";
                }
            }else{
                logger.info("\n");
                logger.info("\n");
                //用一个list来放多个detail
                ArrayList<ExpressInfoDetail> detailList = new ArrayList<>();
                logger.info("+++++++++++++++++++++++++++++++++获取最新时间++++++++++++++++++++++++++++++++++++");
                /**
                 * 获取最新时间
                 */
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                //获得Traces这个数组，里面有多组json数据
                JSONArray jsonArray = json.getJSONArray("Traces");
                int size = jsonArray.size();

                for (int i = 0; i < size; i++) {
                    ExpressInfoDetail expressInfoDetail = new ExpressInfoDetail();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //获取最末数据的 关键字为 AcceptTime的值
                    String time = jsonObject.getString("AcceptTime");
                    String acceptStation = jsonObject.getString("AcceptStation");
                    match = match(acceptStation);
                    if (match == 0) {
                        logger.info("++++++++++++++++++++++++++++++++++++快递状态没有匹配的字符++++++++++++++++++++++++++++++++++++");
                    }else if(match == 4){
                        ExpressInfoDetail checkDeail = detailList.get(detailList.size()-1);
                        Integer express_detailtype = checkDeail.getExpress_detailtype();
                        if(express_detailtype != 3){
                            match = 3;
                            ExpressInfoDetail InfoDetail = new ExpressInfoDetail();
                            InfoDetail.setExpress_detailtype(match);
                            //先获取单号插入
                            InfoDetail.setExpress_number(expressNumber);
                            detailList = twoCompanyName(detailList, acceptStation, InfoDetail, match, errorCompanyList, time);
                        }
                        match = 4;
                        expressInfoRecord.setExpress_type(20);
                    }
                        expressInfoDetail.setExpress_detailtype(match);
                        //先获取单号插入
                        expressInfoDetail.setExpress_number(expressNumber);

                    int beforeSize = detailList.size();
                    detailList = twoCompanyName(detailList, acceptStation, expressInfoDetail, match, errorCompanyList, time);
                    int behindSize = detailList.size();
                    if(beforeSize == behindSize && match==4){
                        jsonObject = jsonArray.getJSONObject(i-1);
                        //获取最末数据的 关键字为 AcceptTime的值
                        time = jsonObject.getString("AcceptTime");
                        acceptStation = jsonObject.getString("AcceptStation");
                        int size1 = detailList.size();
                        detailList = twoCompanyName(detailList, acceptStation, expressInfoDetail, 4, errorCompanyList, time);
                        int size2 = detailList.size();
                        if(size2 == size1){
                            jsonObject = jsonArray.getJSONObject(i-2);
                            //获取最末数据的 关键字为 AcceptTime的值
                            time = jsonObject.getString("AcceptTime");
                            acceptStation = jsonObject.getString("AcceptStation");
                            detailList = twoCompanyName(detailList, acceptStation, expressInfoDetail, 4, errorCompanyList, time);
                        }
                    }
                }

                List<String> errorList = errorCompanyList.stream().distinct().collect(Collectors.toList());

                errorCompanyName = StringUtils.join(errorList, ",");

                if ("".equals(errorCompanyName)) {
                    logger.info("\n");
                    logger.info("\n");
                    logger.info("++++++++++++++++++++++++++++++++++物流明细类型+++++++++++++++++++++++++++++++++");
                    //如果上面解析的时候显示已签收就会把Express_type放进去，这里就不用执行
                    if(expressInfoRecord.getExpress_type() == null){
                        if ( match == 3) {
                            expressInfoRecord.setExpress_type(50);//最后一站中心到派件中之间左右状态（最后一站中心发往派件网点，到达派件网点,派件中）
                        } else if (match == 2 || match == 1 || match == 6) {
                            expressInfoRecord.setExpress_type(40);//从离开出港网点到最后一站中心发出前的所有状态（已揽收，中途在转运，到达最后一站中心）
                        } else if (match == 4) {
                            expressInfoRecord.setExpress_type(20);//已签收
                        } else {
                            expressInfoRecord.setExpress_type(10);//没有物流
                        }
                    }

                    //打印expressInfoDetail 信息
                    logger.info("++++++++++++++++++" + expressInfoRecord.toString() + "++++++++++++++++++++++++++++");

                    logger.info("\n");
                    logger.info("\n");
                    logger.info("++++++++++++++++++++++++++++++++++接口状态+++++++++++++++++++++++++++++++++");
                    String interfaceState = json.getString("Success");
                    logger.info("状态为: " + interfaceState);
                    if (interfaceState.equalsIgnoreCase("true")) {
                        logger.info("接口状态正常");
                        expressInfoRecord.setInterface_state(1);
                    } else if (interfaceState.equalsIgnoreCase("flase")) {
                        logger.info("接口状态超时");
                        expressInfoRecord.setInterface_state(2);
                    } else {
                        logger.info("接口状态不完整");
                        expressInfoRecord.setInterface_state(0);
                    }

                    logger.info("\n");
                    logger.info("\n");
                    logger.info("++++++++++++++++++++++++++++++++++最早物流信息时间+++++++++++++++++++++++++++++++++");
                    //获得Traces这个数组，里面有多组json数据
                    //获取最末的数据
                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                    //获取最末数据的 关键字为 AcceptTime的值
                    String earlyTime = jsonObj.getString("AcceptTime");
                    try {
                        //把String转成Date类型
                        Date date = simpleDateFormat.parse(earlyTime);
                        //装进expressInfoDetail
                        expressInfoRecord.setArea_varchar1(earlyTime);
                        logger.info("最早时间: " + earlyTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    logger.info("\n");
                    logger.info("\n");
                    logger.info("++++++++++++++++++++++++++++++++++最末物流信息时间+++++++++++++++++++++++++++++++++");
                    //最末物流信息时间

                    JSONObject jsonObjectLast = jsonArray.getJSONObject(size - 1);
                    String acceptTime = jsonObjectLast.getString("AcceptTime");
                    try {
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        logger.info("最末物流信息时间： " + acceptTime);
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        expressInfoRecord.setExpress_lastdate(acceptTime);
                        logger.info(expressInfoRecord.toString());
                    } catch (Exception e) {
                        logger.error("expressInfoRecord SQL ERROR");
                        e.printStackTrace();
                    }

                    logger.info("\n");
                    logger.info("\n");
                    logger.info("++++++++++++++++++++++++++++++++++物流是否更新+++++++++++++++++++++++++++++++++");
                    isUpdate(expressInfoRecord,jsonArray, size);

                }else{
                    //将errorName 保存到库 ExpressInfoRecord上
                    expressInfoRecord.setExpress_errorcompanyname(errorCompanyName);
                }
                //更新或者插入record
                updateOrInsertExpressInfoRecord(expressInfoRecord, taskID);

                //因为update暂时无法使用，先用delete再insert代替
                expressInfoDetailRepository.deleteExpressNumber(expressNumber);
                int size2 = detailList.size();
                for (int i = 0; i < size2; i++) {
                    ExpressInfoDetail expressInfoDetail = detailList.get(i);
                    expressInfoDetail.setExpressrecord_id(expressInfoRecord.getId());
                    expressInfoDetailRepository.insert(detailList.get(i));
                }
            }
        }
        if(analysisType ==3) {
            try {
                if (!"".equals(errorCompanyName)) {
                    ExpressInfoRecord errorExpressInfoRecord = new ExpressInfoRecord();
                    errorExpressInfoRecord.setExpress_number(expressNumber);
                    errorExpressInfoRecord.setArea_varchar1(String.valueOf(errorCompanyName));
                    expressInfoRecord = errorExpressInfoRecord;
                }
                logger.info("数据库插入expressInfoRecord");
                List<ExpressInfoRecord> expressInfo = expressInfoRecordService.findByExpressNumber(expressNumber);
                if (expressInfo.isEmpty()) {
                    expressInfoRecordService.insert(expressInfoRecord);
                } else {
                    logger.info("已经有数据，就执行更新： " + expressInfo);
                    expressInfoRecordService.update(expressInfoRecord);
                }
            } catch (Exception e) {
                logger.error("expressInfoRecord INSERT ERROR");
                e.printStackTrace();
            }
        }
        return  errorCompanyName;
    }

    private void updateOrInsertExpressInfoRecord(ExpressInfoRecord expressInfoRecord, Long taskID) {
        try {
            logger.info("数据库插入expressInfoRecord");
            expressInfoRecord.setExpress_taskid(taskID);//用于最新物流查找

            ExpressInfoRecordDTO expressInfoRecordDTO = new ExpressInfoRecordDTO();
            expressInfoRecordDTO.setExpress_taskid(taskID);
            ExpressInfoRecord expressInfoRecordServiceOne = expressInfoRecordService.findOne(expressInfoRecordDTO);
            if(expressInfoRecordServiceOne == null){
                expressInfoRecordService.insert(expressInfoRecord);
            }else{
                expressInfoRecordService.updateIgnore(expressInfoRecord);
            }
        } catch (Exception e) {
            logger.error("expressInfoRecord INSERT ERROR");
            e.printStackTrace();
        }
    }

    //是否更新
    private void isUpdate(ExpressInfoRecord expressInfoRecord,JSONArray jsonArray, int size) {
        /**
         * 任意物流节点，是否正常更新10=正常，20=异常，就是用  当前时间 - 物流最后记录的时间 判断是否大于2天。
         */
        //先获取全部list
        JSONObject jsonObjectLastDate = jsonArray.getJSONObject(size - 1);
        //再根据名字获取时间
        String lastTime = jsonObjectLastDate.getString("AcceptTime");
        String lastStation = jsonObjectLastDate.getString("AcceptStation");

        //获取当前时间
        String nowTime = DateTime.now().toString();

        try {
            logger.info("相差时间： " + getDistanceTime(lastTime, nowTime));
            Long distanceTime = getDistanceTime(lastTime, nowTime);

            //获取判断停滞超过时效(小时)
            Integer prescription = Integer.valueOf(userReportService.getParameter("W10"));

            if (distanceTime > prescription) {
                //设置物流状态为异常
                logger.info("物流超过2天....设置状态值为20");
                expressInfoRecord.setExpress_updatestate(20);
                //把停滞的网点名字存入expressInfoRecord
                ArrayList<String> arrayList = new ArrayList();
                Integer count = 0;//计数停滞网点的数量
                stagnationCompanyName(arrayList,lastStation,count,expressInfoRecord);
                expressInfoRecord.setExpress_stagnationcompanyname(arrayList.get(0));
            } else {
                //设置物流状态为正常
                logger.info("物流正常....设置状态值为10");
                expressInfoRecord.setExpress_updatestate(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String GetEndComany(String acceptStation) {
        String[] split = acceptStation.split(" ");
        int j = 0;
        String[] temp = new String[3];
        int length = temp.length;
        for(int i=0; i<length; i++){
            temp[i] = "";
        }
        for (int i = 0; i < split.length; i++) {
            String sp = split[i];
            //如果0的索引位置是'['那就开始打印
            if (sp.indexOf("【") == 0) {
                String substring = sp.substring(sp.indexOf("【") + 1, sp.indexOf("】"));
                temp[j] = substring;
                logger.info("temp【" + j +"】的值: " + temp[j]);
                j++;
            }
        }

        String endCompanyName = temp[1];
        return endCompanyName;
    }

    private String GetBeginComany(String acceptStation) {
        String[] split = acceptStation.split(" ");
        int j = 0;
        String[] temp = new String[3];
        int length = temp.length;
        for(int i=0; i<length; i++){
            temp[i] = "";
        }
        for (int i = 0; i < split.length; i++) {
            String sp = split[i];
            //如果0的索引位置是'['那就开始打印
            if (sp.indexOf("【") == 0) {
                String substring = sp.substring(sp.indexOf("【") + 1, sp.indexOf("】"));
                temp[j] = substring;
                logger.info("temp【" + j +"】的值: " + temp[j]);
                j++;
            }
        }

        String beginCompanyName = temp[1];
        return beginCompanyName;
    }

    /**
     *  info的值: {"LogisticCode":"780098068058",
     *  "ShipperCode":"ZTO",
     *  "Traces":[{"AcceptStation":"【广州市】  【广州花都】（020-37738523） 的 马溪 （18998345739） 已揽收","AcceptTime":"2018-03-07 00:01:55"},
     *  {"AcceptStation":"【广州市】  快件离开 【广州花都】 发往 【石家庄中转】","AcceptTime":"2018-03-07 00:40:57"},
     *  {"AcceptStation":"【广州市】  快件到达 【广州中心】","AcceptTime":"2018-03-07 01:36:53"},
     *  {"AcceptStation":"【广州市】  快件离开 【广州中心】 发往 【石家庄】","AcceptTime":"2018-03-07 01:38:45"},
     *  {"AcceptStation":"【石家庄市】  快件到达 【石家庄】","AcceptTime":"2018-03-08 21:00:44"},
     *  {"AcceptStation":"【石家庄市】  快件离开 【石家庄】 发往 【长安三部】","AcceptTime":"2018-03-08 23:43:44"},
     *  {"AcceptStation":"【石家庄市】  快件已到达 【长安三部】（0311-85344265）,业务员 容晓光（13081105270） 正在第1次派件, 请保持电话畅通,并耐心等待","AcceptTime":"2018-03-09 09:03:10"},
     *  {"AcceptStation":"【石家庄市】  快件已在 【长安三部】 签收,签收人: 本人, 感谢使用中通快递,期待再次为您服务!","AcceptTime":"2018-03-09 11:59:26"}],
     *  "State":"3",
     *  "Success":true}
     */

    //匹配关键字
    private Integer match(String content) {
       // 1=揽收，2=运输中，3=派件中，4=已签收',5=客户要求改地址,6="退回"
        String regEx="收件|揽件|已揽收|已收件";//1
        String transport="快件离开|快件到达|快件已到达|快件已在|发往|";//2
        String dispatch="派件";//3
        String signed="已签收|签收";//4
        String changeAddress="改地址";//5
        String returnBack="退回";//6
        String collection="快递超市|蜂巢|代收";


        //TODO 快递超市、丰巢代收，代表什么结果

        Pattern pregEx=Pattern.compile(regEx);
        Pattern p1transport=Pattern.compile(transport);
        Pattern p3dispatch=Pattern.compile(dispatch);
        Pattern p2signed=Pattern.compile(signed);
        Pattern p4changed=Pattern.compile(changeAddress);
        Pattern p5return=Pattern.compile(returnBack);

        Pattern p6collection=Pattern.compile(collection);



        Matcher mpregEx=pregEx.matcher(content);
        Matcher mp1transport=p1transport.matcher(content);
        Matcher mp2signed=p2signed.matcher(content);
        Matcher mp3dispatch=p3dispatch.matcher(content);
        Matcher mp4changed=p4changed.matcher(content);
        Matcher mp5return=p5return.matcher(content);

        Matcher mp6collection=p6collection.matcher(content);


        boolean result1=mpregEx.find();
        boolean result2=mp1transport.find();
        boolean result3=mp3dispatch.find();
        boolean result4=mp2signed.find();
        boolean result5=mp4changed.find();
        boolean result6=mp5return.find();

        boolean result7=mp6collection.find();



        if(result2 && result3){
//            network_flag = true;
            return 3;
        }else if(result2 && result4){
            return 4;
        }else if(result1){
            return 1;
        }else if(result6){
            return 2;
        }else if(result5){
            return 2;
        }else if(result2){
            if(content.contains("中心")||content.contains("中转站")){
                return 6;
            }else if(result7){
                return 4;
            }else{
                return 2;
            }
        }else if(result3){
            return 3;
        }else if(result4 || result7){
            return 4;
        }
        return 0;
    }

    //判断2站id和名字
    private ArrayList twoCompanyName(ArrayList detailList,String acceptStation,ExpressInfoDetail expressInfoDetail,Integer match, List<String> errorCompanyList,String time) {
        logger.info("++++++++++++++++++++++++++进入判断起始站id和名字的方法++++++++++++++++++++++++++++++++++++++++");
        /**
         * 起始站id和名字    先从物流上获取到站点名字，然后根据名字到网点表里找到ID
         */
        boolean contains = false;//true表示解析到了丰巢之类的关键词
        String result = userReportService.getParameter("wljxgl");

        String[] param = result.split(":");
        int paramLength = param.length;
        for (int i=0; i<paramLength; i++){
            String word = param[i];
            contains = acceptStation.contains(word);
            if(contains)
                return detailList;
        }

        //先用split分割string
        String[] split = acceptStation.split(" ");

        int j = 0;
        String[] temp = new String[3];
        int length = temp.length;
        for(int i=0; i<length; i++){
            temp[i] = "";
        }
        for (int i = 0; i < split.length; i++) {
            String sp = split[i];
            //如果0的索引位置是'['那就开始打印
            if (sp.indexOf("【") == 0) {
                int front = sp.indexOf("【") + 1;
                int behind = sp.indexOf("】");

                if(behind == -1)
                    continue;
                String substring = sp.substring(front, behind);
                temp[j] = substring;
                logger.info("temp【" + j +"】的值: " + temp[j]);
                j++;
            }
        }

        String beginCompanyName = temp[1];
        if(beginCompanyName == "" && match == 4)
            return detailList;
        String endCompanyName = temp[2];

        for (int i=0; i<paramLength; i++){
            String word = param[i];
            contains = beginCompanyName.contains(word);
            contains = endCompanyName.contains(word);
            if(contains)
                return detailList;
        }

        //判断2个开始和终点公司是否是空的，空的就退出该方法，返回传进来的detailList,不是空的，就放入新的开始和终点公司
        if(beginCompanyName.equalsIgnoreCase("") && endCompanyName.equalsIgnoreCase("")){
            return detailList;
        }else{
            CompanyDTO dto = new CompanyDTO();
            dto.setCompany_name(beginCompanyName);
            Company beginCompany = companyService.findOne(dto);
            if(beginCompany != null){
                expressInfoDetail.setBegin_companyname(beginCompanyName);
                expressInfoDetail.setBegin_companyid(String.valueOf(beginCompany.getId()));
                expressInfoDetail.setBegin_companytype(Integer.parseInt(beginCompany.getCompany_type()));
//                if(network_flag && match==3){
//                }
            }else{
                errorCompanyList.add(beginCompanyName);
            }
            if(!"".equals(endCompanyName)){
                dto.setCompany_name(endCompanyName);
                Company endCompany = companyService.findOne(dto);
                if(endCompany != null){
                    expressInfoDetail.setEnd_companyname(endCompanyName);
                    expressInfoDetail.setEnd_companyid(String.valueOf(endCompany.getId()));
                    expressInfoDetail.setEnd_companytype(Integer.parseInt(endCompany.getCompany_type()));
                }else{
                    errorCompanyList.add(endCompanyName);
                }
            }
            try {
                //装进expressInfoDetail
                expressInfoDetail.setExpress_detaildate(time);
                logger.info("最新时间: " + time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //加入到arraylist中
            detailList.add(expressInfoDetail);
        }
        return detailList;
    }

    //停滞网点的名字，肯能有1个肯能有2个
    private static ArrayList stagnationCompanyName(ArrayList detailList, String acceptStation,Integer count,ExpressInfoRecord expressInfoRecord) {
        //先用split分割string
        String[] split = acceptStation.split(" ");
        int j = 0;
        String[] temp = new String[3];
        int length = temp.length;
        for(int i=0; i<length; i++){
            temp[i] = "";
        }
        for (int i = 0; i < split.length; i++) {
            String sp = split[i];
            //如果0的索引位置是'['那就开始打印
            if (sp.indexOf("【") == 0) {
                int front = sp.indexOf("【") + 1;
                int behind = sp.indexOf("】");

                if(behind == -1)
                    continue;
                String substring = sp.substring(front, behind);
                temp[j] = substring;
                j++;
            }
        }
        String beginCompanyName = temp[1];
        String endCompanyName = temp[2];

        if(beginCompanyName.isEmpty() || beginCompanyName.equals("")){
            count += 1;
        }
        if(endCompanyName.isEmpty() || beginCompanyName.equals("")){
            count += 1;
        }

        expressInfoRecord.setExpress_networknumber(count);
        //加入到arraylist中
        detailList.add(beginCompanyName+endCompanyName);

        return detailList;
    }





    //判断终止站点的类型
    private void twoCompanyType(String companyName,ExpressInfoDetail expressInfoDetail) {
        logger.info("++++++++++++++++++++++++++进入判断终止站点的类型的方法++++++++++++++++++++++++++++++++++++++++");
        System.out.println(companyName);

        String[] split = companyName.split(" ");
        int j = 0;
        String[] temp = new String[3];
        int length = temp.length;
        for(int i=0; i<length; i++){
            temp[i] = "";
        }
        for (int i = 0; i < split.length; i++) {
            String sp = split[i];
            //如果0的索引位置是'['那就开始打印
            if (sp.indexOf("【") == 0) {
                String substring = sp.substring(sp.indexOf("【") + 1, sp.indexOf("】"));
                temp[j] = substring;
                logger.info("temp【" + j +"】的值: " + temp[j]);
                j++;
            }
        }
        String s2 = temp[2];
        String s1 = temp[1];

        if ((s2.indexOf("中转") >= 0) || (s2.indexOf("中心") >= 0)) {
            expressInfoDetail.setEnd_companytype(2);
            logger.info("分拨网点");
        } else if(s2.equalsIgnoreCase("")){
            expressInfoDetail.setEnd_companytype(0);
        }else{
            expressInfoDetail.setEnd_companytype(1);
            logger.info("派件或者收件网点");
        }

        if ((s1.indexOf("中转") >= 0) || (s1.indexOf("中心") >= 0)) {
            expressInfoDetail.setBegin_companytype(2);
            logger.info("分拨网点");
        } else if(s1.equalsIgnoreCase("")){
            expressInfoDetail.setEnd_companytype(0);
        }else{
            expressInfoDetail.setBegin_companytype(1);
            logger.info("派件或者收件网点");
        }

    }


}
