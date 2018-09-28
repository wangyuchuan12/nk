package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;

import java.util.Date;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;


/**
 * @author lishaomiao
 * @date 2018/6/1 11:05
 */
@Data
@NoArgsConstructor
@Entity(table = "ex_info_network")
public class    NetWorkBalance {

    /**
     *  网点id
     */
    @Id
    @Conditions({
            @Condition,
            @Condition(type = CONTAINING, properties = "fuzzyId")
    })
    private Long id;
    /**
     * 网点状态
     * 1启用/2禁用
     */
    @Conditions({
            @Condition,
            @Condition(type = CONTAINING, properties = "fuzzySate")
    })
    private String dot_state;
    /**
     * 网点名称
     */
    @Conditions({
            @Condition,
            @Condition(type = CONTAINING, properties = "fuzzyName")
    })
    private String dot_name;
    /**
     * 网点联系电话
     */
    @Conditions({
            @Condition,
            @Condition(type = CONTAINING, properties = "fuzzyPhone")
    })
    private String dot_phone;
    /**
     * 网点地址
     */
    @Condition
    private String dot_address;
    /**
     * 创建时间
     */
    @Conditions({
            @Condition,
            @Condition(type = CONTAINING, properties = "fuzzyCreateDate")
    })
    private String dot_createdate;
    /**
     * 完成时间
     */
    @Conditions({
            @Condition,
            @Condition(type = CONTAINING, properties = "fuzzyChangeDate")
    })
    private String dot_changedate;
    /**
     * 备注
     */
    @Condition
    private String dot_memo;

}
