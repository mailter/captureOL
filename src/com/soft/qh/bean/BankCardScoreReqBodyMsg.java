package com.soft.qh.bean;

import com.soft.qh.Record;
/**
 * 信用卡评分请求消息体
 * @author lujf
 * 2016年6月2日
 */
public class BankCardScoreReqBodyMsg implements Record{
	 //银行账号
	 private String accountNo;
	 //户主证件号码
	 private String idNo;
	 //户主证件类型
	 private String idType;
	 //户主手机号码
	 private String mobileNo;
	 //户主姓名
	 private String name;
	 //查询原因
	 private String reasonCode;
	 //户主授权代码
	 private String entityAuthCode;
	 //户主授权时间
	 private String entityAuthDate;
	 //序列号
	 private String seqNo;
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
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
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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
