package com.ifrabbit.nk.usercenter.domain;

import static org.springframework.data.mybatis.annotations.Id.GenerationType.ASSIGNATION;
import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

import java.util.List;

import com.alibaba.druid.sql.visitor.functions.Char;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.*;
import org.springframework.data.mybatis.domains.LongId;

import javax.xml.bind.ValidationEvent;

/*
 *
 *
 * @author sjj
 * @date  16:41
 * @param
 * @return
 */
@Entity(table = "tbase_roleinfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends LongId {

    /**
     * 角色编码
     */
    @Conditions({
            @Condition,
            @Condition(properties = "fuzzyCode", type = CONTAINING)
    })
    private String code;

    /**
     * 角色名字
     */
    @Conditions({
            @Condition,
            @Condition(properties = "fuzzyName", type = CONTAINING)
    })
    private String name;

    private String description;//角色备注
    private Integer type; //角色类型
    private String alias;//角色别名
    private Integer state;//角色状态

    private String states;//角色状态所对应的中文
    private String types;//角色类型所对应的中文

    @Transient
    private List<Staff> staffs;
    @Transient
    private List<Menu> menus;
    @Transient
    private List<Group> groups;
    @Transient
    private List<Operate> operates;

}
