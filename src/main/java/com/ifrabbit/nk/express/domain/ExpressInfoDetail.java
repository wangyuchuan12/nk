package com.ifrabbit.nk.express.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mybatis.annotations.Condition;
import org.springframework.data.mybatis.annotations.Conditions;
import org.springframework.data.mybatis.annotations.Entity;
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
public class ExpressInfoDetail extends LongId {

    /**
     * `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
     * `express_number` varchar(64) NOT NULL DEFAULT '' COMMENT '运单号',
     * `express_detaildate` date NOT NULL DEFAULT '0000-00-00' COMMENT '物流记录时间',
     * `express_detailtype` smallint(6) NOT NULL DEFAULT '0' COMMENT '物流明细类型 ：类型分为：1=揽收，2=运输中，3=派件中，4=已签收',
     * `begin_companyid` varchar(64) NOT NULL DEFAULT '' COMMENT '起始站点ID',
     * `begin_companyname` varchar(200) NOT NULL DEFAULT '' COMMENT '起始站点名称',
     * `begin_companytype` smallint(6) NOT NULL DEFAULT '0' COMMENT '起始企业类型 类型分为：1为派件网点，2为分拨中心',
     * `end_companyid` varchar(64) NOT NULL DEFAULT '' COMMENT '终止站点ID',
     * `end_companyname` varchar(200) NOT NULL DEFAULT '' COMMENT '终止站点名称',
     * `end_companytype` smallint(6) NOT NULL DEFAULT '0' COMMENT '终止企业类型 类型分为：1为派件网点，2为分拨中心',
     * `is_lastdetail` smallint(6) DEFAULT NULL COMMENT '是否最末条记录 0为否 1为是',
     * `area_varchar1` varchar(255) DEFAULT NULL COMMENT '字符备用字段',
     * `area_int1` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
     * `area_float1` float DEFAULT NULL COMMENT '浮点备用字段',
     */

    private String express_number;//运单号

    private String express_detaildate;//物流记录时间
    private Integer express_detailtype;//物流明细类型
    private String begin_companyid;//起始站点ID
    private String begin_companyname;//起始站点名称
    private Integer begin_companytype;//起始企业类型
    private String end_companyid;//终止站点ID
    private String end_companyname;//终止站点名称
    private Integer end_companytype;//终止企业类型
    private Integer is_lastdetail;//是否最末条记录

    private String area_varchar1;//字符备用字段 现在存放物流更新时间用来比较物流是否更新
    private Integer area_int1;//数字备用字段
    private Float area_float1;//浮点备用字段


}
