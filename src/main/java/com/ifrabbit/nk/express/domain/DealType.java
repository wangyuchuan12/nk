package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;
import static org.springframework.data.repository.query.parser.Part.Type.SIMPLE_PROPERTY;
import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/5
 * Time:11:28
 */
@NoArgsConstructor
@Entity(table = "tbase_dealtype")
@Data
public class DealType {
    public static final Integer VALID_STATE = 1;
    @Id
    @Column(name = "deal_id")
    private Long id;//自增主键
    @Condition
    @Column(name = "deal_tabtypeid")
    private Long dealTabTypeId;//Process_ID业务模型ID
    @Column(name = "deal_tabname")
    private String dealTabname;
    @Column(name = "deal_type")
    private String dealType;//处理类型代码,类似于111,112等
    @Column(name = "Deal_taskno")
    private Integer dealTaskNo;//工作流环节编码
    @Column(name = "deal_state")
    private Integer dealState;//记录状态.,state=9表示记录失效。删除的时候并不删除记录，而是将记录状态值设为失效。失效的记录将不会在界面上显示出来。系统缺省状态State=1，表示记录有效
    @Column(name = "deal_url")
    private String dealUrl;//程序链接路径
    @Column(name = "deal_joburl")
    private String dealJobUrl;//待办路径
    @Condition
    @Column(name = "deal_dealname")
    //@Conditions(@Condition)
    private String dealDealName;//处理类型的名称,表单的处理行为类型包括如制表、检验、确认、更改、评审、审核、审批等
    @Column(name = "deal_memo")
    private String dealMemo;//说明

    private String deal_varparama;//字符备用字段
    private String deal_varparamb;//字符备用字段
    private String deal_varparamc;//字符备用字段
    private String deal_varparamd;//字符备用字段
    private String deal_varparame;//字符备用字段
    private Long deal_intparama;//数字备用字段
    private Long deal_intparamb;//数字备用字段
    private Long deal_intparamc;//数字备用字段
    private Long deal_intparamd;//数字备用字段
    private Long deal_intparame;//数字备用字段
    private Float deal_floatparama;//浮点备用字段
    private Float deal_floatparamb;//浮点备用字段
    private Float deal_floatparamc;//浮点备用字段
    private Float deal_floatparamd;//浮点备用字段
    private Float deal_floatparame;//浮点备用字段
}
