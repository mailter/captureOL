package com.soft.qh.bean;

import com.soft.qh.Record;
/**
 * 信用卡评分返回消息体
 * @author lujf
 * 2016年6月2日
 */
public class BankCardScoreResBodyMsg implements Record{
	 //银行卡号
	 private String accountNo;
	 //序列号
	 private String seqNo;
	 //借贷标记
	 private String dcFlag;
	 //消费综合评分
	 private String summaryScore;
	 //卡状态得分表
	 private String cstScore;
	 //套现模型得分
	 private String cotScore;
	 //消费趋势得分
	 private String cntScore;
	 //消费能力得分
	 private String cnaScore;
	 //持卡人价值得分
	 private String chvScore;
	 //消费自由度得分
	 private String dsiScore;
	 // 风险得分
	 private String rskScore;
	 //钱包位置得分
	 private String wlpScore;
	 //消费偏好得分
	 private String cnpScore;
	 //套现模型分类
	 private String cotCluster;
	 //消费自由度分类
	 private String dsiCluster;
	 //风险得分分类
	 private String rskCluster;
	 //跨境得分
	 private String crbScore;
	 //跨境得分分类
	 private String crbCluster;
	 //错误代码
	 private String erCode;
	 //错误信息
	 private String erMsg;
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getDcFlag() {
		return dcFlag;
	}
	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}
	public String getSummaryScore() {
		return summaryScore;
	}
	public void setSummaryScore(String summaryScore) {
		this.summaryScore = summaryScore;
	}
	public String getCstScore() {
		return cstScore;
	}
	public void setCstScore(String cstScore) {
		this.cstScore = cstScore;
	}
	public String getCotScore() {
		return cotScore;
	}
	public void setCotScore(String cotScore) {
		this.cotScore = cotScore;
	}
	public String getCntScore() {
		return cntScore;
	}
	public void setCntScore(String cntScore) {
		this.cntScore = cntScore;
	}
	public String getCnaScore() {
		return cnaScore;
	}
	public void setCnaScore(String cnaScore) {
		this.cnaScore = cnaScore;
	}
	public String getChvScore() {
		return chvScore;
	}
	public void setChvScore(String chvScore) {
		this.chvScore = chvScore;
	}
	public String getDsiScore() {
		return dsiScore;
	}
	public void setDsiScore(String dsiScore) {
		this.dsiScore = dsiScore;
	}
	public String getRskScore() {
		return rskScore;
	}
	public void setRskScore(String rskScore) {
		this.rskScore = rskScore;
	}
	public String getWlpScore() {
		return wlpScore;
	}
	public void setWlpScore(String wlpScore) {
		this.wlpScore = wlpScore;
	}
	public String getCnpScore() {
		return cnpScore;
	}
	public void setCnpScore(String cnpScore) {
		this.cnpScore = cnpScore;
	}
	public String getCotCluster() {
		return cotCluster;
	}
	public void setCotCluster(String cotCluster) {
		this.cotCluster = cotCluster;
	}
	public String getDsiCluster() {
		return dsiCluster;
	}
	public void setDsiCluster(String dsiCluster) {
		this.dsiCluster = dsiCluster;
	}
	public String getRskCluster() {
		return rskCluster;
	}
	public void setRskCluster(String rskCluster) {
		this.rskCluster = rskCluster;
	}
	public String getCrbScore() {
		return crbScore;
	}
	public void setCrbScore(String crbScore) {
		this.crbScore = crbScore;
	}
	public String getCrbCluster() {
		return crbCluster;
	}
	public void setCrbCluster(String crbCluster) {
		this.crbCluster = crbCluster;
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
