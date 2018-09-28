package com.ifrabbit.nk.express.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;

import java.io.Serializable;
import java.sql.Timestamp;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;
import static org.springframework.data.repository.query.parser.Part.Type.NOT_CONTAINING;
import static org.springframework.data.repository.query.parser.Part.Type.SIMPLE_PROPERTY;


@Entity(table = "tapp_tabdealinfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("new")
public class Dealinfo implements Serializable {
  @Id
  @Column(name = "appdeal_id")
  @Condition
  private Long id;//ID
  @Condition
  private Long appdealTabid;//tableInfo表里的主键ID
  @Condition
  private Integer appdealProblemtype;//问题类型
  @Condition
  private Integer appdealDealtype;//处理类型
  @Condition
  @Column(name="appdeal_state")
  private Integer appdealDealstate;
  @Condition
  private String appdealDealername;//表单的处理人
  @Condition
  private Integer appdealResult;//表单的处理结果,操作状态值  (电话:电话结果)
  private String appdealResulttext;//表单的处理结果记录，（同意，不同意） (电话结果详情: 电话的UUID 如果是网点还有 网点电话线路)
  @Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyCreateDate") })
  private Timestamp appdealDealcreatedate;//表单的生成时间
  private Timestamp appdealDealenddate;//表单的处理时间
  private  Long appdealUptabid;//父表单的tableInfo主键ID
  private  Long appdealDealerid;//表单的处理人id
  private String appdealDealname;//表单处理类型的名称
  @Condition
  private Long appdealRecorderid;
  @Condition
  private String appdealContent;//表单的处理意见
  private Long appdealHistorytabid;//历史关联表单号,在表单修改产生新表单号的时候，记录与新表单相关联的历史表单号（原表单）供追述
  private String appdealTableno;//表单编号,其内容组成是表单头部信息+年月日时间+表单ID
  private String appdealAudiourl;//音频路径,当前操作的语音文件的路径
  private String appdealMemo;//备注
  private String appdealRecord;//记录
  private String appdealVarparama;//破损拨打收件人结果占用 例"21222"
  @Condition
  private String appdealVarparamb;//用于判断是否需要流程复制
  @Condition
  private String appdealVarparamc;//证明图片一
  @Condition
  private String appdealVarparamd;//证明图片二
  @Condition
  private String appdealVarparame;//证明图片三
  private Long appdealIntparama;
  private String appdealIntparamb;//内物详情
  private String appdealIntparamc;//快件价值
  private String appdealIntparamd;//发件日期
  private Long appdealIntparame;
  private Float appdealFloatparama;//用于记录查询物流更新次数
  private Float appdealFloatparamb;
  private Float appdealFloatparamc;
  private Float appdealFloatparamd;
  private Float appdealFloatparame;
  @OneToMany
  @JoinColumn(name = "problemparts_id", referencedColumnName = "appdeal_tabid")
  private Problem problem;
  @Condition
  private Long appdealProblemid;//问题件表主键ID
  @Condition
  private Long appdealTaskid;//工作流流程环节ID
  private String appdealTabletypename;//表单类型名称,工作流业务模型的名称(processID对应的名称)
  private Integer appdealTabletype;//表单类型,工作流业务模型的ID(process ID)
  private Timestamp appdealDate;//表单处理时间
  /**
   * 其它属性. 采用JSON格式： [{name:""},{key:val}]
   */
 /* private String extattr;*/


//
}
