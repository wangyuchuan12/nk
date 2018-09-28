package com.ifrabbit.nk.express.domain;

import com.ifrabbit.nk.express.utils.TimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.Entity;

import java.text.ParseException;


/**
 * @Auther: WangYan
 * @Date: 2018/7/5 11:30
 * @Description:
 */
@Data
@NoArgsConstructor
public class TableInfoDTO extends  TableInfo {
    private String fuzzyCreateDate;

    public void setFuzzyCreateDate(String fuzzyCreateDate) throws ParseException {
        this.fuzzyCreateDate = TimeUtil.timePare2(fuzzyCreateDate);//日期处理
    }
}
