package com.ifrabbit.nk.message.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@Entity(table = "SMS_middle_table")
public class SmsMiddleTable extends LongId{

    private Long task_id;
    private String business_id;
    private String phone_number;
}
