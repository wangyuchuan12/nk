package com.ifrabbit.nk.usercenter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifrabbit.nk.express.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.*;
import org.springframework.data.mybatis.domains.LongId;

import java.util.List;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;


/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/14
 * Time:11:04
 */
@Entity(table = "tbase_userreport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("new")
public class UserReport extends LongId {
    /**
     * 因为继承了LongId类，该类已经序列化，所以UserReport存到redis缓存中不需要经过序列化
     */
    @Conditions({
            @Condition,
            @Condition(properties = "fuzzyName", type = CONTAINING)
    })
    private String name;

    @Conditions({
            @Condition,
            @Condition(properties = "fuzzyCode", type = CONTAINING)
    })
    private String code;
    @Condition
    private String value;//参数值
    private Integer state;//参数状态
    private Integer type;//参数类型
    private String description;//参数详情
    @Conditions({
            @Condition,
            @Condition(properties = "FuzzyStaffName", type = CONTAINING)
    })
    private String staffname;
    private Integer companyid;//公司id
    private String states;//参数状态对应的中文
    private String types;//参数类型对应的中文
    private String varparam1;//备用字段
    private Integer intparam1;//备用字段
    private Float floatparam1;//备用字段
    /**
     * 要显示所属公司，那么先从前端获取到userid,那就必须有一张user和company关联的中间表，
     * 在获取到companyid,再根据companyid获取到companyname
     */
//    private String companyname;

    @Transient
    private List<Staff> users;//接收多个users
    @Transient
    private List<UserReport> userReports;//接收多个userReports

}
