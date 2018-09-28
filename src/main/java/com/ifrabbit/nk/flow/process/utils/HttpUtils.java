package com.ifrabbit.nk.flow.process.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: WangYan
 * @Date: 2018/5/4 16:54
 * @Description:
 */


public class HttpUtils {
    protected final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private final static String url = "https://open.duyansoft.com/api/v2/call/campaign/auto?apikey=bBjhoF3DdLWYm5buafF0Fi4Gv5a0aGtA&name=008&site_id=";
    private final static String urll = "https://open.duyansoft.com/api/v1/call/campaign/";
    //第一次请求 创建电话任务
    public static Map post(String phone,String name,String type,String siteid,String processInstanceId,String number) {
        String body = cons(phone,name,type,processInstanceId,number);
        try {
            URL url = consURL(siteid);
            trustAllHosts();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(body);
            out.flush();
            out.close();
            int code = connection.getResponseCode();
            InputStream is = null;
            if (code == 200) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }
            // TODO: 2018/7/19  length获取不到
            // 读取响应
           // int length = (int) connection.getContentLength();// 获取长度
          //  if (length != -1) {
                byte[] data = new byte[999];
                byte[] temp = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                String result = new String(data, "UTF-8"); // utf-8编码
                //对结果进行解析并拿到最终结果返回
                Map map = booleanStatus(result);
                return map;
           // }
        } catch (IOException e) {
            logger.error("Exception occur when send http post request!", e);
        }
        return null;
    }
    //构建创建电话任务所要发送的URL
    private static URL consURL(String siteid){
        Date date = new Date();
        long time = date.getTime() + 10000;
        try {
            URL ur = new URL(url + siteid+"&start_time=" + time);
            return ur;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //构建创建电话任务所需发送的dody
    private static String cons(String phone,String name,String type,String vendor,String number) {
//     String body = "[{\"U_phone\": \"15757165157\",\"U_name\": \"张三\",\"U_vendor\": \"度言\"}]"
       if (null == name){
           name = "快送哥";
       }
       if (null == type ){
           type ="未用上";
       }else{
           switch (type){
               case "1" :
                   type= "签收未收到";
                    break;
               case "2" :
                    type = "破损";
                    break;
               case "3" :
                   type = "改地址";
                   break;
               case "4" :
                   type = "退回";
                   break;
               case "5" :
                   type ="催单";
                   break;
           }
       }
        JSONObject body = new JSONObject();
        body.put("U_phone",phone);
        body.put("U_linkertel",phone);
        body.put("U_name",name);
        body.put("U_type",type);
        body.put("U_vendor",vendor);
        body.put("U_number",number);
        JSONArray arr = new JSONArray();
        arr.add(body);
        return arr.toJSONString();
    }
    //对第一次请求的结果进行解析并发送第二次请求 对客户返回结果处理
    private static Map booleanStatus(String result) {
        Map json = JSONObject.parseObject(result, Map.class);
        Object status = json.get("status");
        if (status.toString().equals("1")) {
            Object id = JSONObject.parseObject(json.get("data").toString()).get("id");
            //再次请求 并进行判断返回信息
            Map map = middle(urll+id+"/item?apikey=bBjhoF3DdLWYm5buafF0Fi4Gv5a0aGtA&page_num=1&page_size=4");
            return  map;
        }
        return null;
    }
    private static Map middle(String uu){
        while (true){
            String message = getRequest(uu);
            logger.info("请求路径"+uu);
            String uuid = "";
           try{
                uuid = booleanMessage(message);
           }catch (NullPointerException e){
               logger.info("这通电话没有找到UUID+请核实+++++");
               throw e;
           }
            Map map  = getTag(message);
            if (null!= map.get("outcome")){
                map.put("call_uuid",uuid);
                return  map;
            }
            try{
                Thread.sleep(30000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    // 获取通话详情列表
    private static String getRequest(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            logger.info("======================发送的URL是：" + url);
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                logger.info("=========" + key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("============发送GET请求出现异常=============" + e.getMessage());
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                logger.info("============finally块来关闭输入流出现异常=============" + e2.getMessage());
                e2.printStackTrace();
            }
        }
        return result;
    }
    //返回处理客户信息tag
    private static Map  getTag(String message) {
        Map map = new HashMap();
        JSONObject obj = JSONObject.parseObject(message);
        Object status = obj.get("status");
        if (status.toString().equals("1")) {
            JSONObject data = (JSONObject) obj.get("data");
            JSONArray campaigns = data.getJSONArray("campaigns");
            JSONObject result = (JSONObject) campaigns.get(0);
            String outcome = (String) result.get("outcome");
            map.put("outcome",outcome);
            return map;
        }
        return null;
    }

    //信任证书
    public static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                logger.info("skyapp", "checkClientTrusted");
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                logger.info("skyapp", "checkServerTrusted");
            }
        }};
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    拿到uuid
    private static String booleanMessage(String message) {
        JSONObject obj = JSONObject.parseObject(message);
        Object status = obj.get("status");
        if (status.toString().equals("1")) {
            JSONObject data = (JSONObject) obj.get("data");
            logger.info(data.toString());
            JSONArray campaigns = data.getJSONArray("campaigns");
            logger.info(campaigns.toString());
            JSONObject getUUID = (JSONObject) campaigns.get(0);
            logger.info(getUUID.toString());
            String callUuid = (String) getUUID.get("call_uuid");
            return callUuid;
        }
        return null;
    }
    public static  String sendPhone(String url,String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + param;
            logger.info("======================发送的URL是："+urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                logger.info("========="+key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("============发送GET请求出现异常=============" + e.getMessage());
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                logger.info("============finally块来关闭输入流出现异常=============" + e2.getMessage());
                e2.printStackTrace();
            }
        }
        return result;
    }

//    //获取转译content
//    public static String pp(String url,String uuid) {
//        try {
//            URL uu = new URL(url);
//            trustAllHosts();
//            HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            connection.setUseCaches(false);
//            connection.setInstanceFollowRedirects(true);
//            connection.setRequestMethod("POST"); // 设置请求方式
//            connection.setRequestProperty("Accept", "application/json, text/plain, */*"); // 设置接收数据的格式
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); // 设置发送数据的格式
//            connection.connect();
//            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
//            out.append(bodyy+"\""+uuid+"\"}]");
//            out.flush();
//            out.close();
//            int code = connection.getResponseCode();
//            InputStream is = null;
//            if (code == 200) {
//                is = connection.getInputStream();
//            } else {
//                is = connection.getErrorStream();
//            }
//            // 读取响应
//            int length = (int) connection.getContentLength();// 获取长度
//            if (length != -1) {
//                byte[] data = new byte[length];
//                byte[] temp = new byte[512];
//                int readLen = 0;
//                int destPos = 0;
//                while ((readLen = is.read(temp)) > 0) {
//                    System.arraycopy(temp, 0, data, destPos, readLen);
//                    destPos += readLen;
//                }
//                String result = new String(data, "UTF-8"); // utf-8编码
//                JSONObject content = parseResult(result);
//                if (null!=content){
//                    return content.toJSONString();
//                }
//                return null;
//            }
//        } catch (IOException e) {
//            logger.error("Exception occur when send http post request!", e);
//        }
//        return "error";
//    }
//    private static JSONObject parseResult(String finalResult) {
//        JSONObject object = JSONObject.parseObject(finalResult);
//        if ("1".equals(object.get("status"))) {
//            JSONObject data = JSONObject.parseObject((String) object.get("data"));
//            String status = (String) data.get("status");
//            if ("DONE".equals(status)) {
//                JSONObject content = JSONObject.parseObject(data.get("content").toString());
//                logger.info(content.toString());
//                //==
//                return content;
//            }
//            return null;
//        }
//        return null;
//    }


}
