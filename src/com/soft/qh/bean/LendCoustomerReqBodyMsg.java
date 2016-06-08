package com.soft.qh.bean;

import com.soft.qh.Record;


/**
 * 常贷客请求
 * @param 
 * @return
 */
public class LendCoustomerReqBodyMsg  implements Record{
	
	private String idNo ; //证件号码
	private String idType ;//证件类型
	private String busiDesc;//业务描述
	private String name;//主体名称
	private String entityAuthCode;//信息主体授权码
	private String entityAuthDate;//息主体授权时间
	private String seqNo;//序列号
	
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
	public String getBusiDesc() {
		return busiDesc;
	}
	public void setBusiDesc(String busiDesc) {
		this.busiDesc = busiDesc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
