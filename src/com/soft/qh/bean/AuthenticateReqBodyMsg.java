package com.soft.qh.bean;

import com.soft.qh.Record;


/**
 * 好信鉴通请求
 * @param 
 * @return
 */
public class AuthenticateReqBodyMsg  implements Record{
	
	private String idNo ; //证件号码
	private String idType;//证件类型
	private String name;//主体名称
	private String address ;//联系地址
	private String mobileNo ;//手机号码
	private String company ;//工作单位
	private String refName ;//联系人
	private String refMobileNo ;//联系人手机号码
	private String carNo ;//车牌号
	private String engineNo ;//车牌号
	private String carFrameNum ;//车牌号
	private String needeQryDrvStatus ;//车牌号
	private String photo64 ;//相片
	private String areaCode;//行政区代码
	private String eductionBckgrd;//学历
	private String busiDesc;//业务描述
	private String reasonCode;//查询原因
	private String entityAuthCode;//信息主体授权码
	private String entityAuthDate;//信息主体授权时间
	private String seqNo =""; //序列号
	
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
	public String getRefMobileNo() {
		return refMobileNo;
	}
	public void setRefMobileNo(String refMobileNo) {
		this.refMobileNo = refMobileNo;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getCarFrameNum() {
		return carFrameNum;
	}
	public void setCarFrameNum(String carFrameNum) {
		this.carFrameNum = carFrameNum;
	}
	public String getNeedeQryDrvStatus() {
		return needeQryDrvStatus;
	}
	public void setNeedeQryDrvStatus(String needeQryDrvStatus) {
		this.needeQryDrvStatus = needeQryDrvStatus;
	}
	public String getPhoto64() {
		return photo64;
	}
	public void setPhoto64(String photo64) {
		this.photo64 = photo64;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getEductionBckgrd() {
		return eductionBckgrd;
	}
	public void setEductionBckgrd(String eductionBckgrd) {
		this.eductionBckgrd = eductionBckgrd;
	}
	public String getBusiDesc() {
		return busiDesc;
	}
	public void setBusiDesc(String busiDesc) {
		this.busiDesc = busiDesc;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getEntityAuthCode() {
		return entityAuthCode;
	}
	public void setEntityAuthCode(String entityAuthCode) {
		this.entityAuthCode = entityAuthCode;
	}
	public String getEntityAuthDate() {
		return entityAuthDate;
	}
	public void setEntityAuthDate(String entityAuthDate) {
		this.entityAuthDate = entityAuthDate;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

}
