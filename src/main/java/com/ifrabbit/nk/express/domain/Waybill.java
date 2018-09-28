package com.ifrabbit.nk.express.domain;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Conditions;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.JoinColumn;
import org.springframework.data.mybatis.annotations.ManyToOne;
import org.springframework.data.mybatis.domains.LongId;

import com.bstek.uflo.model.task.Task;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(table = "ex_waybill")
public class Waybill extends LongId {
	/**
	 * 运单号码.
	 */
	@Conditions({ @Condition, @Condition(type = CONTAINING, properties = "fuzzyNumber") })
	@Column(name = "number")
	private String number;
	/**
	 * 出发城市.
	 */
	@Condition
	private String fromCity;
	/**
	 * 到达城市.
	 */
	@Condition
	private String toCity;

	@Conditions({ @Condition(properties = "customer.id", column = "id"),
			@Condition(properties = "customerId", column = "id"),
			@Condition(properties = "customer.name", column = "name") })
	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer;

	private Long processInstanceId;

	@Transient
	private Task task;

}
