package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/8/14
 * Time:14:44
 */
@Data
@NoArgsConstructor
public class SystemLogDTO extends SystemLog {
    private String fuzzyExpressNumber;
}
