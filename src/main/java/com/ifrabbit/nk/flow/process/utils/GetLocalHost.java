package com.ifrabbit.nk.flow.process.utils;

import com.ifrabbit.nk.constant.Constant;

import java.net.InetAddress;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/8/30
 * Time:13:04
 */
public class GetLocalHost {

    //获取本机ip地址
    public static String getIp(){
        InetAddress ia=null;
        try {
            ia=ia.getLocalHost();
            String localip=ia.getHostAddress();
            System.out.println("本机的ip是 ："+localip);
            return localip;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
