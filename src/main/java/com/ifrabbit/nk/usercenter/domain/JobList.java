package com.ifrabbit.nk.usercenter.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifrabbit.nk.express.domain.Problem;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;
import org.springframework.data.mybatis.domains.LongId;
import org.springframework.data.repository.query.parser.Part;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import static org.springframework.data.repository.query.parser.Part.Type.*;

@Data
@NoArgsConstructor
@Entity(table = "tapp_joblist")
@Getter
public class JobList extends LongId implements Serializable {

    private Long joblistProblemid;//问题件主键ID

    @Condition
    private String joblistExpressnumber;//运单号

    private Long joblistTabid;//tableInfo表里的主键ID

    @Condition
    private Long joblistDealid;//dealInfo表里的主键ID

    private Integer joblistTabletype;//表单类型 工作流业务模型的名称(取自工作流)processID

    private String joblistTabletypename;//工作流业务模型的名称

    private Integer joblistDealtype;//处理类型

    private String joblistDealname;//表单的操作名字，流程环节名字

    private Long joblistUptabid;//父表单的tableInfo主键ID

    @Condition
    private Integer joblistState;//代办工作状态，0=未处理，1=已处理，2=处理中，3=异常错误终止

    private String joblistContent;//返回待办处理的状况，出现异常状况时使用

    @Conditions({@Condition,@Condition(properties = "fuzzyCreateDate",type=AFTER),@Condition(properties = "fuzzyShutDownDate",type=BEFORE)})
    private Timestamp joblistTime;//待办产生的时间

    private String joblistMemo;//备注

    private String joblistRecord;//记录

    private Integer joblistResult;//表单处理结果

    @Condition
    private String joblistOwner;//代办拥有人

    @Condition
    private Integer joblistProblemtype;//问题类型

    @Condition
    private String joblistSubmitterphone;//发件人电话

    @Condition
    @Column(name = "joblist_uflo_taskid")
    private Long joblistTaskid;//任务ID


    /**
     * 备用字段
     */
    private String joblistVarparama;
    private String joblistVarparamb;
    private String joblistVarparamc;
    private String joblistVarparamd;
    private String joblistVarparame;

    private Long joblistIntparama;
    private Long joblistIntparamb;
    private Long joblistIntparamc;
    private Long joblistIntparamd;
    private Long joblistIntparame;

    private Float joblistFloatparama;
    private Float joblistFloatparamb;
    private Float joblistFloatparamc;
    private Float joblistFloatparamd;
    private Float joblistFloatparame;

}
