package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuDTO extends Menu {

  private String fuzzyName;
  private String fuzzyCode;

  private Boolean containRole;
  private String userId;
  private Long roleId;
}
