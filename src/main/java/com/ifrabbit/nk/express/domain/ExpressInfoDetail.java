package com.ifrabbit.nk.express.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.*;
import org.springframework.data.mybatis.domains.LongId;

import java.util.Date;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/16
 * Time:10:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(table = "ex_info_detail")
public class ExpressInfoDetail {
    @Id
    @Column(name="interface_id")
    private Long id;

    @Column(name="interface_id")
    private String express_number;//运单号

    private Long express_id;//对应recordid

    @Column(name="interface_expressdetaildate")
    private String express_detaildate;//物流记录时间
    @Column(name="interface_expressdetailtype")
    private Integer express_detailtype;//物流明细类型
    @Column(name="interface_begincompanyid")
    private String begin_companyid;//起始站点ID
    @Column(name="interface_begincompanyname")
    private String begin_companyname;//起始站点名称
    @Column(name="interface_begincompanytype")
    private Integer begin_companytype;//起始企业类型
    @Column(name="interface_endcompanyid")
    private String end_companyid;//终止站点ID
    @Column(name="interface_endcompanyname")
    private String end_companyname;//终止站点名称
    @Column(name="interface_endcompanytype")
    private Integer end_companytype;//终止企业类型
    @Column(name="interface_islastdetail")
    private Integer is_lastdetail;//是否最末条记录

    private String area_varchar1;//字符备用字段 现在存放物流更新时间用来比较物流是否更新
    private Integer area_int1;//数字备用字段
    private Float area_float1;//浮点备用字段


}
