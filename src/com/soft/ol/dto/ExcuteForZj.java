package com.soft.ol.dto;

import java.io.Serializable;
import java.util.Date;

public class ExcuteForZj implements Serializable {

	/**
	 * 法院
	 */
	private String strCourt;

	/**
	 * 案件状态
	 */
	private String caseStatus;
	
	/**
	 * 立案日期
	 */
	private String excuteDate;
	
	/**
	 * 执行人
	 */
	private String ExecuteDSR;

	/**
	 * 被执行人
	 */
	private String ExecutedDSR;
	
	
	/**
	 * 案号
	 */
	private String caseId;
	
	/**
	 * 抓取日期
	 */
	private String createTime ;
	
	/**
	 * 来源
	 */
	private String dataFrom ;

	public String getStrCourt() {
		return strCourt;
	}

	public void setStrCourt(String strCourt) {
		this.strCourt = strCourt;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	public String getExcuteDate() {
		return excuteDate;
	}

	public void setExcuteDate(String excuteDate) {
		this.excuteDate = excuteDate;
	}

	public String getExecutedDSR() {
		return ExecutedDSR;
	}

	public void setExecutedDSR(String executedDSR) {
		ExecutedDSR = executedDSR;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}
	
	public String getExecuteDSR() {
		return ExecuteDSR;
	}

	public void setExecuteDSR(String executeDSR) {
		ExecuteDSR = executeDSR;
	}


}
