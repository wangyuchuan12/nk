package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Conditions;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.Id;
import org.springframework.data.mybatis.domains.LongId;

import java.util.ArrayList;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

@Data
@NoArgsConstructor
@Entity(table = "tapp_problemparts")
public class AppProblemparts  {

	/**
	 * 表单id
	 */
	@Id(
			strategy = Id.GenerationType.AUTO
	)
	protected Long problempartsId;

	private Long problempartsStaffid;
	/**
	 * 表单状态 10=待确认，20=审核不通过，1=流程正常结束，4=作废
	 */
	@Condition
	private Integer problempartsState;
	/**
	 * 运单号
	 */
	@Condition
	private Long problempartsExpressnumber;
	/**
	 * 问题类型
	 */
	@Condition
	private Integer problempartsType;
	/**
	 * 内物
	 */
	@Condition
	private String problempartsInsideitem;
	/**
	 *收件人名字
	 */
	@Condition
	private String problempartsReceivename;
	/**
	 * 收件人电话
	 */
	@Condition
	private String problempartsReceivephone;
	/**
	 * 收件人地址
	 */
	@Condition
	private String problempartsReceiveaddress;
	/**
	 * 发件人名字
	 */
	@Condition
	private String problempartsSendname;
	/**
	 * 发件人电话
	 */
	@Condition
	private String problempartsSendphone;
	/**
	 * 发件人地址
	 */
	@Condition
	private String problempartsSendaddress;
	/**
	 * 投诉人姓名
	 */

	@Conditions({
			@Condition,
			@Condition(type = CONTAINING, properties = "fuzzyName")
	})
	private String problempartsSubmittername;
	/**
	 * 投诉人电话
	 */
	@Conditions({
			@Condition,
			@Condition(type = CONTAINING, properties = "fuzzyPhone")
	})
	private String problempartsSubmitterphone;
	/**
	 * 新收件人姓名
	 */
	@Condition
	private String problempartsNewreceivename;
	/**
	 *新收件人电话
	 */
	@Condition
	private String problempartsNewreceivephone;
	/**
	 * 新收件人地址
	 */
	@Condition
	private String problempartsNewreceiveaddress;
	/**
	 * 派件网点ID
	 */
	@Condition
	private String problempartsCompanyid;
	/**
	 * 内物图片路径
	 */
	@Condition
	private String problempartsInsideitempicurl;
	/**
	 * 外包装图片路径
	 */
	@Condition
	private String problempartsOutsideitempicurl;
	/**
	 * 退回运单号
	 */
	@Condition
	private String problempartsReturnexpressnumber;
	/**
	 * 物流状态：无物流=10，已签收=20，派件中=30，从离开出港网点到最后一站中心发出前的所有状态=40，从最后一站中心到派送前的所有状态=50
	 */
	@Condition
	private String problempartsExpresstype;
	/**
	 * 物流是否更新:任意物流节点，是否正常更新10=正常，20=异常
	 */
	@Condition
	private String problempartsExpressupdatestate;
	/**
	 * 是否免责:10=免责，20=非免责
	 */
	@Condition
	private String problempartsDutystate;
	/**
	 * 是否收到货:10=收到，20=未收到
	 */
	@Condition
	private String problempartsObtainstate;
	/**
	 * 是否退回:10=继续退回，20=取消退回
	 */
	@Condition
	private String problempartsReturnstate;
	/**
	 * 是否改地址:10=继续改地址，20=取消改地址
	 */
	@Condition
	private String problempartsAlteraddr;
	/**
	 * 地址是否改出:10=地址已改出，20=地址未改出
	 */
	@Condition
	private String problempartsAlteraddrstate;

	/**
	 * 拨打发件人电话次数:默认值为0,逐1累加
	 */
	@Condition
	private String problempartsCallsendercount;
	/**
	 * 拨打收件人电话次数
	 */
	@Condition
	private String problempartsCallrecipientscount;
	/**
	 * 拨打网点电话次数：默认值为0,逐1累加。网点可以是派件点，停滞点或者上级网点
	 */
	@Condition
	private String problempartsCallcompanycount;
	/**
	 * 未收到天数:默认值为0,逐1累加
	 */
	@Condition
	private String problempartsUncollecteddays;
	/**
	 * 是否签收:10=签收，20=未签收
	 */
	@Condition
	private String problempartsReceivestate;

	/**
	 * 是否遗失:10=遗失，20=未遗失
	 */
	@Condition
	private String problempartsLoststate;
	/**
	 * 签收日期:催单流程获取的日期
	 */
	@Condition
	private String problempartsReceivedate;
	/**
	 * 催单流程分支:10=继续系统流程等待，20=智能语音催中心，30=上报遗失
	 */
	@Condition
	private String problempartsReminderstate;
	/**
	 * 工单来源:10=系统，20=中天
	 */
	@Condition
	private String problempartsSource;
	/**
	 * 操作员ID:上报人ID
	 */
	@Condition
	private String problempartsOperatorid;
	/**
	 * 创建时间
	 */
	@Condition
	private String problempartsCreatedate;
	/**
	 * 修改时间
	 */
	@Condition
	private String problempartsChangedate;
	/**
	 * 备注
	 */
	@Condition
	private String problempartsMemo;

	@Condition
	private String problempartsVarparama;
	@Condition
	private String problempartsVarparamb;
	@Condition
	private String problempartsVarparamc;
	@Condition
	private Integer problempartsIntparama;
	@Condition
	private Integer problempartsIntparamb;
	@Condition
	private Integer problempartsIntparamc;
	@Condition
	private Float problempartsFloatparama;
	@Condition
	private Float problempartsFloatparamb;
	@Condition
	private Float problempartsFloatparamc;
}
