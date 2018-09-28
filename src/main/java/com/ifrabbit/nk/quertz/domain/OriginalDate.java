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
 * @Date 2018/6/8 15:22
 * Content:
 */
@Data
@NoArgsConstructor
@Entity(table = "TManage_OriginalDate")
public class OriginalDate{
    @Id
    @Condition
    @Column(name = "odate_id")
    private Long id;//ID
    private Integer odate_companyid;//网点ID
    private String odate_companycode;//网点编码
    private Integer odate_userid;//用户ID
    private String odate_username;//用户姓名
    private int odate_usertype;//用户类型
    private int odate_problemtype;//问题类型
    private BigDecimal odate_originalamount;//初始值
    private float odate_originalown;//应收总金额
    private float odate_originalsum;//已收总金额
    private Date odate_originaldate;//初始数据时间
    private int odate_datetype;//统计数据类型
    private String odate_responsor;//设置人
    private Date odate_settime;//设置时间
    private int odate_state;//数据状态
    private String odate_varparama;//字符备用字段
    private String odate_varparamb;//字符备用字段
    private String odate_varparamc;//字符备用字段
    private String odate_varparamd;//字符备用字段
    private String odate_varparame;//字符备用字段
    private Integer odate_intparama;//数字备用字段
    private Integer odate_intparamb;//数字备用字段
    private Integer odate_intparamc;//数字备用字段
    private Integer odate_intparamd;//数字备用字段
    private Integer odate_intparame;//数字备用字段
    private Float odate_floatparama;//浮点备用字段
    private Float odate_floatparamb;//浮点备用字段
    private Float odate_floatparamc;//浮点备用字段
    private Float odate_floatparamd;//浮点备用字段
    private Float odate_floatparame;//浮点备用字段

}
