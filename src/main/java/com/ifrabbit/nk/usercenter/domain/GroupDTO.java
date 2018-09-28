package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupDTO extends Group {

  private String fuzzyName;
  private String fuzzyCode;

  /**
   * 根据UserId查询Group
   */
  private String userId;
  /**
   * 根据RoleId查询Group
   */
  private Long roleId;

  //测试的
// private Long groupId;
}
