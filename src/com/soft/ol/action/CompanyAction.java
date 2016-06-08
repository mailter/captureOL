package com.soft.ol.action;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.soft.jxl.service.JlxServiceImp;
import com.soft.ol.service.AuthenticateService;
import com.soft.qh.service.QhService;

public class CompanyAction extends ActionSupport {
	private static Logger log = (Logger) LogManager
			.getLogger(CompanyAction.class);
	private static final long serialVersionUID = 1L;

	private QhService qhService;
	
	private AuthenticateService authenticService;
	
	private JlxServiceImp jxlService;
	
	
	public void setJxlService(JlxServiceImp jxlService) {
		this.jxlService = jxlService;
	}

	public void setAuthenticService(AuthenticateService authenticService) {
		this.authenticService = authenticService;
	}

	int i = 1;
	
	public void setQhService(QhService qhService) {
		this.qhService = qhService;
	}


	public String areaCode;
	public String companyName;
	public String idNo;
	public String name;
	public String resonCd;
	public String mobileNo;
	public String remoteIP;
	public String cardCode;

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getRemoteIP() {
		return remoteIP;
	}

	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}

	public String getResonCd() {
		return resonCd;
	}

	public void setResonCd(String resonCd) {
		this.resonCd = resonCd;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

//	public void findCourtByName(){
//		log.info("=========interface===findCourtByName====start=======");
//		System.out.println("=========interface===findCourtByName====start=======");
//		System.out.println(areaCode);
//		System.out.println(companyName);
//		//spideService.courtThread(areaCode,companyName);
//		spideService.courtThread("330000","义乌市金联饰品加工厂");
//		
//		System.out.println("=========interface====findCourtByName===end=======");
//		log.info("=========interface====findCourtByName===end=======");
//	}
//	
//	public void findBrandByName(){
//		log.info("=========interface====findBrandByName===start=======");
//		System.out.println("=========interface===findBrandByName====start=======");
//		System.out.println(areaCode);
//		System.out.println(companyName);
//		brandService.captureBrand(companyName);
//		System.out.println("=========interface=====findBrandByName==end=======");
//		log.info("=========interface====findBrandByName===end=======");
//	}
	
//	public void lendCoustomerService(){
//		MessageHead messageHead = new MessageHead();
//		messageHead.setChnlId("qhcs-dcs");//固定
//		messageHead.setTransNo("6688998"+(i++));//自增
//		messageHead.setTransDate("2015-02-02 14:12:14");
//		messageHead.setAuthCode("CRT001A2");//固定
//		messageHead.setAuthDate("2015-12-02 14:12:14");
//		
//		MessageBody messageBody = new MessageBody();
//		
//		messageBody.setBatchNo("33adfsf323233");
//		
//		//好信常贷客
//		LendCoustomerReqBodyMsg lendCoustomerReqBodyMsg = new LendCoustomerReqBodyMsg();
//		lendCoustomerReqBodyMsg.setIdNo("410329197511045073");
//		lendCoustomerReqBodyMsg.setIdType("0");
//		lendCoustomerReqBodyMsg.setName("张三");
//		lendCoustomerReqBodyMsg.setEntityAuthCode("123qwe2122");
//		lendCoustomerReqBodyMsg.setEntityAuthDate("2015-12-11 12:22:23");
//		lendCoustomerReqBodyMsg.setSeqNo("120000");
//		List records = new ArrayList();
//		records.add(lendCoustomerReqBodyMsg);
//		messageBody.setRecords(records);
//		Message res = qhService.lendCoustomerService(messageHead, messageBody);	
//		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
//	}
	/**
	 * 聚信力接口
	 * @param
	 * @return
	 */
	public void executeJxl(){
		jxlService.accessReportData(name,idNo,mobileNo);
		//jxlService.accessReportData("蔡杭军","339011197809199014","18806756337");
	}
	
	/**
	 * 失信执行信息
	 * @param
	 * @return
	 */
	public void executeAuth(){
		
		//"王海军","4129251975"
		//authenticService.executeAuthenticateResult("","叶军","01","18806756337","",);
		authenticService.executeAuthenticateResult(idNo,name,resonCd,mobileNo,remoteIP,cardCode);
	}
	
	
}
