package com.soft.ol.dto;

import java.io.Serializable;

public class ExcuteCase implements Serializable {
	
	private String spideId=null;
	
	private String caseCode=null;

	private String caseState=null;

	private String execCourtName =null;
	
	private String execMoney =null;
	
	private String partyCardNum=null;
	
	private String pname=null;
	
	private String caseCreateTime=null;
	
	private String attentionNum ="0";
	
	private String updateId=null;
	
	private String type = "0";
	
	public String getSpideId() {
		return spideId;
	}

	public void setSpideId(String spideId) {
		this.spideId = spideId;
	}

	public String getCaseCode() {
		return caseCode;
	}

	public void setCaseCode(String caseCode) {
		this.caseCode = caseCode;
	}

	public String getCaseState() {
		return caseState;
	}

	public void setCaseState(String caseState) {
		this.caseState = caseState;
	}

	public String getExecCourtName() {
		return execCourtName;
	}

	public void setExecCourtName(String execCourtName) {
		this.execCourtName = execCourtName;
	}

	public String getExecMoney() {
		return execMoney;
	}

	public void setExecMoney(String execMoney) {
		this.execMoney = execMoney;
	}

	public String getPartyCardNum() {
		return partyCardNum;
	}

	public void setPartyCardNum(String partyCardNum) {
		this.partyCardNum = partyCardNum;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getCaseCreateTime() {
		return caseCreateTime;
	}

	public void setCaseCreateTime(String caseCreateTime) {
		this.caseCreateTime = caseCreateTime;
	}
	
	public String getAttentionNum() {
		return attentionNum;
	}

	public void setAttentionNum(String attentionNum) {
		this.attentionNum = attentionNum;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	
}
