package com.soft.jxl.bean;

import java.util.Date;

/**
 * 聚信立接口调用凭证
 * @author 001362
 * 2016年5月26日
 */
public class Token {
	//过期时间
	private String expiresIn;
	//授权token
	private String accessToken;
	//说明信息
	private String note;
	//创建时间
	private Date createTime;
	
	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 判断是否过期
	 * @author lujf
	 * @return
	 * @date 2016年5月26日
	 */
	public boolean expire(){
		//per表示token为永久性可用
		if(this.expiresIn.equals("per")){
			return false;
		}
		
		//当前时间
		long nowTime = new Date().getTime();
		//创建时间
		long createTimes = this.createTime.getTime();
		//过期时间
		long expiresTimes = Integer.parseInt(expiresIn)*3600*1000;
		 
		if(createTimes + expiresTimes > nowTime){
			return false;
		}
		
		return true;
	}
	
	
	
}
