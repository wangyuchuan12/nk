package com.ifrabbit.nk.flow.process.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class DuyanData {
    @JsonProperty("call_logs")
    private List<CallLogVo> callLogs;
    @JsonProperty("total_elements")
    private Integer totalElements;
    @JsonProperty("total_pages")
    private Integer totalPages;

    public List<CallLogVo> getCallLogs() {
        return callLogs;
    }

    public void setCallLogs(List<CallLogVo> callLogs) {
        this.callLogs = callLogs;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
