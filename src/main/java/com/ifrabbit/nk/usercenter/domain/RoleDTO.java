package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


/**  
 *    
 *   
 * @author sjj
 * @date  16:40
 * @param   
 * @return   
 */

@Data
@NoArgsConstructor
public class RoleDTO extends Role {

  private String fuzzyName;
  private String fuzzyCode;
  /**
   * 根据UserId查询Role.
   */
  private String userId;
  /**
   * 根据menuId查询role..
   */
  private Long menuId;


}
