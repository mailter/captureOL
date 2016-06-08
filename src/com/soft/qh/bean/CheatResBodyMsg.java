package com.soft.qh.bean;

import com.soft.qh.Record;
/**
 * 好信欺诈返回消息体
 * @author lujf
 * 2016年6月2日
 */
public class CheatResBodyMsg implements Record{
	//手机号码
	 private String mobileNo;
	 //IP地址
	 private String ip;
	 //序列号
	 private String seqNo;
	 //命中暴力破解
	 private String isMachdForce;
	 //命中DNS服务器
	 private String isMachdDNS;
	 //命中邮件服务器
	 private String isMachdMailServ;
	 //命中seo
	 private String isMachdSEO;
	 //命中组织出口
	 private String isMachdOrg;
	 //命中爬虫
	 private String isMachdCrawler;
	 //命中代理服务器
	 private String isMachdProxy;
	 //命中第三方标注黑名单
	 private String isMachdBlacklist;
	 //命中web服务器
	 private String isMachdWebServ;
	 //命中vpn服务器
	 private String isMachdVPN;
	 //风险评分
	 private String rskScore;
	 //命中第三方标注黑名单
	 private String isMachdBlMakt;
	 //命中骚扰电话
	 private String isMachCraCall;
	 //命中欺诈号码
	 private String isMachFraud;
	 //命中空号（非正常短信语音号码）
	 private String isMachEmpty;
	 //命中收码平台号码
	 private String isMachYZmobile;
	 //命中小号
	 private String isMachSmallNo;
	 //命中社工库号码
	 private String isMachSZNo;
	 //ip风险时间
	 private String iUpdateDate;
	 //手机号码风险时间
	 private String mUpdateDate;
	 //IP风险描述
	 private String iRskDesc;
	 //手机号码风险描述
	 private String mRskDesc;
	 //错误代码
	 private String erCode;
	 //错误信息
	 private String erMsg;
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
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getIsMachdForce() {
		return isMachdForce;
	}
	public void setIsMachdForce(String isMachdForce) {
		this.isMachdForce = isMachdForce;
	}
	public String getIsMachdDNS() {
		return isMachdDNS;
	}
	public void setIsMachdDNS(String isMachdDNS) {
		this.isMachdDNS = isMachdDNS;
	}
	public String getIsMachdMailServ() {
		return isMachdMailServ;
	}
	public void setIsMachdMailServ(String isMachdMailServ) {
		this.isMachdMailServ = isMachdMailServ;
	}
	public String getIsMachdSEO() {
		return isMachdSEO;
	}
	public void setIsMachdSEO(String isMachdSEO) {
		this.isMachdSEO = isMachdSEO;
	}
	public String getIsMachdOrg() {
		return isMachdOrg;
	}
	public void setIsMachdOrg(String isMachdOrg) {
		this.isMachdOrg = isMachdOrg;
	}
	public String getIsMachdCrawler() {
		return isMachdCrawler;
	}
	public void setIsMachdCrawler(String isMachdCrawler) {
		this.isMachdCrawler = isMachdCrawler;
	}
	public String getIsMachdProxy() {
		return isMachdProxy;
	}
	public void setIsMachdProxy(String isMachdProxy) {
		this.isMachdProxy = isMachdProxy;
	}
	public String getIsMachdBlacklist() {
		return isMachdBlacklist;
	}
	public void setIsMachdBlacklist(String isMachdBlacklist) {
		this.isMachdBlacklist = isMachdBlacklist;
	}
	public String getIsMachdWebServ() {
		return isMachdWebServ;
	}
	public void setIsMachdWebServ(String isMachdWebServ) {
		this.isMachdWebServ = isMachdWebServ;
	}
	public String getIsMachdVPN() {
		return isMachdVPN;
	}
	public void setIsMachdVPN(String isMachdVPN) {
		this.isMachdVPN = isMachdVPN;
	}
	public String getRskScore() {
		return rskScore;
	}
	public void setRskScore(String rskScore) {
		this.rskScore = rskScore;
	}
	public String getIsMachdBlMakt() {
		return isMachdBlMakt;
	}
	public void setIsMachdBlMakt(String isMachdBlMakt) {
		this.isMachdBlMakt = isMachdBlMakt;
	}
	public String getIsMachCraCall() {
		return isMachCraCall;
	}
	public void setIsMachCraCall(String isMachCraCall) {
		this.isMachCraCall = isMachCraCall;
	}
	public String getIsMachFraud() {
		return isMachFraud;
	}
	public void setIsMachFraud(String isMachFraud) {
		this.isMachFraud = isMachFraud;
	}
	public String getIsMachEmpty() {
		return isMachEmpty;
	}
	public void setIsMachEmpty(String isMachEmpty) {
		this.isMachEmpty = isMachEmpty;
	}
	public String getIsMachYZmobile() {
		return isMachYZmobile;
	}
	public void setIsMachYZmobile(String isMachYZmobile) {
		this.isMachYZmobile = isMachYZmobile;
	}
	public String getIsMachSmallNo() {
		return isMachSmallNo;
	}
	public void setIsMachSmallNo(String isMachSmallNo) {
		this.isMachSmallNo = isMachSmallNo;
	}
	public String getIsMachSZNo() {
		return isMachSZNo;
	}
	public void setIsMachSZNo(String isMachSZNo) {
		this.isMachSZNo = isMachSZNo;
	}
	public String getiUpdateDate() {
		return iUpdateDate;
	}
	public void setiUpdateDate(String iUpdateDate) {
		this.iUpdateDate = iUpdateDate;
	}
	public String getmUpdateDate() {
		return mUpdateDate;
	}
	public void setmUpdateDate(String mUpdateDate) {
		this.mUpdateDate = mUpdateDate;
	}
	public String getiRskDesc() {
		return iRskDesc;
	}
	public void setiRskDesc(String iRskDesc) {
		this.iRskDesc = iRskDesc;
	}
	public String getmRskDesc() {
		return mRskDesc;
	}
	public void setmRskDesc(String mRskDesc) {
		this.mRskDesc = mRskDesc;
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
