package com.ifrabbit.nk.express.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.domains.LongId;

import java.sql.Timestamp;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/9/20
 * Time:14:39
 */

@Data
@Entity(table = "ex_info_test")
@NoArgsConstructor
@AllArgsConstructor
public class ExpressInfoTest extends LongId {

    @Condition
    private String  express_number;//运单号

    @Condition
    private Long express_taskid;//任务ID
    @Condition
    private Long express_problemid;//问题件ID

    private Integer express_dealtype;//分类编码
    @Condition
    private String express_nodename;//当前节点名

    private Timestamp express_querytime;//实际查询时间

    private Timestamp express_plantime;//实际查询时间

    @Condition
    private Integer express_state;//处理状态，0未处理，1已处理
    @Condition
    private String express_ipaddress;//主机地址

    private Integer express_result;//查询结果

    private String express_modal;//物流的三种解析类型

    @Condition
    private Long express_processinstanceid;//实例ID
}
