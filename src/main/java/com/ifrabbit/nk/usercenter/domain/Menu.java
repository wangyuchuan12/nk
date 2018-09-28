package com.ifrabbit.nk.usercenter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.Id;

@Entity(table = "tbase_menuinfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("new")
public class Menu {

  @Id
  @Column(name = "menuinfo_id")
  private Long id;

  public static final Long ROOT_PID = 0L;

  @Column(name = "menuinfo_upmenuid")
  private Long pid;

  @Condition
  private String code;

  @Condition
  @Column(name = "menuinfo_showname")
  private String name;

  @Column(name = "menuinfo_url")
  private String url;

  @Column(name = "menuinfo_icon")
  private String icon;

  private String resource;

  @Column(name = "menuinfo_memo")
  private String description;

  private Long menuinfoDealid;

  private Boolean hidden;

  private Long lft;

  private Long rgt;

  private Integer sort;

  private String target;

  private Long menuinfo_tabid;

  private String menuinfo_layrec;

  private Integer menuinfo_layno;

  private Integer menuinfo_type;

  private String menuinfo_name;

  private Integer menuinfo_intro;

  private Integer menuinfo_status;

  private Integer menuinfo_attribute;

  private String menuinfo_varparama;

  private String menuinfo_varparamb;

  private String menuinfo_varparamc;

  private String menuinfo_varparamd;

  private String menuinfo_varparame;

  private Long menuinfo_intparama;

  private Long menuinfo_intparamb;

  private Long menuinfo_intparamc;

  private Long menuinfo_intparamd;

  private Long menuinfo_intparame;

  private Float menuinfo_floatparama;

  private Float menuinfo_floatparamb;

  private Float menuinfo_floatparamc;

  private Float menuinfo_floatparamd;

  private Float menuinfo_floatparame;

  /**
   * 其它属性. 采用JSON格式： [{name:""},{key:val}]
   */
  private String extattr;

  @Transient
  private Menu parent;

  @Transient
  private List<Menu> children;

  @Transient
  private List<Operate> operates;

  @Transient
  private List<Role> roles;

  public void addChild(Menu child) {
    if (null == children) {
      children = new ArrayList<>();
    }
    children.add(child);
  }

}
