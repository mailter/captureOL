package com.soft.qh.bean;

import com.soft.qh.Record;
/**
 * 地址通请求消息体
 * @author lujf
 * 2016年6月1日
 */
public class AddressReqBodyMsg implements Record{
	//证件号码
	private String idNo;
	//证件类型
	private String idType;
	//主体名称
	private String name;
	//手机号码
	private String mobileNo;
	//地址
	private String address;
	//查询原因
	private String reasonNo;
	//邮箱
	private String email;   
	//信息主体授权码
	private String entityAuthCode;
	//信息主体授权时间
	private String entityAuthDate;
	//qq号
	private String qqNo;
	//序列号
	private String seqNo;
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReasonNo() {
		return reasonNo;
	}
	public void setReasonNo(String reasonNo) {
		this.reasonNo = reasonNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEntityAuthCode() {
		return entityAuthCode;
	}
	public void setEntityAuthCode(String entityAuthCode) {
		this.entityAuthCode = entityAuthCode;
	}
	public String getEntityAuthDate() {
		return entityAuthDate;
	}
	public void setEntityAuthDate(String entityAuthDate) {
		this.entityAuthDate = entityAuthDate;
	}
	public String getQqNo() {
		return qqNo;
	}
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	
}
