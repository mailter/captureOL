package com.soft.ol.dto;

public class Customer { 
	
	private String customerId ="";
	private String customerName ="";
	private String customerCerCode ="";
	//审核阶段状态
	private String customerStatus ="";
	//审核状态
	private String authenticateStatus ="";
	
	private String mobileNo="";

	private String authtype ="";
	
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAuthtype() {
		return authtype;
	}
	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerCerCode() {
		return customerCerCode;
	}
	public void setCustomerCerCode(String customerCerCode) {
		this.customerCerCode = customerCerCode;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	public String getAuthenticateStatus() {
		return authenticateStatus;
	}
	public void setAuthenticateStatus(String authenticateStatus) {
		this.authenticateStatus = authenticateStatus;
	}

}
	