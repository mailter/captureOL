package com.soft.qh.bean;

import com.soft.qh.Record;


/**
 * 好信鉴通响应
 * @param 
 * @return
 */
public class AuthenticateResBodyMsg  implements Record{
	
	private String idNo ; //证件号码
	private String idType ;//证件类型
	private String name ;//主体名称
	private String mobileNo ;//手机号码
	private String seqNo ; //序列号
	private String isRealIdentity ;//是否真实身份
	private String isValidAddress;//是否本人真实活动地址
	private String addressType ;//地址类型
	private String isRealCompany ;//是否真实工作单位
	private String companySimDeg ;//单位匹配度分值
	private String appear1ThDate ;//单位最初出现时间
	private String appearLastDate ;//单位最近一次出现时间
	private String isOwnerMobile ;//手机验证结果
	private String ownerMobileStatus ;//手机状态
	private String useTimeScore ;//使用时间分数
	private String isExistRel;//是否存在关系
	private String relLevel ;//关系力度
	private String carChkResult;//车辆验证结果
	private String isAsyned ;//是否异步返回结果
	private String carTypeInc;//车型
	private String carFactoryInc;//厂牌
	private String carPrice;//新车购置价
	private String drvStatusQryId;//行驶证状态查询Id号
	private String houseChkResult;//车房屋验证结果
	private String hous;//房屋
	private String eDataDate ;//数据获取时间
	private String photoCmpResult ;//相片比对结果	
	private String photoSimDeg;//相片相似度
	private String isHighestEduBkg ;//是否本人真实最高学历
	private String dataDate ;//数据获取时间
	private String graduateSchool ;//毕业院校
	private String graduateMajor ;//毕业专业
	private String graduateYear ;//毕业年份
	private String errorInfo ;//错误信息
	private String isOwnerMobileII;//手机验证II结果
	private String ownerMobileStatusII;//手机状态II
	private String useTimeScoreII;//使用时间分数II
	
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
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getIsRealIdentity() {
		return isRealIdentity;
	}
	public void setIsRealIdentity(String isRealIdentity) {
		this.isRealIdentity = isRealIdentity;
	}
	public String getIsValidAddress() {
		return isValidAddress;
	}
	public void setIsValidAddress(String isValidAddress) {
		this.isValidAddress = isValidAddress;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getIsRealCompany() {
		return isRealCompany;
	}
	public void setIsRealCompany(String isRealCompany) {
		this.isRealCompany = isRealCompany;
	}
	public String getCompanySimDeg() {
		return companySimDeg;
	}
	public void setCompanySimDeg(String companySimDeg) {
		this.companySimDeg = companySimDeg;
	}
	public String getAppear1ThDate() {
		return appear1ThDate;
	}
	public void setAppear1ThDate(String appear1ThDate) {
		this.appear1ThDate = appear1ThDate;
	}
	public String getAppearLastDate() {
		return appearLastDate;
	}
	public void setAppearLastDate(String appearLastDate) {
		this.appearLastDate = appearLastDate;
	}
	public String getIsOwnerMobile() {
		return isOwnerMobile;
	}
	public void setIsOwnerMobile(String isOwnerMobile) {
		this.isOwnerMobile = isOwnerMobile;
	}
	public String getOwnerMobileStatus() {
		return ownerMobileStatus;
	}
	public void setOwnerMobileStatus(String ownerMobileStatus) {
		this.ownerMobileStatus = ownerMobileStatus;
	}
	public String getUseTimeScore() {
		return useTimeScore;
	}
	public void setUseTimeScore(String useTimeScore) {
		this.useTimeScore = useTimeScore;
	}
	public String getIsExistRel() {
		return isExistRel;
	}
	public void setIsExistRel(String isExistRel) {
		this.isExistRel = isExistRel;
	}
	public String getRelLevel() {
		return relLevel;
	}
	public void setRelLevel(String relLevel) {
		this.relLevel = relLevel;
	}
	public String getCarChkResult() {
		return carChkResult;
	}
	public void setCarChkResult(String carChkResult) {
		this.carChkResult = carChkResult;
	}
	public String getIsAsyned() {
		return isAsyned;
	}
	public void setIsAsyned(String isAsyned) {
		this.isAsyned = isAsyned;
	}
	public String getCarTypeInc() {
		return carTypeInc;
	}
	public void setCarTypeInc(String carTypeInc) {
		this.carTypeInc = carTypeInc;
	}
	public String getCarFactoryInc() {
		return carFactoryInc;
	}
	public void setCarFactoryInc(String carFactoryInc) {
		this.carFactoryInc = carFactoryInc;
	}
	public String getCarPrice() {
		return carPrice;
	}
	public void setCarPrice(String carPrice) {
		this.carPrice = carPrice;
	}
	public String getDrvStatusQryId() {
		return drvStatusQryId;
	}
	public void setDrvStatusQryId(String drvStatusQryId) {
		this.drvStatusQryId = drvStatusQryId;
	}
	public String getHouseChkResult() {
		return houseChkResult;
	}
	public void setHouseChkResult(String houseChkResult) {
		this.houseChkResult = houseChkResult;
	}
	public String getHous() {
		return hous;
	}
	public void setHous(String hous) {
		this.hous = hous;
	}
	public String geteDataDate() {
		return eDataDate;
	}
	public void seteDataDate(String eDataDate) {
		this.eDataDate = eDataDate;
	}
	public String getPhotoCmpResult() {
		return photoCmpResult;
	}
	public void setPhotoCmpResult(String photoCmpResult) {
		this.photoCmpResult = photoCmpResult;
	}
	public String getPhotoSimDeg() {
		return photoSimDeg;
	}
	public void setPhotoSimDeg(String photoSimDeg) {
		this.photoSimDeg = photoSimDeg;
	}
	public String getIsHighestEduBkg() {
		return isHighestEduBkg;
	}
	public void setIsHighestEduBkg(String isHighestEduBkg) {
		this.isHighestEduBkg = isHighestEduBkg;
	}
	public String getDataDate() {
		return dataDate;
	}
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	public String getGraduateSchool() {
		return graduateSchool;
	}
	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}
	public String getGraduateMajor() {
		return graduateMajor;
	}
	public void setGraduateMajor(String graduateMajor) {
		this.graduateMajor = graduateMajor;
	}
	public String getGraduateYear() {
		return graduateYear;
	}
	public void setGraduateYear(String graduateYear) {
		this.graduateYear = graduateYear;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public String getIsOwnerMobileII() {
		return isOwnerMobileII;
	}
	public void setIsOwnerMobileII(String isOwnerMobileII) {
		this.isOwnerMobileII = isOwnerMobileII;
	}
	public String getOwnerMobileStatusII() {
		return ownerMobileStatusII;
	}
	public void setOwnerMobileStatusII(String ownerMobileStatusII) {
		this.ownerMobileStatusII = ownerMobileStatusII;
	}
	public String getUseTimeScoreII() {
		return useTimeScoreII;
	}
	public void setUseTimeScoreII(String useTimeScoreII) {
		this.useTimeScoreII = useTimeScoreII;
	}
	

}
