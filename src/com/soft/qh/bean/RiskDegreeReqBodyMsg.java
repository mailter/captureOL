package com.soft.qh.bean;

import com.soft.qh.Record;
/**
 * 风险度请求消息体
 * @author lujf
 * 2016年6月1日
 */
public class RiskDegreeReqBodyMsg implements Record{
	//证件号码
	private String idNo;
	//证件类型
	private String idType;
	//IP集
	private String ips;
	//卡号集
	private String cardNos;
	//手机号码集
	private String moblieNos;
	//主体名称
	private String name;
	//查询原因
	private String reasonCode;
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
	public String getIps() {
		return ips;
	}
	public void setIps(String ips) {
		this.ips = ips;
	}
	public String getCardNos() {
		return cardNos;
	}
	public void setCardNos(String cardNos) {
		this.cardNos = cardNos;
	}
	public String getMoblieNos() {
		return moblieNos;
	}
	public void setMoblieNos(String moblieNos) {
		this.moblieNos = moblieNos;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
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
