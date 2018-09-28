package com.ifrabbit.nk.usercenter.domain;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Conditions;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.domains.LongId;

@Entity(table = "uc_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group extends LongId {

  public static final Long ROOT_PID = 0L;
  @Condition
  private Long pid;
  @Conditions({
      @Condition,
      @Condition(properties = "fuzzyCode", type = CONTAINING)
  })
  private String code;
  @Conditions({
      @Condition,
      @Condition(properties = "fuzzyName", type = CONTAINING)
  })
  private String name;
  private String description;
  private Long lft;
  private Long rgt;
  private Integer sort;
  /**
   * 其它属性. 采用JSON格式： [{name:""},{key:val}]
   */
  private String extattr;

  @Transient
  private Group parent;
  @Transient
  private List<Group> children;
//  @Transient//测试的
//  private Long groupId;

  public void addChild(Group child) {
    if (null == children) {
      children = new ArrayList<>();
    }
    children.add(child);
  }

}
