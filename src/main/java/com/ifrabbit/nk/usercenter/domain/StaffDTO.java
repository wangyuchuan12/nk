package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StaffDTO extends Staff {

    private String fuzzyName;
    private String fuzzyUsername;
    private String fuzzyMobile;
    private String fuzzyEmail;
    private Boolean containRole = false;
    private Boolean containGroup = false;
    private Long roleId;
    /**
     * 根据用户组获取用户
     */
    private Long groupId;

}
