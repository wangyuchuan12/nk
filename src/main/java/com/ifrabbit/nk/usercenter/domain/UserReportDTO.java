package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: SunJiaJian
 * @Description:
 * @Date: Created in 10:51 2018/3/14
 */
@Data
@NoArgsConstructor
public class UserReportDTO extends UserReport {

    private String fuzzyName;
    private String fuzzyCode;
    private String FuzzyStaffName;
    /**
     * 根据用户Id查询公司名需要关联查询
     */
    private Long userId;

}
