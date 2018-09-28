package com.ifrabbit.nk.express.domain;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Conditions;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.domains.LongId;

@Data
@NoArgsConstructor
@Entity(table = "ex_customer")
public class Customer extends LongId {
	/**
	 * 客户姓名.
	 */
	@Conditions({
			@Condition,
			@Condition(type = CONTAINING, properties = "fuzzyName")
	})
	private String name;
	/**
	 * 手机号码.
	 */
	@Conditions({
			@Condition,
			@Condition(type = CONTAINING, properties = "fuzzyPhone")
	})
	private String phone;
	/**
	 * 地址.
	 */
	private String address;

	@Transient
	private Long waybillCount;

}
