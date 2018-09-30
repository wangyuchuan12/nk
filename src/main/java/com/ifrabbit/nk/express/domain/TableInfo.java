package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Auther: WangYan
 * @Date: 2018/7/5 09:50
 * @Description:
 */
@Data
@NoArgsConstructor
@Entity(table = "tapp_tableinfo")
@Getter
public class TableInfo implements Serializable {
    @Id
    @Column(name = "tableinfo_id")
    private Long id;
    @Condition
    private Long tableinfo_bussinessid;
    @Condition
    private Long tableinfo_dealid;//dealInfo表里的主键ID
    @Condition
    private Integer tableinfo_problemtype;//问题件类型
    private Integer tableinfo_dealtype;//处理类型

    @Column(name="tableinfo_state")
    private Integer tableinfo_dealstate;//表单的状态，1处理中，0结束
    private String tableinfo_content;
    @Condition
    @Column(name="tableinfo_uptableid")
    private Long tableinfo_uptabid;//父表单的tableInfo主键ID
    //当前任务节点ID
    @Condition
    private Long tableinfo_dealerid;//处理人ID
    private String tableinfo_dealername;//处理人名字
    @Condition
    @Column(name="tableinfo_date")
    private Timestamp tableinfo_dealcreatedate;//表单创建时间
    private String tableinfo_dealenddate;
    @Condition
    private Integer tableinfo_result;//处理结果

    @Condition
    @Column(name="tableInfo_resulttext")
    private Integer tableInfo_resulttext;//表单处理结果的文字表达
    @Condition
    private Long tableinfo_historytabid;//历史表单的ID
    @Condition
    private Long tableinfo_groupid;
    private String tableinfo_tableno;
    private String tableinfo_memo;
    private Integer tableinfo_takeout;
    @Condition
    private Long tableinfo_recorderid;//处理人的id
    private String tableinfo_record;//记录

    private String tableinfo_varparama;
    private String tableinfo_varparamb;//价值证明图片一
    private String tableinfo_varparamc;//价值证明图片二
    private String tableinfo_varparamd;//价值证明图片三
    private String tableinfo_varparame;//内物详情
    private String tableinfo_varparamvalue; //快件价值
    private String tableinfo_varparamgdate; //发件日期
    private Long tableinfo_intparama;//判断是否打过上海总中心1是打过
    private Long tableinfo_intparamb;
    private Long tableinfo_intparamc;
    private Long tableinfo_intparamd;
    private Long tableinfo_intparame;
    private float tableinfo_floatparama;
    private float tableinfo_floatparamb;
    private float tableinfo_floatparamc;
    private float tableinfo_floatparamd;
    private float tableinfo_floatparame;
//    @Conditions({
//            @Condition(properties = "problem.problemparts_expressnumber", column = "problemparts_expressnumber")})
//    @ManyToOne
////    @JoinColumn(name = "tableinfo_bussinessid", referencedColumnName = "problemparts_id")
    @OneToMany
    @JoinColumn(name = "problemparts_id", referencedColumnName = "tableinfo_bussinessid")
    private Problem problem;
    @Condition
    private Long tableinfo_problemid;//问题件表的主键ID
    @Condition
    @Column(name="tableinfo_instanceid")
    private Long tableinfo_tabid;//工作流实例ID（instantID)

    @Column(name="tableInfo_tabletypename")
    private String tableInfo_tabletypename;//工作流业务模型的名称
    private Integer tableinfo_tabletype;//表单类型 工作流业务模型的名称(取自工作流)processID
    private String tableinfo_dealname;//流程环节名称

    @Column(name="tableinfo_dealtime")
    private Timestamp tableinfo_dealtime;
}
