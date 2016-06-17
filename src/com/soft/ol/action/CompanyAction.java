package com.soft.ol.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

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

	public String idNo;
	public String name;
	public String mobileNo;
	//前海
	public String resonCd;
	public String remoteIP;
	public String cardCode;
	public String edu;
	public String company;
	//聚信力接口
	public String password;
	public String token;
	public String website;
	public String captcha;


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

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
	

	/**
	 * 聚信力接口
	 * @param
	 * @return
	 */
	public void executeJxl(){
		try {
		log.info("=========interface===executeJxl====start=======");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		String strJson =jxlService.submitCapture(name, idNo, mobileNo, password, token, website, captcha);
		log.info("=========strJson:======="+strJson);	
		response.getWriter().write(strJson);		
		log.info("=========interface===executeJxl====end=======");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
	}
	
	
	/**
	 * 重置密码
	 * @param
	 * @return
	 */
	public void resetPassword(){
		
		log.info("=========interface===resetPassword====start=======");
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			String result = jxlService.resetPassword(token,name,idNo,mobileNo,password,captcha,website,null,null,null);
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		log.info("=========interface===resetPassword====end=======");
	}
	
	
	/**
	 * 失信执行信息
	 * @param
	 * @return
	 */
	public void executeAuth(){
		
		log.info("=========interface===executeAuth====start=======");
		authenticService.executeAuthenticateResult(idNo,name,resonCd,mobileNo,remoteIP,cardCode,company,edu);
		log.info("=========interface===executeAuth====end=======");
	}

	
//	/**
//	 * 接口调试方法
//	 * @param
//	 * @return
//	 */
//	public void executeBank(){
//		
//		//"王海军","4129251975"
//	
//		log.info("=========interface===executeAuth====start=======");
//		authenticService.callQianHaiCreditCard(idNo,name,cardCode,resonCd,mobileNo);
//		
//		log.info("=========interface===executeAuth====end=======");
//	}
//	
}
