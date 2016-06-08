package com.soft.qh.bean;

import com.soft.qh.Record;
/**
 * 好信欺诈请求消息体
 * @author lujf
 * 2016年6月2日
 */
public class CheatReqBodyMsg implements Record{
	//证件号码
	private String idNo;
	//证件类型
	private String idType;
	//主体名称
	private String name;
	//查询原因
	private String reasonNo;
	//手机号码
	private String mobileNo;
	//IP地址
	private String ip;
	//业务描述
	private String busiDesc;
	//信息主体授权码
	private String entityAuthCode;
	//信息主体授权时间
	private String entityAuthDate;
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
	public String getReasonNo() {
		return reasonNo;
	}
	public void setReasonNo(String reasonNo) {
		this.reasonNo = reasonNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getBusiDesc() {
		return busiDesc;
	}
	public void setBusiDesc(String busiDesc) {
		this.busiDesc = busiDesc;
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
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	
}
