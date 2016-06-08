package com.soft.jxl.bean;

import java.util.Date;

/**
 * 用户基本信息分析数据
 * @author lujf
 *
 */
public class AppCheck {
	
	private long id;
	//电话号码
	private String mobile;
	//身份证
	private String idCard;
	//名字
	private String name;
	//是否绑定运营商
	private String isBindOper;
	//是否绑定电商
	private String isBindEc;
	//是否绑定出行网站
	private String isBindTripweb;
	//是否绑定学信网
	private String isBindXxweb;
	//是否绑定公积金
	private String isBindPf;
	//是否提交登记号码信息
	private String isBindNuminfo;
	//身份证号码是否有效
	private String idcardIsVaild;
	//姓名是否与运营商数据匹配
	private String nameIsmatchOper;
	//身份证号码是否与运营商数据匹配
	private String idcardIsmatchOper;
	//姓名是否与学信网数据匹配
	private String nameIsmatchXxweb;
	//身份证号码是否与学信网数据匹配
	private String idccardIsmatchXxweb;
	//姓名是否与公积金数据匹配
	private String nameIsmatchPf;
	//身份证号码是否与公积金数据匹配
	private String idcardIsmatchPf;
	//申请人姓名+身份证是否出现在法院黑名单
	private String blistOfCourt;
	//申请人姓名+身份证是否出现在金融服务类机构黑名单
	private String blistOfBank;
	//申请人姓名+手机号码是否出现在金融服务类机构黑名单
	private String mobilblistOfBank;
	//朋友圈在哪里
	private String circleOfFs;
	//澳门电话通话情况
	private String macauCallInfo;
	//110通话情况
	private String callPoliceInfo;
	//律师号码通话情况
	private String callLawyerInfo;
	//法院号码通话情况
	private String callCourtInfo;
	//号码使用时间
	private String phoneUedDate;
	//彩票购买情况
	private String buyLotteryInfo;
	//居住地址定位
	private String address;
	//工作地址定位
	private String workAddress;
	//夜间活动情况
	private String nocturnalInfo;
	//贷款类号码联系情况
	private String loanNumberContact;
	//创建时间
	private Date createDate;
	
	private String json;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsBindOper() {
		return isBindOper;
	}

	public void setIsBindOper(String isBindOper) {
		this.isBindOper = isBindOper;
	}

	public String getIsBindEc() {
		return isBindEc;
	}

	public void setIsBindEc(String isBindEc) {
		this.isBindEc = isBindEc;
	}

	public String getIsBindTripweb() {
		return isBindTripweb;
	}

	public void setIsBindTripweb(String isBindTripweb) {
		this.isBindTripweb = isBindTripweb;
	}

	public String getIsBindXxweb() {
		return isBindXxweb;
	}

	public void setIsBindXxweb(String isBindXxweb) {
		this.isBindXxweb = isBindXxweb;
	}

	public String getIsBindPf() {
		return isBindPf;
	}

	public void setIsBindPf(String isBindPf) {
		this.isBindPf = isBindPf;
	}

	public String getIsBindNuminfo() {
		return isBindNuminfo;
	}

	public void setIsBindNuminfo(String isBindNuminfo) {
		this.isBindNuminfo = isBindNuminfo;
	}

	public String getIdcardIsVaild() {
		return idcardIsVaild;
	}

	public void setIdcardIsVaild(String idcardIsVaild) {
		this.idcardIsVaild = idcardIsVaild;
	}

	public String getNameIsmatchOper() {
		return nameIsmatchOper;
	}

	public void setNameIsmatchOper(String nameIsmatchOper) {
		this.nameIsmatchOper = nameIsmatchOper;
	}

	public String getIdcardIsmatchOper() {
		return idcardIsmatchOper;
	}

	public void setIdcardIsmatchOper(String idcardIsmatchOper) {
		this.idcardIsmatchOper = idcardIsmatchOper;
	}

	public String getNameIsmatchXxweb() {
		return nameIsmatchXxweb;
	}

	public void setNameIsmatchXxweb(String nameIsmatchXxweb) {
		this.nameIsmatchXxweb = nameIsmatchXxweb;
	}

	public String getIdccardIsmatchXxweb() {
		return idccardIsmatchXxweb;
	}

	public void setIdccardIsmatchXxweb(String idccardIsmatchXxweb) {
		this.idccardIsmatchXxweb = idccardIsmatchXxweb;
	}

	public String getNameIsmatchPf() {
		return nameIsmatchPf;
	}

	public void setNameIsmatchPf(String nameIsmatchPf) {
		this.nameIsmatchPf = nameIsmatchPf;
	}

	public String getIdcardIsmatchPf() {
		return idcardIsmatchPf;
	}

	public void setIdcardIsmatchPf(String idcardIsmatchPf) {
		this.idcardIsmatchPf = idcardIsmatchPf;
	}

	public String getBlistOfCourt() {
		return blistOfCourt;
	}

	public void setBlistOfCourt(String blistOfCourt) {
		this.blistOfCourt = blistOfCourt;
	}

	public String getBlistOfBank() {
		return blistOfBank;
	}

	public void setBlistOfBank(String blistOfBank) {
		this.blistOfBank = blistOfBank;
	}

	public String getMobilblistOfBank() {
		return mobilblistOfBank;
	}

	public void setMobilblistOfBank(String mobilblistOfBank) {
		this.mobilblistOfBank = mobilblistOfBank;
	}

 

	public String getCircleOfFs() {
		return circleOfFs;
	}

	public void setCircleOfFs(String circleOfFs) {
		this.circleOfFs = circleOfFs;
	}

	public String getMacauCallInfo() {
		return macauCallInfo;
	}

	public void setMacauCallInfo(String macauCallInfo) {
		this.macauCallInfo = macauCallInfo;
	}

	public String getCallPoliceInfo() {
		return callPoliceInfo;
	}

	public void setCallPoliceInfo(String callPoliceInfo) {
		this.callPoliceInfo = callPoliceInfo;
	}

	public String getCallLawyerInfo() {
		return callLawyerInfo;
	}

	public void setCallLawyerInfo(String callLawyerInfo) {
		this.callLawyerInfo = callLawyerInfo;
	}

	public String getCallCourtInfo() {
		return callCourtInfo;
	}

	public void setCallCourtInfo(String callCourtInfo) {
		this.callCourtInfo = callCourtInfo;
	}

	public String getPhoneUedDate() {
		return phoneUedDate;
	}

	public void setPhoneUedDate(String phoneUedDate) {
		this.phoneUedDate = phoneUedDate;
	}

	public String getBuyLotteryInfo() {
		return buyLotteryInfo;
	}

	public void setBuyLotteryInfo(String buyLotteryInfo) {
		this.buyLotteryInfo = buyLotteryInfo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getNocturnalInfo() {
		return nocturnalInfo;
	}

	public void setNocturnalInfo(String nocturnalInfo) {
		this.nocturnalInfo = nocturnalInfo;
	}

	public String getLoanNumberContact() {
		return loanNumberContact;
	}

	public void setLoanNumberContact(String loanNumberContact) {
		this.loanNumberContact = loanNumberContact;
	}

 
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	
	
}
