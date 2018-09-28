package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
public class WaybillDTO extends Waybill {

	private String fuzzyNumber;
	private String fuzzyCustomerName;
	private Long customerId;

}
