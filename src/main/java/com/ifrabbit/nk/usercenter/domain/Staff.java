package com.ifrabbit.nk.usercenter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.*;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static org.springframework.data.mybatis.annotations.Id.GenerationType.ASSIGNATION;
import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

@Entity(table = "tbase_staffinfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("new")
public class Staff implements Serializable {

    @Id(strategy = ASSIGNATION)
    @Column(name = "staff_id")
    private String id;

    @Conditions({@Condition, @Condition(properties = "fuzzyUsername", type = CONTAINING)})
    private String staffUsername;//用户名

    @JsonProperty(access = WRITE_ONLY)
    private String staffPassword;//密码

    @Conditions({@Condition, @Condition(properties = "fuzzyMobile", type = CONTAINING)})
    private String staffMobil;//手机号码

    @Conditions({@Condition, @Condition(properties = "fuzzyEmail", type = CONTAINING)})
    private String staffEmail;//邮箱

    /**
     * 姓名.
     */
    @Conditions({@Condition, @Condition(properties = "fuzzyName", type = CONTAINING)})
    private String staffName;


    @Condition
    private String staffCode;//人员编码

    @Condition
    private String staffIdcard;//身份证号码

    @Condition
    private Integer staffUsertype;//用户类型,系统默认有四种用户0=系统管理员；1=客服人员；2=机器人；3=外部用户；10000=测试用户

    @Condition
    private Long staffCompanyid;//企业ID,关联企业表中的主键

    @Condition
    private String staffCreatedate;//创建日期

    @Condition
    private Integer staffJobstate;//人员状态,1=在职，2=休息

    @Condition
    private Integer staffState = 1;//记录状态,1=有效，9=失效。系统默认值=1

    @Condition
    private String staffMemo;//备注

    @Condition
    private String staffNameId;//客户名称（登录的账号）

    @Condition
    private String staffInsideItem;//内物

    @Condition
    private String staffSendName;//发件人

    @Condition
    private String StaffSendPhone;//发件人电话

    @Condition
    private String StaffSendAdd;//发件人地址

    private String staffVarparama;//字符备用字段公司名

    private String staffVarparamb;//字符备用字段

    private String staffVarparamc;//字符备用字段

    private String staffVarparamd;//字符备用字段

    private String staffVarparame;//字符备用字段

    private Integer staffIntparama;//数字备用字段  用于判断是否执行deleteRoleUserByUserId

    private Integer staffIntparamb;//数字备用字段

    private Integer staffIntparamc;//数字备用字段

    private Integer staffIntparamd;//数字备用字段

    private Integer staffIntparame;//数字备用字段

    private Float staffFloatparama;//浮点备用字段

    private Float staffFloatparamb;//浮点备用字段

    private Float staffFloatparamc;//浮点备用字段

    private Float staffFloatparamd;//浮点备用字段

    private Float staffFloatparame;//浮点备用字段


    @Condition
    @Transient
    private List<Role> roles;

    @Transient
    private List<Group> groups;

    @Transient
    private String[] actions;

    public Staff(String id) {
        this.id = id;
    }

    public enum STATUS {
        normal, locked, expired,
    }
}
