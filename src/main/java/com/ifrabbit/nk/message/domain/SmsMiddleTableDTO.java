package com.ifrabbit.nk.message.domain;

import com.ifrabbit.nk.message.domain.SmsMiddleTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.domains.LongId;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/7/4
 * Time:18:05
 */
@Data
@NoArgsConstructor
@Entity(table = "SMS_middle_table")
public class SmsMiddleTableDTO extends SmsMiddleTable{
    private String fuzzyname;

    private String fuzzynumber;
}
