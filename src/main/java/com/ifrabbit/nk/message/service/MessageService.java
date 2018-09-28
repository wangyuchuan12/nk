package com.ifrabbit.nk.message.service;


/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/6/14
 * Time:14:56
 */
public interface MessageService {
    String[] sendMessage(String[] telePhoneParams,String MessModal,String[] data);//企业
    String sendMessage(String telePhoneParams,String MessModal,String[] data);//个人
}
