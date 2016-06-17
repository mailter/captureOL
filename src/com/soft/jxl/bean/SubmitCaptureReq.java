package com.soft.jxl.bean;

public class SubmitCaptureReq {
	//token
	private String token;
	//手机号
	private String account;
	//验证码
	private String password;
	//类型SUBMIT_CAPTCHA,RESEND_CAPTCHA
	private String type;
	//网站短信验证码
	private String captcha;
	
	private String website;
	
	private String success;
 
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
 
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	

}
