package com.ifrabbit.nk.usercenter.domain;

import com.ifrabbit.nk.express.utils.TimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.Entity;
import java.text.ParseException;


@Entity(table = "UFLO_TASK")
@Data
@NoArgsConstructor
public class UfloTaskDTO extends UfloTask {
    private String fuzzyAssignee;
    private String fuzzyExpressNumber;
    private String fuzzyExpressType;
    private String fuzzyBusinessId;
    private String fuzzyCreateDate;
    private String fuzzyOwMer;
    public void setFuzzyCreateDate(String fuzzyCreateDate) throws ParseException {
        this.fuzzyCreateDate = TimeUtil.timePare2(fuzzyCreateDate);//日期处理
    }
}
