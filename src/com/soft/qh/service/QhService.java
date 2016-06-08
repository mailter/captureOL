package com.soft.qh.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.oracle.jrockit.jfr.ValueDefinition;
import com.soft.http.HttpConnect;
import com.soft.http.bean.RequestHead;
import com.soft.qh.auth.DataSecurityUtil;
import com.soft.qh.bean.AddressReqBodyMsg;
import com.soft.qh.bean.AddressResBodyMsg;
import com.soft.qh.bean.AuthenticateReqBodyMsg;
import com.soft.qh.bean.AuthenticateResBodyMsg;
import com.soft.qh.bean.BankCardScoreResBodyMsg;
import com.soft.qh.bean.CheatResBodyMsg;
import com.soft.qh.bean.GoodReliabilityReqBodyMsg;
import com.soft.qh.bean.GoodReliabilityResBodyMsg;
import com.soft.qh.bean.LendCoustomerReqBodyMsg;
import com.soft.qh.bean.LendCoustomerResBodyMsg;
import com.soft.qh.bean.MessageBody;
import com.soft.qh.bean.MessageHead;
import com.soft.qh.bean.RiskDegreeReqBodyMsg;
import com.soft.qh.bean.RiskDegreeResBodyMsg;
import com.soft.qh.bean.RiskDegreeSubmitResBodyMsg;
import com.soft.qh.bean.SecurityInfo;
import com.soft.qh.bean.UserInfo;
import com.soft.util.JSONUtils;

import org.apache.http.HttpException;
import org.apache.http.cookie.Cookie;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.soft.qh.bean.Message;
import com.sun.jndi.cosnaming.IiopUrl.Address;
import com.soft.ol.action.CompanyAction;
import com.soft.ol.service.QhEntityService;
/**
 * 前海接口实现
 * @author lujf
 * 2016年5月27日
 */
public class QhService implements IQhService{
	
	private static Logger log = (Logger) LogManager.getLogger(CompanyAction.class);
	
	private HttpConnect httpConnect = new HttpConnect();	//接口用户信息
	
	private UserInfo userInfo;
	//服务地址
	private String serviceHost;//="https://test-qhzx.pingan.com.cn:5443/";
	//数据加密密码
	private String keyStr;// = "SK803@!QLF-D25WEDA5E52DA";
	//加密方式
	private String transformation;// = "DESede/ECB/PKCS5Padding";
	//证书地址
	private String keyStorePath;// = "D:\\workspace\\接口实现\\前海接口\\stg证书\\credoo_stg.jks";
	
	private String certificatePath;// = "D:\\workspace\\接口实现\\前海接口\\stg证书\\credoo_stg.cer";
	//别名
	private String alias;// = "signKey";
	//密码
	private String password;// = "qhzx_stg";
	//所请求算法的标准名称
	private String algorithm;// = "SHA1WithRSA";
	
	
	private QhEntityService qhEntityService;
	
	
	
	public void setQhEntityService(QhEntityService qhEntityService) {
		this.qhEntityService = qhEntityService;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	
	
	public void setHttpConnect(HttpConnect httpConnect) {
		this.httpConnect = httpConnect;
	}

	public void setServiceHost(String serviceHost) {
		this.serviceHost = serviceHost;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}

	public void setTransformation(String transformation) {
		this.transformation = transformation;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * 发送消息
	 * @author lujf
	 * @param url
	 * @param message
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 * @date 2016年5月27日
	 */
	protected String sendMessage(String url,String message) throws HttpException, IOException{
		List<Cookie>cookies = new ArrayList<Cookie>();
		List<RequestHead> requestHeads = new ArrayList<RequestHead>();
		 requestHeads.add(new RequestHead("Content-Type", "application/json;charset=utf-8"));
		String result = (String)httpConnect.request(url, message, "string", "UTF-8", cookies, requestHeads);
		return result;
	}
	
	/**
	 * 
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @return
	 * @throws Exception
	 * @date 2016年5月27日
	 */
	public  String  createReqMsg(MessageHead messageHead,MessageBody messageBody) throws Exception{
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		buf.append("\"header\":");
		String heads = JSONUtils.toJSONString(messageHead);
		buf.append(JSONUtils.removeFields(heads,new String[]{"rtCode","rtMsg"}));
		buf.append(",");
		String encBusiData = DataSecurityUtil.encrypt(JSONUtils.toJSONStringExpNull(messageBody).getBytes("UTF-8"), keyStr,transformation);
		buf.append("\"busiData\":");
		buf.append("\"");
		buf.append(encBusiData);
		buf.append("\"");
		buf.append(",");
		buf.append("\"securityInfo\":");
		String sigValue = DataSecurityUtil.signData(encBusiData, keyStorePath, alias, password, algorithm);
	    String pwd = DataSecurityUtil.digest(userInfo.getUserPassword().getBytes());
	    buf.append("{\"signatureValue\":\"" + sigValue
                + "\",\"userName\":\""+userInfo.getUserName()+"\",\"userPassword\":\"" + pwd + "\"}");
		buf.append("}");
		return buf.toString();
	}
	
	/**
	 * 接口调用返回信息处理
	 * @author lujf
	 * @param clazz
	 * @param messageHead
	 * @param messageBody
	 * @return
	 * @throws Exception 
	 * @date 2016年5月27日
	 */
	public Message getResMessage(MessageHead messageHead,MessageBody messageBody,Class clazz,String service) throws Exception{
		messageHead.setOrgCode(userInfo.getOrgCode());
		String reqMessage = createReqMsg(messageHead, messageBody); 
		String res = sendMessage(serviceHost+service, reqMessage);
		if(!"".equals(res)){
			MessageHead resHead = JSONUtils.toBean(JSONUtils.getStringByKey(res,"header"), MessageHead.class);
			if("E000000".equals(resHead.getRtCode())){
				//验证签名合法性
				DataSecurityUtil.verifyData(certificatePath, JSONUtils.getStringByKey(res,"busiData"), JSONUtils.getStringByKey(JSONUtils.getStringByKey(res,"securityInfo"),"signatureValue"), algorithm);
				String busiDate = DataSecurityUtil.decrypt(JSONUtils.getStringByKey(res,"busiData"), keyStr, transformation);
				Message message = new Message();
				message.setMessageBody(JSONUtils.toBean(busiDate, MessageBody.class, "records", clazz));
				message.setMessageHead(resHead);
				return message;
			}else{
				throw new Exception(resHead.getRtCode());
			}		
		}
		return null;
	}
	
	
	
	/**
	 * 好信度数据数据接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 */
	public Message goodReliabilityService(MessageHead messageHead,MessageBody messageBody,String customerId){
		Message message = null;
		try {
			message = this.getResMessage(messageHead, messageBody, GoodReliabilityResBodyMsg.class, GOOD_RELIABILITY_SERVICE);
			//插入数据库
			qhEntityService.insertgoodreliability(message,customerId);
			//erCode E000000:成功 
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return message;

	}
	
	
	/**
	 * 地址通数据数据接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 */
	public Message addressService(MessageHead messageHead,MessageBody messageBody,String customerId){
		Message message = null;
		try {
			message = this.getResMessage(messageHead, messageBody, AddressResBodyMsg.class, ADDRESS_T_SERVICE);
			qhEntityService.insertaddress(message,customerId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		//erCode  E000000:成功 
		return message;
	}
	
	
	/**
	 * 风险度提升接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 * @return
	 */
	public Message riskDegreeService(MessageHead messageHead,MessageBody messageBody,String customerId){
		Message message = null;
		try {
			message = this.getResMessage(messageHead, messageBody, RiskDegreeResBodyMsg.class, RISK_DEGREE_SERVICE);
			qhEntityService.insertriskDegree(message,customerId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		//E000000:成功  E000996:未找到数据
		
		return message;
	}
	
	
	/**
	 * 常贷客数据数据接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 * @return
	 */
	public Message lendCoustomerService(MessageHead messageHead,MessageBody messageBody,String customerId){
		
		Message message = null;
		try {
			message = this.getResMessage(messageHead, messageBody, LendCoustomerResBodyMsg.class, LENDCOUSTOMER_SERVICE);
			qhEntityService.insertlendCoustomer(message,customerId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		//rtCode E000000:成功 
		return message;
		
	}
	
	
	
	/**
	 * 好信一鉴通数据接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 * @return
	 */
	public Message	authenticateService(MessageHead messageHead,MessageBody messageBody,String customerId){
		Message message = null;
		try {
			message = this.getResMessage(messageHead, messageBody, AuthenticateResBodyMsg.class, AUTHENTICATE_SERVICE);
			qhEntityService.insertauthenticate(message,customerId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return message;
	}
	
	/**
	 * 风险度报送接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @param securityInfo

	 * @date 2016年06月02日
	 * @return
	 */
	public Message riskDegreeSubmitService(MessageHead messageHead,MessageBody messageBody,String customerId){
		Message message = null;
		try {
			message = this.getResMessage(messageHead, messageBody, RiskDegreeSubmitResBodyMsg.class, RISK_DEGREE_SUBMIT_SERVICE);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		//E000000:成功  E000996:未找到数据
		
		return message;
	}
	
	/**
	 * 好信欺诈数据接口
	 * @param messageHead
	 * @param messageBody
	 * @return
	 */
	public Message cheatService(MessageHead messageHead,MessageBody messageBody,String customerId){
		Message message = null;
		try {
			message = this.getResMessage(messageHead, messageBody, CheatResBodyMsg.class, CHEAT_SERVICE);
			qhEntityService.insertcheat(message,customerId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return message;
	}
	
	/**
	 * 银行卡评分接口
	 * @param messageHead
	 * @param messageBody
	 * @return
	 */
	public Message bankCardScoreService(MessageHead messageHead,MessageBody messageBody,String customerId){
		Message message = null;
		try {
			message = this.getResMessage(messageHead, messageBody, BankCardScoreResBodyMsg.class, BANK_CARD_SCORE_SERVICE);
			qhEntityService.insertbankcardscore(message,customerId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return message;
	}
	 
	
	 
}
