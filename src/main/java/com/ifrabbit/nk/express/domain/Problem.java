package com.ifrabbit.nk.express.domain;

import com.bstek.uflo.model.task.Task;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.*;

import java.io.Serializable;
import java.util.Date;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;


/**
 * @author lishaomiao
 * @date 2018/3/14 11:05
 */
@Data
@NoArgsConstructor
@Entity(table = "tapp_problemparts")
public class Problem implements Serializable {
    /**
     * id
     */
    @Id
    @Condition
    @Column(name="problemparts_id")
    private Long id;
    /**
     * 用户id
     */
    @Condition
    private Integer problemparts_staffid;
    /**
     * 表单状态 10=待确认，20=审核不通过，1=流程正常结束，4=作废
     */
    @Condition
    private Integer problemparts_state;
    /**
     * 运单号
     */
    @Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyExpressNum")})
    private Long problemparts_expressnumber;
    /**
     * 问题类型
     */
    @Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyType")})
    private Integer problemparts_type;
    /**
     * 内物
     */
    @Condition
    private String problemparts_insideitem;
    /**
     *收件人名字
     */
    @Condition
    private String problemparts_receivename;
    /**
     * 收件人电话
     */
    @Condition
    private String problemparts_receivephone;
    /**
     * 收件人地址
     */
    @Condition
    private String problemparts_receiveaddress;
    /**
     * 发件人名字
     */
    @Condition
    private String problemparts_sendname;
    /**
     * 发件人电话
     */
    @Condition
    private String problemparts_sendphone;
    /**
     * 发件人地址
     */
    @Condition
    private String problemparts_sendaddress;
    /**
     * 投诉人姓名
     */
    @Condition
    private String problemparts_submittername;
    /**
     * 投诉人电话
     */
    @Condition
    private String problemparts_submitterphone;
    /**
     * 新收件人姓名
     */
    @Condition
    private String problemparts_newreceivename;
    /**
     *新收件人电话
     */
    @Condition
    private String problemparts_newreceivephone;
    /**
     * 新收件人地址
     */
    @Condition
    private String problemparts_newreceiveaddress;
    /**
     * 派件网点ID
     */
    @Condition
    private String problemparts_companyid;
    /**
     * 内物图片路径
     */
    @Condition
    private String problemparts_insideitempicurl;
    /**
     * 外包装图片路径
     */
    @Condition
    private String problemparts_outsideitempicurl;
    /**
     * 退回运单号
     */
    @Condition
    private Long problemparts_returnexpressnumber;
    /**
     * 物流状态：无物流=10，已签收=20，派件中=30，从离开出港网点到最后一站中心发出前的所有状态=40，从最后一站中心到派送前的所有状态=50
     */
    @Condition
    private Integer problemparts_expresstype;
    /**
     * 物流是否更新:任意物流节点，是否正常更新10=正常，20=异常
     */
    @Condition
    private Integer problemparts_expressupdatestate;
    /**
     * 是否免责:10=免责，20=非免责
     */
    @Condition
    private Integer problemparts_dutystate;
    /**
     * 是否收到货:10=收到，20=未收到
     */
    @Condition
    private Integer problemparts_obtainstate;
    /**
     * 是否退回:10=继续退回，20=取消退回
     */
    @Condition
    private Integer problemparts_returnstate;
    /**
     * 是否改地址:10=继续改地址，20=取消改地址
     */
    @Condition
    private Integer problemparts_alteraddr;
    /**
     * 地址是否改出:10=地址已改出，20=地址未改出
     */
    @Condition
    private Integer problemparts_alteraddrstate;

    /**
     * 拨打发件人电话次数:默认值为0,逐1累加
     */
    @Condition
    private Integer problemparts_callsendercount;
    /**
     * 拨打收件人电话次数
     */
    @Condition
    private Integer problemparts_callrecipientscount;
    /**
     * 拨打网点电话次数：默认值为0,逐1累加。网点可以是派件点，停滞点或者上级网点
     */
    @Condition
    private Integer problemparts_callcompanycount;
    /**
     * 未收到天数:默认值为0,逐1累加
     */
    @Condition
    private Integer problemparts_uncollecteddays;
    /**
     * 是否签收:10=签收，20=未签收
     */
    @Condition
    private Integer problemparts_receivestate;
    /**
     * 是否遗失:10=遗失，20=未遗失
     */
    @Condition
    private Integer problemparts_loststate;
    /**
     * 签收日期:催单流程获取的日期
     */
    @Condition
    private Date problemparts_receivedate;
    /**
     * 催单流程分支:10=继续系统流程等待，20=智能语音催中心，30=上报遗失
     */
    @Condition
    private Integer problemparts_reminderstate;
    /**
     * 工单来源:10=系统，20=中天
     */
    @Condition
    private Integer problemparts_source;
    /**
     * 操作员ID:上报人ID
     */
    @Condition
    private Integer problemparts_operatorid;
    /**
     * 创建时间
     */
    @Condition
    private String problemparts_createdate;
    /**
     * 修改时间
     */
    @Condition
    private String problemparts_changedate;
    /**
     * 备注
     */
    @Condition
    private String problemparts_memo;//此字段用在前端jobList owner字段上
    @Condition
    private String problemparts_staffname;//当前客户
    @Condition
    private String problempartsVarparama;//此字段用在 前端jobList 是否遗失 的说明上了
    @Condition
    private String problempartsVarparamb;//此字段用在 前端jobList 遗失操作结果回复 的说明上了
    @Condition
    private String problempartsVarparamc;//此字段用在 前端jobList 获取taskId上
    @Condition
    private Integer problempartsIntparama;//此字段用在 前端jobList 是否结束催单流程上 10结束 20继续 30忽略该操作
    @Condition
    private Integer problempartsIntparamb;//此字段用在 前端jobList 是否延误10延误 20不延误
    @Condition
    private Integer problempartsIntparamc;//此字段用在 前端joblist 是否申请仲裁 10 申请 20 否
    @Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyTaskType")})
    private Integer problemparts_tasktype; //任务是否进行  0正在进行,1当前任务结束
    private Float problempartsFloatparama;//备用字段一
    private Float problempartsFloatparamb;//备用字段二
    private Float problempartsFloatparamc;//备用字段三
    private Long processInstanceId;
    private String problemparts_currenttask; //当前环节
    private String problempartsVarparamd; //证明图片一
    private String problempartsVarparame; //证明图片二
    private String problempartsVarparamf; //证明图片三
    private String problemparts_details ; //内物详情
    private String problemparts_expressvalue; //快件价值
    private String problemparts_expressdate; //发件日期
    @Transient
    private Task task;
    private String problemparts_finish;//问题件在什么情况下结束的消息语
}
