package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;
import org.springframework.data.mybatis.domains.LongId;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/8/14
 * Time:14:34
 */

@Entity(table = "system_log")
@Data
@NoArgsConstructor
public class SystemLog extends LongId {
    private String createTime;
    private String content;
    @Condition@Conditions(@Condition(type = CONTAINING,properties = "fuzzyExpressNumber"))
    private String expressNumber;

}
