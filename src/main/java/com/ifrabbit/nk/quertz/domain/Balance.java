package com.ifrabbit.nk.quertz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;
import org.springframework.data.mybatis.domains.LongId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

/**
 * @Authod: chenyu
 * @Date 2018/6/8 14:34
 * Content:
 */
@Data
@NoArgsConstructor
@Entity(table = "tmanage_balance")
public class Balance{
    @Id
    @Condition
    @Column(name="balance_id")
    private Long id;//ID
    private Integer balance_tabid;//表单ID
    private Integer balance_companyid;//网点ID
    @Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyName") })
    private String balance_companyname;//网点名称
    private String balance_companycode;//网点编码
    @Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyDate") })
    private String balance_date;//收款日期
    private BigDecimal balance_amount;//本次收款金额
    private BigDecimal balance_originalown;//应收总金额
    private BigDecimal balance_originalsum;//已收款总金额
    private String balance_payee;
    private BigDecimal balance_own;//剩余应收金额
    private BigDecimal balance_credit;//剩余信用额
    private int balance_state;//记录状态
    private String balance_memo;//备注
    private String balance_varparama;//字符备用字段
    private String balance_varparamb;//字符备用字段
    private String balance_varparamc;//字符备用字段
    private String balance_varparamd;//字符备用字段
    private String balance_varparame;//字符备用字段
    private Integer balance_intparama;//数字备用字段
    private Integer balance_intparamb;//数字备用字段
    private Integer balance_intparamc;//数字备用字段
    private Integer balance_intparamd;//数字备用字段
    private Integer balance_intparame;//数字备用字段
    private Float balance_floatparama;//浮点备用字段
    private Float balance_floatparamb;//浮点备用字段
    private Float balance_floatparamc;//浮点备用字段
    private Float balance_floatparamd;//浮点备用字段
    private Float balance_floatparame;//浮点备用字段
}
