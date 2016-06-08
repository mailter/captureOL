package com.soft.qh.bean;
/**
 * 消息头
 * @author lujf
 * 2016年5月27日
 */
public class MessageHead {
	//机构代码
	private String orgCode;
	//渠道、系统ID
	private String chnlId;
	//交易流水号
	private String transNo;
	//交易时间 yyyy-MM-dd HH:mm:ss
	private String transDate;
	//授权代码
	private String authCode;
	//授权时间yyyy-MM-dd HH:mm:ss
	private String authDate;
	//错误代码
	private String rtCode;
	//错误消息
	private String rtMsg;
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getChnlId() {
		return chnlId;
	}
	public void setChnlId(String chnlId) {
		this.chnlId = chnlId;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public String getRtCode() {
		return rtCode;
	}
	public void setRtCode(String rtCode) {
		this.rtCode = rtCode;
	}
	public String getRtMsg() {
		return rtMsg;
	}
	public void setRtMsg(String rtMsg) {
		this.rtMsg = rtMsg;
	}
}
