package com.ifrabbit.nk.quertz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Authod: chenyu
 * @Date 2018/6/11 9:47
 * Content:
 */
@Data@NoArgsConstructor
public class BalanceDTO extends Balance {
    private String fuzzyName;
    private String fuzzyDate;
}
