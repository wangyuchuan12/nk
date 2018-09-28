package com.ifrabbit.nk.usercenter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;
import org.springframework.data.mybatis.domains.LongId;

@Entity(table = "tbase_menuoperate")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("new")
public class Operate {

  @Id
  private Long id;

  @Conditions({
          @Condition(column = "menuoperate_menuid", properties = "menu.id"),
          @Condition(column = "menuoperate_menuid", properties = "menuId")
  })

  @ManyToOne
  @JoinColumn(name = "menuoperate_menuid", referencedColumnName = "menuinfo_id")
  private Menu menu;

  @Condition
  private String code;

  @Condition
  @Column(name = "menuoperate_operatename")
  private String name;

  @Column(name = "menuoperate_link")
  private String url;

  @Column(name = "menuoperate_icon")
  private String icon;

  private String resource;

  @Column(name = "menuoperate_memo")
  private String description;

  private Boolean hidden;

  private String target;

  private Long menuoperate_tabid;

  private Long menuoperate_dealid;

  private String menuoperate_operate;

  private Integer menuoperate_state;

  private Integer menuoperate_type;

  private String menuoperate_varparama;

  private String menuoperate_varparamb;

  private String menuoperate_varparamc;

  private String menuoperate_varparamd;

  private String menuoperate_varparame;

  private Long menuoperate_intparama;

  private Long menuoperate_intparamb;

  private Long menuoperate_intparamc;

  private Long menuoperate_intparamd;

  private Long menuoperate_intparame;

  private Float menuoperate_floatparama;

  private Float menuoperate_floatparamb;

  private Float menuoperate_floatparamc;

  private Float menuoperate_floatparamd;

  private Float menuoperate_floatparame;

  /**
   * 其它属性. 采用JSON格式： [{name:""},{key:val}]
   */
  private String extattr;

}
