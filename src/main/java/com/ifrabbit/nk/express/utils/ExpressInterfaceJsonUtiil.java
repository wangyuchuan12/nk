package com.ifrabbit.nk.express.utils;

import com.ifrabbit.nk.express.domain.ExpressInfoDetail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/16
 * Time:9:59
 */
public class ExpressInterfaceJsonUtiil {

        public void parseJSON(String jsonData){
            System.out.println("开始使用parseJSON解析......................");
//        {"LogisticCode":"454244690951",
// "ShipperCode":"ZTO",
// "Traces":[
// {"AcceptStation":"[武汉市]  [中吉武汉仓] 的 鑫源泽数码专营店 (17742032635) 已收件",
// "AcceptTime":"2017-09-17 13:52:50"},
// {"AcceptStation":"[武汉市]  快件到达 [武汉中转部]",
// "AcceptTime":"2017-09-17 19:56:01"},
// {"AcceptStation":"[武汉市]  快件离开 [武汉中转部] 发往 [成都中转]",
// "AcceptTime":"2017-09-17 21:41:00"},
// {"AcceptStation":"[荆州市]  快件到达 [荆州中转部]",
// "AcceptTime":"2017-09-18 03:42:33"},
// {"AcceptStation":"[南充市]  快件到达 [南充中转站]",
// "AcceptTime":"2017-09-18 18:28:08"},
// {"AcceptStation":"[成都市]  快件离开 [成都中转] 发往 [成都华阳]",
// "AcceptTime":"2017-09-19 04:30:04"},
// {"v":"[成都市]  快件已到达 [成都华阳],
// 业务员 雍-何淋(18190713228) 正在第2次派件, 请保持电话畅通,并耐心等待",
// "AcceptTime":"2017-09-19 14:13:45"},
// {"AcceptStation":"[成都市]  快件已在 [成都华阳] 签收,签收人: 本人, 感谢使用中通快递,期待再次为您服务!",
// "AcceptTime":"2017-09-19 18:01:22"}],
// "State":"3","Success":true}

            JSONObject json = JSONObject.fromObject(jsonData);
            ExpressInfoDetail expressDetail = new ExpressInfoDetail();


            System.out.println("++++++++++++++++++++++++++++++运单号是从前台获取到+++++++++++++++++++++++++++++++");
            /**
             * 运单号,是从前台获取到
             */

            System.out.println("+++++++++++++++++++++++++++++++++获取最新时间++++++++++++++++++++++++++++++++++++");
            /**
             * 获取最新时间
             */

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            //获得Traces这个数组，里面有多组json数据
            JSONArray jsonArray = json.getJSONArray("Traces");
            //获取最末的数据
            JSONObject jsonObject = jsonArray.getJSONObject((jsonArray.size() - 1));
            //获取最末数据的 关键字为 AcceptTime的值
            String time = jsonObject.getString("AcceptTime");
            try{
                //把String转成Date类型
                //装进expressDetail
                expressDetail.setExpress_detaildate(time);
                System.out.println("最新时间: " + time);
            }catch (Exception e){
                e.printStackTrace();
            }


            System.out.println("+++++++++++++++++++++++++++++++++起始站id和名字+++++++++++++++++++++++++++++++++");
            /**
             * 起始站id和名字    先从物流上获取到站点名字，然后根据名字到网点表里找到ID
             */

            JSONObject jsonObjectStart = jsonArray.getJSONObject(0);
            String acceptStation = jsonObjectStart.getString("AcceptStation");

            //先用split分割string
            String[] split = acceptStation.split(" ");
            int j = 0;
            String[] temp = new String[2];
            for (int i = 0; i < split.length-1; i++) {
                String sp = split[i];
                //如果0的索引位置是'['那就开始打印
                if (sp.indexOf("[") == 0) {
                    String substring = sp.substring(sp.indexOf("[") + 1, sp.indexOf("]"));
                    temp[j] = substring;
                    System.out.println("temp[" + j + "]的值: "+ temp[j]);
                    expressDetail.setBegin_companyname(temp[1]);
                    j++;
                    if(j == 2)break;
                }
            }
            System.out.println("起始公司名称是： " + expressDetail.getBegin_companyname());
            //TODO 根据公司名字到网点表找id,写入数据库




            System.out.println("++++++++++++++++++++++++++++++++起始站点的类型+++++++++++++++++++++++++++++++");
            //起始站点的类型
            String name = expressDetail.getBegin_companyname();
            String substring = name.substring(name.length() - 5, name.length());
            System.out.println(substring);
            if (substring.indexOf("中转") >= 0){
                expressDetail.setBegin_companytype(0);
                System.out.println("分拨网点");
            }else{
                expressDetail.setBegin_companytype(1);
                System.out.println("派件或者收件网点");
            }

            System.out.println("++++++++++++++++++++++++++++++++终止站点名称和ID+++++++++++++++++++++++++++++++");
            /**
             *终止站点
             */
            int size = jsonArray.size()-1;

            JSONObject jsonObjectEnd = jsonArray.getJSONObject(size);
            String acceptStationEnd = jsonObjectEnd.getString("AcceptStation");

            //先用split分割string
            String[] splitEnd = acceptStationEnd.split(" ");
            int x = 0;
            String[] tempEnd = new String[2];
            for (int i = 0; i < splitEnd.length-1; i++) {
                String sp = splitEnd[i];
                //如果0的索引位置是'['那就开始打印
                if (sp.indexOf("[") == 0) {
                    String subStringEnd = sp.substring(sp.indexOf("[") + 1, sp.indexOf("]"));
                    tempEnd[x] = subStringEnd;
                    System.out.println("temp[" + x + "]的值: "+ tempEnd[x]);
                    expressDetail.setEnd_companyname(tempEnd[1]);
                    x++;
                    if(x == 2)break;
                }
            }
            System.out.println("终止公司名称是： " + expressDetail.getEnd_companyname());
            //TODO 根据公司名字到网点表找id,写入数据库


            System.out.println("++++++++++++++++++++++++++++++++终止站点的类型+++++++++++++++++++++++++++++++");
            //终止站点的类型
            String nameEnd = expressDetail.getEnd_companyname();
            String substringEnd = nameEnd.substring(nameEnd.length() - 4, nameEnd.length());
            System.out.println(substringEnd);
            if (substringEnd.indexOf("中转") >= 0){
                expressDetail.setEnd_companytype(0);
                System.out.println("分拨网点");
            }else{
                expressDetail.setEnd_companytype(1);
                System.out.println("派件或者收件网点");
            }


            //是否最末条记录
            // TODO




            System.out.println("++++++++++++++++++++++++++++++++++物流明细类型+++++++++++++++++++++++++++++++++");
            //物流明细类型
            // state	快递单当前的状态 ：　
            //0：在途，即货物处于运输过程中；
            //1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息；
            //2：疑难，货物寄送过程出了问题；
            //3：签收，收件人已签收；
            //4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收；
            //5：派件，即快递正在进行同城派件；
            //6：退回，货物正处于退回发件人的途中；
            //该状态还在不断完善中，若您有更多的参数需求，欢迎发邮件至 kuaidi@kingdee.com 提出。
            String state = json.getString("State");
            System.out.println("物流的状态是: " + state);
            expressDetail.setExpress_detailtype(Integer.parseInt(state));
            if(state.equals("0")){
                System.out.println("在途");
            }else if(state.equals("1")){
                System.out.println("揽件");
            }else if(state.equals("2")){
                System.out.println("疑难");
            }else if(state.equals("3")){
                System.out.println("签收");
            }else if(state.equals("4")){
                System.out.println("退签");
            }else if(state.equals("5")){
                System.out.println("派件");
            }else if(state.equals("6")){
                System.out.println("退回");
            }


            System.out.println("++++++++++++++++++++++++++++++++++最末物流信息时间+++++++++++++++++++++++++++++++++");
            //最末物流信息时间

            JSONObject jsonObjectLast = jsonArray.getJSONObject(size);
            String acceptTime = jsonObjectLast.getString("AcceptTime");
            System.out.println("最末物流信息时间： " + acceptTime);

            System.out.println(expressDetail.toString());
        }
    }
