package com.soft.ol.dto;

import java.io.Serializable;
import java.util.Date;

public class Court implements Serializable {

	/**
	 * 法院
	 */
	private String strCourt;

	/**
	 * 法庭
	 */
	private String strTribunal;
	
	/**
	 * 开庭日期
	 */
	private String openDate;
	
	/**
	 * 排期日期
	 */
	private String arrDate;
	
	/**
	 * 案号
	 */
	private String caseId;
	
	/**
	 * 承办部门
	 */
	private String caseDept;
	
	/**
	 * 审判长
	 */
	private String judge;
	
	/**
	 * 案由
	 */
	private String caseContent;
	
	/**
	 * 原告
	 */
	private String plaintiff;
	
	/**
	 * 被告
	 */
	private String defendant;
	
	/**
	 * 抓取id
	 */
	private String id ;
	
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

	public String getStrTribunal() {
		return strTribunal;
	}
	public void setStrTribunal(String strTribunal) {
		this.strTribunal = strTribunal;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getArrDate() {
		return arrDate;
	}
	public void setArrDate(String arrDate) {
		this.arrDate = arrDate;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getCaseDept() {
		return caseDept;
	}
	public void setCaseDept(String caseDept) {
		this.caseDept = caseDept;
	}
	public String getJudge() {
		return judge;
	}
	public void setJudge(String judge) {
		this.judge = judge;
	}
	public String getCaseContent() {
		return caseContent;
	}
	public void setCaseContent(String caseContent) {
		this.caseContent = caseContent;
	}
	public String getPlaintiff() {
		return plaintiff;
	}
	public void setPlaintiff(String plaintiff) {
		this.plaintiff = plaintiff;
	}
	public String getDefendant() {
		return defendant;
	}
	public void setDefendant(String defendant) {
		this.defendant = defendant;
	}
	public String getDataFrom() {
		return dataFrom;
	}
	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
