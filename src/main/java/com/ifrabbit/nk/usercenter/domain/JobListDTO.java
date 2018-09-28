package com.ifrabbit.nk.usercenter.domain;

import com.ifrabbit.nk.express.utils.TimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;

@Data
@NoArgsConstructor
public class JobListDTO extends JobList{
    private String fuzzyCreateDate;
    private String fuzzyShutDownDate;
    public void setFuzzyCreateDate(String fuzzyCreateDate) throws ParseException {
        this.fuzzyCreateDate = TimeUtil.timePare3(fuzzyCreateDate);
    }
    public void setFuzzyShutDownDate(String fuzzyShutDownDate) throws ParseException {
        this.fuzzyShutDownDate =  TimeUtil.timePare3(fuzzyShutDownDate);
    }
}
