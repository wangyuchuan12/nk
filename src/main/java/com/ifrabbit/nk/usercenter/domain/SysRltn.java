package com.ifrabbit.nk.usercenter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.*;
import java.io.Serializable;
import static org.springframework.data.repository.query.parser.Part.Type.SIMPLE_PROPERTY;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("new")
@Entity(table = "tbase_sysrltn")
public class SysRltn implements Serializable {


    @Id
    @Column(name = "sysrltn_id")
    private Long id;   //id

    @Column(name = "sysrltn_type")
    private Integer sysRLtnType; //关联类型,1=角色与人员关联表,3表示系统参数与电话参数的关系,sysrltn_mainid此时为答案的ID,sysrltn_assistid为问题的ID

    @Column(name = "sysrltn_mainid")
    private Long sysRLtnMainId;  //主数据

    @Column(name = "sysrltn_assistid")
    private Long sysRLtnAssistId;  //关联数据

    @Column(name = "sysrltn_state")
    private Integer sysRLtnState;  //状态,默认=1，有效。0=失效

    @Column(name = "sysrltn_memo")
    private String sysRLtnMemo;  //备注

    @Column(name = "sysrltn_varparama")
    private String sysRLVarParaMa;//时间间隔
    @Column(name = "sysrltn_varparamb")
    private String sysRLVarParaMb;//任务名称
    @Column(name = "sysrltn_varparamc")
    @Conditions({ @Condition, @Condition(type = SIMPLE_PROPERTY, properties = "fuzzyName") })
    private String sysRLVarParaMc;//电话问题
    @Column(name = "sysrltn_varparamd")
    @Conditions({ @Condition, @Condition(type = SIMPLE_PROPERTY, properties = "fuzzyAnswer") })
    private String sysRLVarParaMd;//答案
    @Column(name = "sysrltn_varparame")
    private String sysRLVarParaMe;//IVR
    private String sysrltn_intparama;
    private Long sysrltn_intparamb;
    private Integer sysrltn_intparamc;
    private Integer sysrltn_intparamd;
    private Integer sysrltn_intparame;
    private Float sysrltn_floatparama;
    private Float sysrltn_floatparamb;
    private Float sysrltn_floatparamc;
    private Float sysrltn_floatparamd;
    private Float sysrltn_floatparame;
    @Transient
    private String[] answer;



}
