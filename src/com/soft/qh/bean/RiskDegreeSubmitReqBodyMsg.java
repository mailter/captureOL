package com.soft.qh.bean;

import com.soft.qh.Record;

/**
 * 风险度报送请求消息体
 * @author lujf
 * 2016年6月1日
 */
public class RiskDegreeSubmitReqBodyMsg  implements Record{
	//证件号码
	private String idNo;
	//证件类型
	private String idType;
	//主体名称
	private String name;
	//金额
	private String money;
	//IP集
	private String ips;
	//卡号集
	private String cardNos;
	//手机号码集
	private String moblieNos;
	//风险标记
	private String rskMark;
	//币种
	private String currency;
	//报送严重等级
	private String gradeReport;
	//业务发生时间
	private String updatedDate;
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
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
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
	public String getRskMark() {
		return rskMark;
	}
	public void setRskMark(String rskMark) {
		this.rskMark = rskMark;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getGradeReport() {
		return gradeReport;
	}
	public void setGradeReport(String gradeReport) {
		this.gradeReport = gradeReport;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
	

}
