package com.ifrabbit.nk.flow.process.model;

public class DyyuanResult {
    //正常
    public static final Integer NORMAL_STATUS = 1;
    private Integer status;
    private DuyanData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DuyanData getData() {
        return data;
    }

    public void setData(DuyanData data) {
        this.data = data;
    }
}
