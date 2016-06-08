package com.soft.qh.bean;
/**
 * 授权信息
 * @author lujf
 * 2016年5月27日
 */
public class SecurityInfo {
	//签名
	private String signatureValue;
	//虚拟用户名
	private String userName;
	//密码
	private String userPassword;
	
	public String getSignatureValue() {
		return signatureValue;
	}
	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
}
