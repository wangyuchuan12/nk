package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyDTO extends Company {
    private String fuzzyCompanyName;
    private String[] fuzzyCompanyRegion;
    private String fuzzyCompany_province;
    private String fuzzyCompany_city;
}
