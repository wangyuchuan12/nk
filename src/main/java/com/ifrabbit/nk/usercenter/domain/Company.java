package com.ifrabbit.nk.usercenter.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;


@Data
@NoArgsConstructor
@Entity(table = "tbase_company")
public class Company implements Serializable{
    @Id
    @Column(name="company_id")
    private Long id;
    @Conditions({@Condition, @Condition(properties = "fuzzyCompanyName", type = CONTAINING)})
    private String company_name;
    private Long company_code;
    @Condition
    private String company_type;
    private String company_extcode;
    private Long company_parentid=0l;
    private String company_layrec;
    private Integer company_layno=0;
    @Conditions({@Condition, @Condition(properties = "fuzzyCompany_province", type = CONTAINING)})
    private String company_province;
    @Conditions({@Condition, @Condition(properties = "fuzzyCompany_city", type = CONTAINING)})
    private String company_city;
    private String company_area;
    private String company_address;
    private String company_postzip;
    private String company_email;
    private String company_linker;
    private String company_linktel;
    private String company_tel;
    private String company_tel_2;
    private String company_tel_3;
    private String company_fax;
    private Integer company_state = 0;//1=生效，9=失效，2=待审核，3=冻结。系统默认值=0
    private String company_iscontract;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date company_begincontractdate;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date company_endcontractdate;
    private String company_memo;
    private Date company_adddate=new Date(new java.util.Date().getTime());
    private BigDecimal company_originalcredit;
    private BigDecimal company_credit;
    private BigDecimal company_price;
    private BigDecimal company_own;
    private BigDecimal company_paid;
    private BigDecimal company_sum;
    @Condition
    private String company_varparama;//上级网点名称
    @Condition
    private String company_varparamb;//登录的账号
    private String company_varparamc;
    private String company_varparamd;
    private String company_varparame;
    private Long company_intparama;
    private Long company_intparamb;
    private Long company_intparamc;
    private Long company_intparamd;
    private Long company_intparame;
    private Float company_floatparama;
    private Float company_floatparamb;
    private Float company_floatparamc;
    private Float company_floatparamd;
    private Float company_floatparame;
    @Transient
    private String[] companyRegion;
    @Transient
    private String company_parentlayrec;

    public void setCompany_type(String company_type) {
        if(company_type.equals("中心")){
            this.company_type = "1";
        }else if(company_type.equals("一级网点")){
            this.company_type = "2";
        }else if(company_type.equals("二级网点")){
            this.company_type = "3";
        }else if(company_type.equals("其他")){
            this.company_type = "4";
        }else if(company_type.equals("1")){
            this.company_type = "1";
        }else if(company_type.equals("2")){
            this.company_type = "2";
        }else if(company_type.equals("3")){
            this.company_type = "3";
        }else {
            this.company_type = "4";
        }
    }

    public void setCompany_iscontract(String company_iscontract) {
        if(company_iscontract.equals("已签约")){
            this.company_iscontract = "1";
        }else if(company_iscontract.equals("未签约")){
            this.company_iscontract = "0";
        }else if(company_iscontract.equals("1")){
            this.company_iscontract = "1";
        }else{
            this.company_iscontract = "0";
        }
    }

}
