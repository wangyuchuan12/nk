package com.ifrabbit.nk.flow.process.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VarsVo {
    @JsonProperty("U_vendor")
    private String vendor;
    @JsonProperty("U_qianshou")
    private String qianshou;
    @JsonProperty("U_return")
    private String zReturn;
    @JsonProperty("U_items")
    private String items;
    @JsonProperty("U_outside")
    private String outside;
    @JsonProperty("U_inside")
    private String inside;
    @JsonProperty("U_date")
    private String date;
    @JsonProperty("U_selflf")
    private String selflf;
    @JsonProperty("U_line")
    private String line;
    @JsonProperty("U_message")
    private String message;
    @JsonProperty("U_type")
    private String type;
    @JsonProperty("U_name")
    private String name;

    @JsonProperty("S_LOCAL")
    private String local;

    @JsonProperty("S_REMOTE")
    private String remote;

    @JsonProperty("U_status")
    private String status;

    @JsonProperty("U_number")
    private String number;

    @JsonProperty("S_ORGID")
    private String orgid;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getQianshou() {
        return qianshou;
    }

    public void setQianshou(String qianshou) {
        this.qianshou = qianshou;
    }

    public String getzReturn() {
        return zReturn;
    }

    public void setzReturn(String zReturn) {
        this.zReturn = zReturn;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getOutside() {
        return outside;
    }

    public void setOutside(String outside) {
        this.outside = outside;
    }

    public String getInside() {
        return inside;
    }

    public void setInside(String inside) {
        this.inside = inside;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSelflf() {
        return selflf;
    }

    public void setSelflf(String selflf) {
        this.selflf = selflf;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }
}
