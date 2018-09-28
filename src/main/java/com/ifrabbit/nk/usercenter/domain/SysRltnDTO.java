package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lishaomiao
 */
@Data
@NoArgsConstructor
public class SysRltnDTO extends SysRltn {
    private String fuzzyName;
    private String fuzzyAnswer;
}
