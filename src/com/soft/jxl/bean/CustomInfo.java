package com.soft.jxl.bean;

import java.util.Date;

/**
 * 客户信息
* @ClassName: CustomInfo 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Lujf
* @date 2016年6月14日 下午3:05:37
 */
public class CustomInfo {
	private String name;
	private String idcard;
	private String phone;
	private int count;
	private long nextTime;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public CustomInfo(String name,String idcard,String phone){
		this.name = name;
		this.idcard = idcard;
		this.phone = phone;
		this.setNextTime();
	}
	
	/**
	 * 设置下次运行时间
	* @Title: setNextTime 
	* @Description:
	* @param
	* @return void
	* @author Lujf
	* @2016年6月14日@下午3:11:24
	* @throws
	 */
 	public void setNextTime(){
 		long now = new Date().getTime() ;
 		if(count == 0){
 			this.nextTime = now+(2*60*1000);
 		}else if(count ==1){
 			this.nextTime = now+(4*60*1000);
 		}else if(count == 2){
 			this.nextTime = now+(6*60*1000);
 		}
 		count = count+1;
 	}
 	
 	/**
 	 * 判断是否过期
 	* @Title: isExpire 
 	* @Description:
 	* @param
 	* @return boolean
 	* @author Lujf	
 	* @2016年6月14日@下午3:18:52
 	* @throws
 	 */
 	public boolean isExpire(){
 		long now = new Date().getTime();
 		if(now > this.nextTime){
 			return true;
 		}
 		return false;
 	}
}
