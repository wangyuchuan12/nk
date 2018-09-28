package com.ifrabbit.nk.message.domain;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/6/21
 * Time:11:18
 */
public class ParamVo {
    private String action;  //功能操作标识 ，SMSArrived
    private String smsType;  //短信类型，0：上行短信，1：手机接收状态报告
    private String recvTime;  //收到上行短信/短信送达手机时间，格式：YYYYMMDDHHMMSS
    private String apiVersion;//REST API版本号，格式：YYYY-MM-DD
    private String fromNum;   //发送/接收短信的手机号码，以13等开头的11位号码
    private String content;   //短信内容，当smsType=1时，该字段的值为短信ID，即下行短信请求响应的smsMessageSid。当smsType=0时，该字段值手机回复的短信内容，utf8格式
    private String appendCode; //短信扩展码，由数字组成，对应不同的下行短信签名，smsType=0时有效。以此区分不同的下行短信签名
    private String subAppend;  //自定义短信扩展码，对应下行时传递的subAppend，smsType=0时有效
    private String status;    //短信到达状态, 0为接收成功，其它值为接收失败，smsType=1时有效
    private String dateSent;  //短信发送时间，格式：YYYYMMDDHHMMSS，smsType=1时有效
    private String deliverCode; //到达状态描述，即运营商网关状态码。当status非0且smsType=1时有效
    public ParamVo(){

    }
    public ParamVo(String action,String smsType,String recvTime,String apiVersion,String fromNum,String content,String appendCode,String subAppend,String status,String dateSent,String deliverCode){
        this.action=action;
        this.smsType=smsType;
        this.recvTime=recvTime;
        this.apiVersion=apiVersion;
        this.fromNum=fromNum;
        this.content=content;
        this.appendCode=appendCode;
        this.subAppend=subAppend;
        this.status=status;
        this.dateSent=dateSent;
        this.deliverCode=deliverCode;
    }
    /**
     * action.
     *
     * @return  the action
     * @since   JDK 1.6
     */
    public String getAction() {
        return action;
    }
    /**
     * smsType.
     *
     * @return  the smsType
     * @since   JDK 1.6
     */
    public String getSmsType() {
        return smsType;
    }
    /**
     * recvTime.
     *
     * @return  the recvTime
     * @since   JDK 1.6
     */
    public String getRecvTime() {
        return recvTime;
    }
    /**
     * apiVersion.
     *
     * @return  the apiVersion
     * @since   JDK 1.6
     */
    public String getApiVersion() {
        return apiVersion;
    }
    /**
     * fromNum.
     *
     * @return  the fromNum
     * @since   JDK 1.6
     */
    public String getFromNum() {
        return fromNum;
    }
    /**
     * content.
     *
     * @return  the content
     * @since   JDK 1.6
     */
    public String getContent() {
        return content;
    }
    /**
     * appendCode.
     *
     * @return  the appendCode
     * @since   JDK 1.6
     */
    public String getAppendCode() {
        return appendCode;
    }
    /**
     * subAppend.
     *
     * @return  the subAppend
     * @since   JDK 1.6
     */
    public String getSubAppend() {
        return subAppend;
    }
    /**
     * status.
     *
     * @return  the status
     * @since   JDK 1.6
     */
    public String getStatus() {
        return status;
    }
    /**
     * dateSent.
     *
     * @return  the dateSent
     * @since   JDK 1.6
     */
    public String getDateSent() {
        return dateSent;
    }
    /**
     * deliverCode.
     *
     * @return  the deliverCode
     * @since   JDK 1.6
     */
    public String getDeliverCode() {
        return deliverCode;
    }
    /**
     * action.
     *
     * @param   action    the action to set
     * @since   JDK 1.6
     */
    public void setAction(String action) {
        this.action = action;
    }
    /**
     * smsType.
     *
     * @param   smsType    the smsType to set
     * @since   JDK 1.6
     */
    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }
    /**
     * recvTime.
     *
     * @param   recvTime    the recvTime to set
     * @since   JDK 1.6
     */
    public void setRecvTime(String recvTime) {
        this.recvTime = recvTime;
    }
    /**
     * apiVersion.
     *
     * @param   apiVersion    the apiVersion to set
     * @since   JDK 1.6
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
    /**
     * fromNum.
     *
     * @param   fromNum    the fromNum to set
     * @since   JDK 1.6
     */
    public void setFromNum(String fromNum) {
        this.fromNum = fromNum;
    }
    /**
     * content.
     *
     * @param   content    the content to set
     * @since   JDK 1.6
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * appendCode.
     *
     * @param   appendCode    the appendCode to set
     * @since   JDK 1.6
     */
    public void setAppendCode(String appendCode) {
        this.appendCode = appendCode;
    }
    /**
     * subAppend.
     *
     * @param   subAppend    the subAppend to set
     * @since   JDK 1.6
     */
    public void setSubAppend(String subAppend) {
        this.subAppend = subAppend;
    }
    /**
     * status.
     *
     * @param   status    the status to set
     * @since   JDK 1.6
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * dateSent.
     *
     * @param   dateSent    the dateSent to set
     * @since   JDK 1.6
     */
    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }
    /**
     * deliverCode.
     *
     * @param   deliverCode    the deliverCode to set
     * @since   JDK 1.6
     */
    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode;
    }
    /**
     * TODO 简单描述该方法的实现功能（可选）.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ParamVo [action=" + action + ", smsType=" + smsType + ", recvTime=" + recvTime + ", apiVersion="
                + apiVersion + ", fromNum=" + fromNum + ", content=" + content + ", appendCode=" + appendCode
                + ", subAppend=" + subAppend + ", status=" + status + ", dateSent=" + dateSent + ", deliverCode="
                + deliverCode + "]";
    }



}