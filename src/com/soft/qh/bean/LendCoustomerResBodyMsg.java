package com.soft.qh.bean;

import com.soft.qh.Record;


/**
 * 常贷客响应
 * @param 
 * @return
 */
public class LendCoustomerResBodyMsg  implements Record{
	
	private String idNo ; //证件号码
	private String idType ;//证件类型
	private String name ;//主体名称
	private String seqNo ;//序列号
	private String reasonCode ;//查询原因
	private String industry ;//机构所属行业
	private String amount ;//命中机构数目
	private String busiDate ;//业务发生时间日期
	private String erCode ;//错误代码
	private String erMsg ;//错误信息
	
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
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBusiDate() {
		return busiDate;
	}
	public void setBusiDate(String busiDate) {
		this.busiDate = busiDate;
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
