package com.ifrabbit.nk.express.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Auther: WangYan
 * @Date: 2018/8/20 17:00
 * @Description:
 */
@Data
@NoArgsConstructor
@Entity(table = "tapp_calldetail")
public class CallDetail implements Serializable {
    @Id
    @Column(name = "calldetail_id")
    private Long id;
    @Condition
    private Long calldetail_tableid;
    @Condition
    private Long calldetail_dealid;
    @Condition
    private Long calldetail_problemid;
    private String calldetail_phonenumber;
    private Integer calldetail_result;
    private String calldetail_expressnumber;
    @Condition
    private String calldetail_nodename;
    private Timestamp calldetail_calltime;
    private Timestamp calldetail_time;
    private Integer calldetail_contentid;
    private String calldetail_content;
    private Integer calldetail_answerid;
    private String  calldetail_name;
    private Long calldetail_companyid;
    private Integer calldetail_calltype;
    @Condition
    private String calldetail_ivrid;
    private String calldetail_answer;
    private Float calldetail_period;
    @Condition
    private Integer calldetail_state;
    private String mwordlevel_varparama;
    private String mwordlevel_varparamb;
    private String mwordlevel_varparamc;
    private String mwordlevel_varparamd;
    private String mwordlevel_varparame;
    private Long mwordlevel_intparama;
    private Long mwordlevel_intparamb;
    private Long mwordlevel_intparamc;
    private Long mwordlevel_intparamd;
    private Long mwordlevel_intparame;
    private Float mwordlevel_floatparama;
    private Float mwordlevel_floatparamb;
    private Float mwordlevel_floatparamc;
    private Float mwordlevel_floatparamd;
    private Float mwordlevel_floatparame;
}
