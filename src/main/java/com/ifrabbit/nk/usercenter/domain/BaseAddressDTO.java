package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class BaseAddressDTO extends BaseAddress {

	private String fuzzyName;
	private String fuzzyPhone;

}
