/**
 * 
 */
package com.soft.ol.common;
/**
 * @author Administrator
 * 
 */
public abstract class BaseException extends Exception {

	protected String status; // 错误编码

	protected String message; // 错误信息

	protected String desc; // 错误描述

	public BaseException() {
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String desc, Throwable cause) {
		super(cause);
		this.desc = desc;
	}

	public BaseException(String errorCode, String errorMessage) {
		this.status = errorCode;
		this.message = errorMessage;
	}

	public BaseException(String errorCode, String errorMessage, Throwable cause) {
		super(cause);
		this.status = errorCode;
		this.message = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
