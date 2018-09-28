package com.ifrabbit.nk.express.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Conditions;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.domains.LongId;
import org.springframework.data.repository.query.parser.Part;

import java.sql.Timestamp;
import java.util.Date;

import static org.springframework.data.repository.query.parser.Part.Type.SIMPLE_PROPERTY;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/15
 * Time:16:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(table = "ex_info_record")
public class ExpressInfoRecord extends LongId {
    @Condition
    private String  express_number;//运单号
    @Condition
    private Integer express_type;//物流状态 无物流=10，已签收=20，派件中=30，从离开出港网点到最后一站中心发出前的所有状态=40，从最后一站中心到派送前的所有状态=50
    @Condition
    private Integer express_updatestate;//物流是否更新 任意物流节点，是否正常更新10=正常，20=异常
    @Condition
    private String  express_lastdate;//最末物流信息时间  最后一条物流信息的时间，可用于打延误或者验证物流信息是否停滞

    @Condition
    private Long express_taskid;//任务ID
    @Condition
    private Long express_problemid;//问题件ID

    private Integer express_dealtype;//分类编码
    @Condition
    private String express_nodename;//当前节点名

    private Timestamp express_querytime;//实际查询时间

    private Timestamp express_plantime;//计划查询时间

    private Timestamp express_lasttime;//上一次查询时间

    @Condition
    private Integer express_state;//处理状态，0未处理，1已处理
    @Condition
    private String express_ipaddress;//主机地址

    private Integer express_result;//查询结果

    private String express_modal;//物流的三种解析类型

    private String  express_stagnationcompanyname;//发出停滞网点名字

    private Integer express_networknumber;//计数停滞网点个数

    @Condition
    private Long express_processinstanceid;//实例ID

    @Condition
    private Long express_dealid;//dealInfo表的主键ID

    @Condition
    private Long express_tabid;//tableInfo表的主键ID

    private String express_errorcompanyname;//错误网点名

    @Condition
    private Integer interface_state;//物流接口状态 1正常，2超时，0不完整
    private String  area_varchar1;//字符备用字段
    private Integer area_int1;//数字备用字段
    private Float   area_float1;//浮点备用字段
}
