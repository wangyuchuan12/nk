package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class CustomerDTO extends Customer {

	private String fuzzyName;
	private String fuzzyPhone;

}
