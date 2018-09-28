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
@Entity(table = "tmanage_balance")
public class TmanageBalanceDTO extends TmanageBalance{
    private String fuzzyName;
    private String fuzzyPhone;

}
