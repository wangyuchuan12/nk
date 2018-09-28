package com.ifrabbit.nk.express.domain;

import com.ifrabbit.nk.express.utils.TimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;

@Data@NoArgsConstructor
public class DealinfoDTO extends Dealinfo {
    private String fuzzyCreateDate;
    public void setFuzzyCreateDate(String fuzzyCreateDate) throws ParseException {
        this.fuzzyCreateDate = TimeUtil.timePare2(fuzzyCreateDate);//日期处理
    }
}
