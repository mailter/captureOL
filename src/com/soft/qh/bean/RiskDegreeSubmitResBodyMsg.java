package com.soft.qh.bean;

import com.soft.qh.Record;

/**
 * 风险度报送返回消息体
 * @author lujf
 * 2016年6月1日
 */
public class RiskDegreeSubmitResBodyMsg  implements Record{
	//序列号
	private String seqNo;
	//错误代码
	private String erCode;
	//错误信息
	private String 	erMsg;

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
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
