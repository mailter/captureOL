package com.soft.qh.bean;

import com.soft.qh.Record;
/**
 * 好信度请求消息体
 * @author lujf
 * 2016年5月27日
 */
public class GoodReliabilityReqBodyMsg implements Record{
	//证件号码
	private String idNo;
	//证件类型
	private String idType;
	//主体名称
	private String name;
	//手机号码
	private String mobileNo;
	//银行卡号
	private String cardNo;
	//查询原因
	private String reasonNo;
	//邮箱
	private String email;
	//微博号
	private String weiboNo;
	//微信号
	private String weixinNo;
	//qq号
	private String qqNo;
	//淘宝帐号
	private String taobaoNo;
	//京东帐号
	private String jdNo;
	//亚马逊帐号
	private String amazonNo;
	//1号店
	private String yhdNo;
	//信息主体授权码
	private String entityAuthCode;
	//信息主体授权时间
	private String entityAuthDate;
	//子批次号，本批次内唯一
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
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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
	public String getWeiboNo() {
		return weiboNo;
	}
	public void setWeiboNo(String weiboNo) {
		this.weiboNo = weiboNo;
	}
	public String getWeixinNo() {
		return weixinNo;
	}
	public void setWeixinNo(String weixinNo) {
		this.weixinNo = weixinNo;
	}
	public String getQqNo() {
		return qqNo;
	}
	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}
	public String getTaobaoNo() {
		return taobaoNo;
	}
	public void setTaobaoNo(String taobaoNo) {
		this.taobaoNo = taobaoNo;
	}
	public String getJdNo() {
		return jdNo;
	}
	public void setJdNo(String jdNo) {
		this.jdNo = jdNo;
	}
	public String getAmazonNo() {
		return amazonNo;
	}
	public void setAmazonNo(String amazonNo) {
		this.amazonNo = amazonNo;
	}
	public String getYhdNo() {
		return yhdNo;
	}
	public void setYhdNo(String yhdNo) {
		this.yhdNo = yhdNo;
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
