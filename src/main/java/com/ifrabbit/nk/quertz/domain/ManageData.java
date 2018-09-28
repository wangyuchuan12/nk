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
 * @Date 2018/6/8 17:03
 * Content:
 */
@Data
@NoArgsConstructor
@Entity(table = "TManage_Data")
public class ManageData{
    @Id
    @Condition
    @Column(name = "mdata_id")
    private Long id;//ID
    private Integer mdata_companyid;//网点ID
    private String mdata_companyname;//网点名称
    private String mdata_companycode;//网点编码
    private String mdata_username;//客服姓名
    private Integer mdata_userid;//客服ID
    private Integer mdata_usertype;//用户类型
    private int mdata_problemtype;//问题类别
    private BigDecimal mdata_amount;//结存数量
    private float mdata_own;//应收款总金额
    private float mdata_sum;//收款金额
    private int mdata_type;//结存类别
    private int mdata_period;//结存周期标志
    private Date mdata_date;//更新时间
    private Date mdata_time;//记录时间
    private int mdata_state;//状态
    private String mdata_memo;//备注
    private String mdata_varparama;//字符备用字段
    private String mdata_varparamb;//字符备用字段
    private String mdata_varparamc;//字符备用字段
    private String mdata_varparamd;//字符备用字段
    private String mdata_varparame;//字符备用字段
    private Integer mdata_intparama;//数字备用字段
    private Integer mdata_intparamb;//数字备用字段
    private Integer mdata_intparamc;//数字备用字段
    private Integer mdata_intparamd;//数字备用字段
    private Integer mdata_intparame;//数字备用字段
    private Float mdata_floatparama;//浮点备用字段
    private Float mdata_floatparamb;//浮点备用字段
    private Float mdata_floatparamc;//浮点备用字段
    private Float mdata_floatparamd;//浮点备用字段
    private Float mdata_floatparame;//浮点备用字段
}
