package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;

import javax.persistence.criteria.CriteriaBuilder;


/**
 * @author lishaomiao
 * @date 2018/6/1 11:05
 */
@Data
@NoArgsConstructor
@Entity(table = "tmanage_balance")
public class TmanageBalance {

    /**
     * ID
     */
    @Id
    @Condition
    @Column(name = "balance_id")
    private Long id;

    /**
     * 表单ID 与表单信息表TApp_TableInfo中的TableID相对应
     */
    private Integer balance_tabid;

    /**
     * 网点ID
     */
    private Integer balance_companyid;

    /**
     * 网点名称
     */
    private String balance_companyname;

    /**
     * 网点编码
     */
    @Condition
    private String balance_companycode;

    /**
     * 收款日期
     */
    private String balance_date;

    /**
     * 本次收款金额
     */
    private Integer balance_amount;

    /**
     * 应收总金额  取自企业信息表Company_Own
     */
    @Condition
    private Integer balance_originalown;

    /**
     * 已收款总金额 未收款之前的收款总金额，取自企业信息表Company_Paid
     */
    @Condition
    private Integer balance_originalsum;

    /**
     * 剩余应收金额  =应收总金额-已收款总金额-本次收款金额
     */
    @Condition
    private Integer balance_own;
    /**
     * 剩余信用额  =初始信用额-剩余应收金额。
     * 若初始信用额为空值，则意味着无须判断剩余信用额度
     * 则剩余信用额也为空值。
     */
    @Condition
    private Integer balance_credit;

    /**
     * 记录状态 1=有效，0=失效
     */
    @Condition
    private Integer balance_state;

    /**
     * 登录人姓名
     */
    @Condition
    private String balance_name;

    /**
     * 企业信用额
     */
    @Condition
    private Integer balance_creditamount;

    /**
     * 当前应收金额
     */
    @Condition
    private Integer balance_currentown;

    /**
     * 收款人姓名
     */
    @Condition
    private String balance_payee;

    /**
     * 备注
     */
    @Condition
    private String balance_memo;

}