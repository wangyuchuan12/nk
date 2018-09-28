package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppProblempartsDTO extends AppProblemparts {

	private String fuzzyName;
	private String fuzzyPhone;

}
