package com.ifrabbit.nk.constant;

public interface Constant {

  /**
   * x-token
   */
  public static final String HEADER_TOKEN = "x-token";

  /**
   * 1签收未收到
   */
  public static final String NOT_RECEIVED_RECEIPT = "1";

  /**
   * 2破损
   */
  public static final String BE_DAMAGED = "2";

  /**
   * 3改地址
   */
  public static final String MODIFY_ADDRESS  = "3";

  /**
   * 4退回
   */
  public static final String RETURN_EXPRESS = "4";

  /**
   * 5催单
   */
  public static final String BE_REMINDER = "5";

  /**
   * 电话拒接
   */
  public static final String NO_PASS = "3";

 /**
  工单来源:10=系统
  */
  public static final int SYSTEM= 10;

  /**
   工单来源:20=中天
   */
  public static final int MID_HEAVEN= 20;

    /**
    队列名称（请本地自行修改不要提交）
     */
    public static final String EXPRESS_QUEUE_NAME= "express";//给自己设置一个唯一的物流队列名

    public static final String PHONE_QUEUE_NAME= "phone";//给自己设置一个唯一的电话队列名

    public static final String PHONERESULT_QUEUE_NAME = "result";//电话结果队列

    public static final String TASK_NO="task_no";

}
