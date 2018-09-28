package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.Entity;

/**
 * @author lishaomiao
 * @date 2018/6/1 11:05
 */
@Data
@NoArgsConstructor
@Entity(table = "ex_info_network")
public class NetWorkBalanceDTO extends NetWorkBalance {
    private String fuzzyId;
    private String fuzzyName;
    private String fuzzyPhone;
    private String fuzzySate;
    private String fuzzyCreateDate;
    private String fuzzyChangeDate;
}
