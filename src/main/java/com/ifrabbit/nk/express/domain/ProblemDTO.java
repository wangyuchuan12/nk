package com.ifrabbit.nk.express.domain;

import com.ifrabbit.nk.express.utils.TimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.Entity;

import java.text.ParseException;

/**
 * @author lishaomiao
 * @date 2018/3/14 11:05
 */
@Data
@NoArgsConstructor
@Entity(table = "tapp_problemparts")
public class ProblemDTO extends Problem{
    private String fuzzyName;
    private String fuzzyPhone;
    private String fuzzyStartTime;
    private String fuzzyEndTime;
    private Long fuzzyExpressNum;
    private Integer fuzzyType;
    private String fuzzyReciverName;
    private String fuzzyCreateDate;
    private Integer fuzzyTaskType;


    public void setFuzzyStartTime(String fuzzyStartTime) throws ParseException {
        this.fuzzyStartTime =  TimeUtil.timePare3(fuzzyStartTime);
    }

    public void setFuzzyEndTime(String fuzzyEndTime) throws ParseException {
        this.fuzzyEndTime = TimeUtil.timePare4(fuzzyEndTime);
    }
}
