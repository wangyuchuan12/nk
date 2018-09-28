package com.ifrabbit.nk.usercenter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mybatis.annotations.*;
import org.springframework.data.mybatis.domains.LongId;

import java.sql.Array;
import java.util.ArrayList;

import static org.springframework.data.repository.query.parser.Part.Type.CONTAINING;

@Data
@NoArgsConstructor
@Entity(table = "tbase_address")
public class BaseAddress extends LongId {

	/**
	 * 所属用户
	 */
    @Conditions({
            @Condition,
            @Condition(type = CONTAINING, properties = "fuzzyName")
    })
	private Long addressUserid;
	/**
	 * 发货人姓名
	 */
    @Condition
    private String addressReceiver;
//	/**
//	 * 二级区域ID
//	 */
//	@Condition
//	private Long addressProvinceid;
//	/**
//	 * 三级区域ID
//	 */
//	@Condition
//	private Long addressCityid;
//	/**
//	 *四级区域ID
//	 */
//    @Condition
//	private Long addressAreaid;


    /**
	 *二级区域名称
	 */
    @Transient
	private ArrayList<String> addressCascader;
    public ArrayList<String> getAddressCascader() {
        return addressCascader;
    }
    public void setAddressCascader(ArrayList<String> addressCascader) {
        this.addressCascader = addressCascader;
    }


    @Condition
    private String addressProvince;

    public String getAddressProvince() {
            if(this.getAddressCascader() == null||this.getAddressCascader().size()==0){
            return addressProvince;

        }else {
                return this.getAddressCascader().get(0);

        }
    }

    public void setAddressProvince(String addressProvince) {
        if(this.getAddressCascader() == null||this.getAddressCascader().size()==0){
            this.addressProvince = addressProvince;

        }else {
            this.addressProvince = this.getAddressCascader().get(0);

        }
       }


    public String getAddressCity() {
        if(this.getAddressCascader() == null||this.getAddressCascader().size()==0){
            return addressCity;

        }else {
            return this.getAddressCascader().get(1);

        }
    }
    public void setAddressCity(String addressCity) {

       if(this.getAddressCascader() == null||this.getAddressCascader().size()==0){
            this.addressCity = addressCity;
            }else {
            this.addressCity = this.getAddressCascader().get(1);

        }
    }
    public String getAddressArea() {

        if(this.getAddressCascader() == null||this.getAddressCascader().size()==0){
            return addressArea;

        }else {
            return this.getAddressCascader().get(1);

        }
    }
    public void setAddressArea(String addressArea) {
        if(this.getAddressCascader() == null||this.getAddressCascader().size()==0){
            this.addressArea = addressArea;

        }else {
            this.addressArea = this.getAddressCascader().get(2);

        }
    }



    /**
	 *三级区域名称
	 */
    @Condition
	private String addressCity;



    /**
	 *四级区域名称
	 */
    @Condition
	private String addressArea;
	/**
	 *街道地址
	 */
    @Condition
	private String addressAddress;
	/**
	 *邮政编码
	 */
    @Condition
	private String addressZipcode;
	/**
	 *联系电话
	 */
    @Conditions({
            @Condition,
            @Condition(type = CONTAINING, properties = "fuzzyPhone")
    })
	private String addressTel;
	/**
	 *1=默认；2=不是默认
	 */
    @Condition
	private Integer addressIsdefault;
	/**
	 *1=有效；9=无效
	 */
    @Condition
	private Integer addressState;
	/**
	 *备注
	 */
    @Condition
	private String addressMemo;
	/**
	 *备用字段
	 */
	@Condition
	private String addressVarparama;
	/**
	 *备用字段
	 */
    @Condition
	private String addressVarparamb;
	/**
	 *备用字段
	 */
    @Condition
	private String addressVarparamc;
	/**
	 *备用字段
	 */
    @Condition
	private String addressVarparamd;
	/**
	 *备用字段
	 */
    @Condition
	private String addressVarparame;

	/**
	 *备用字段
	 */
    @Condition
	private Long addressIntparama;
	/**
	 *备用字段
	 */
    @Condition
	private Long addressIntparamb;

	/**
	 *备用字段
	 */
    @Condition
	private Long addressIntparamc;
	/**
	 *备用字段
	 */
    @Condition
	private Long addressIntparamd;
	/**
	 *备用字段
	 */
    @Condition
	private Long addressIntparame;
	/**
	 *备用字段
	 */
    @Condition
	private Float addressFloatparama;
	/**
	 *备用字段
	 */
    @Condition
	private Float addressFloatparamb;
	/**
	 *备用字段
	 */
    @Condition
	private Float addressFloatparamc;
	/**
	 *备用字段
	 */
    @Condition
	private Float addressFloatparamd;
	/**
	 *备用字段
	 */
    @Condition
	private Float addressFloatparame;

}
