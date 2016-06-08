package com.soft.qh.bean;

import com.soft.qh.Record;

public class GoodReliabilityResBodyMsg implements Record {
	//证件号码
	private String idNo;
	//证件类型
	private String idType;
	//主体名称
	private String name;
	//手机号码
	private String mobileNo;
	//子批次号，本批次内唯一
	private String seqNo;
	//来源代码
	private String sourceId;
	//综合评分
	private String credooScore;
	//查询时间
	private String dataBuildTime;
	//错误代码
	private String erCode;
	//错误信息
	private String erMsg;
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
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getCredooScore() {
		return credooScore;
	}
	public void setCredooScore(String credooScore) {
		this.credooScore = credooScore;
	}
	public String getDataBuildTime() {
		return dataBuildTime;
	}
	public void setDataBuildTime(String dataBuildTime) {
		this.dataBuildTime = dataBuildTime;
	}
	public String getErCode() {
		return erCode;
	}
	public void setErCode(String erCode) {
		this.erCode = erCode;
	}
	public String getErMsg() {
		return erMsg;
	}
	public void setErMsg(String erMsg) {
		this.erMsg = erMsg;
	}

}
