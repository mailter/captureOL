package com.soft.qh.bean;

import com.soft.qh.Record;
/**
 * 风险度返回消息体
 * @author lujf
 * 2016年6月1日
 */
public class RiskDegreeResBodyMsg implements Record{
	 //证件号码
	 private String idNo;
	 //证件类型
	 private String idType;
	 //主体名称
	 private String name;
	 //序列号
	 private String seqNo;
	 //来源代码
	 private String sourceId;
	 //风险得分
	 private String rskScore;
	 //风险标记
	 private String rskMark;
	 //业务发生时间
	 private String dataBuildTime;
	 //数据状态
	 private String dataStatus;
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
	public String getRskScore() {
		return rskScore;
	}
	public void setRskScore(String rskScore) {
		this.rskScore = rskScore;
	}
	public String getRskMark() {
		return rskMark;
	}
	public void setRskMark(String rskMark) {
		this.rskMark = rskMark;
	}
	public String getDataBuildTime() {
		return dataBuildTime;
	}
	public void setDataBuildTime(String dataBuildTime) {
		this.dataBuildTime = dataBuildTime;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
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
