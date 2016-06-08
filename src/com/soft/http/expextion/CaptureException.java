package com.soft.http.expextion;
/**
 * 抓取异常类
 * @author lujf
 *
 */
public class CaptureException extends RuntimeException{
	//错误代码
	private String errorCode;
	//错误详细信息
	private String detailMsg;
	//信息描述
	private String message;
	
	public CaptureException(String detailMsg,String message){
		this.detailMsg = detailMsg;
		this.message = message;
	}
	
	public CaptureException(String errorCode ,String detailMsg,String message){
		this.errorCode = errorCode;
		this.detailMsg = detailMsg;
		this.message = message;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getDetailMsg() {
		return detailMsg;
	}
	public void setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
