package com.ifrabbit.nk.mq.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/7/24
 * Time:14:27
 */

/**
 *用后接受后台查询出符合轮询条件的
 */
@NoArgsConstructor
@Data
@ToString
public class Contain implements Serializable{
    private Long dealId;
    private Long taskId;
    private Long businessId;
    private Integer dealType;
    private String nodeName;
    private Integer problemType;
    private String expressNumber;
    private String insideItem;
    private Integer nodeCallTimes;
    private Integer uncollectedDays = 0;
    private String problempartsReceivephone;
    private String problempartsReceivename;
    private String problempartsSendphone;
    private String problempartsSendname;
    private Integer problempartsCallCompanyCount = 0;
    private String assignee;
    private String processInstanceId;
    private String problemparts_obtainstate;
    private String problemparts_dutystate;
    private String problemparts_callsendercount;
}
