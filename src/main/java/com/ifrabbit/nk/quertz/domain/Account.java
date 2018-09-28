package com.ifrabbit.nk.quertz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.Id;
import org.springframework.data.mybatis.domains.LongId;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Authod: chenyu
 * @Date 2018/6/8 16:11
 * Content:
 */
@Data
@NoArgsConstructor
@Entity(table = "TManage_Account")
public class Account{
    @Id
    @Condition
    @Column(name="account_id")
    private Long id;
    private Integer account_companyid;//网点ID
    private String account_companyname;//网点名称
    private String account_companycode;//网点编码
    private String acccount_username;//客服姓名
    private Integer account_userid;//客服ID
    private BigDecimal account_amount;//结存数量
    private float account_sum;//结存金额
    private int account_problemtype;//问题类别
    private int account_type;//结存类别
    private int account_period;//结存周期标志
    private Date account_date;//更新时间
    private Date account_time;//记录时间
    private String account_memo;//备注
    private String exmpart_varparama;//字符备用字段
    private String exmpart_varparamb;//字符备用字段
    private String exmpart_varparamc;//字符备用字段
    private String exmpart_varparamd;//字符备用字段
    private String exmpart_varparame;//字符备用字段
    private Integer exmpart_intparama;//数字备用字段
    private Integer exmpart_intparamb;//数字备用字段
    private Integer exmpart_intparamc;//数字备用字段
    private Integer exmpart_intparamd;//数字备用字段
    private Integer exmpart_intparame;//数字备用字段
}
