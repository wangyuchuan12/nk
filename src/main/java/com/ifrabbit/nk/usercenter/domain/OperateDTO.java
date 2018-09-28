package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperateDTO extends Operate {

  private String fuzzyName;
  private String fuzzyCode;
  private Long menuId;
  private Long roleId;
}
