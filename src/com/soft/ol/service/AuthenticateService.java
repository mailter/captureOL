package com.soft.ol.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.soft.ol.common.Util;
import com.soft.ol.dao.AuthenticateDao;
import com.soft.ol.dto.Customer;
import com.soft.qh.Record;
import com.soft.qh.bean.AuthenticateReqBodyMsg;
import com.soft.qh.bean.AuthenticateResBodyMsg;
import com.soft.qh.bean.BankCardScoreReqBodyMsg;
import com.soft.qh.bean.BankCardScoreResBodyMsg;
import com.soft.qh.bean.CheatReqBodyMsg;
import com.soft.qh.bean.CheatResBodyMsg;
import com.soft.qh.bean.GoodReliabilityReqBodyMsg;
import com.soft.qh.bean.GoodReliabilityResBodyMsg;
import com.soft.qh.bean.LendCoustomerReqBodyMsg;
import com.soft.qh.bean.LendCoustomerResBodyMsg;
import com.soft.qh.bean.Message;
import com.soft.qh.bean.MessageBody;
import com.soft.qh.bean.MessageHead;
import com.soft.qh.bean.RiskDegreeReqBodyMsg;
import com.soft.qh.bean.RiskDegreeResBodyMsg;
import com.soft.qh.service.IQhService;
import com.soft.util.JSONUtils;


public class AuthenticateService {
	
	private static Logger log = (Logger) LogManager.getLogger(AuthenticateService.class);
	private static final long serialVersionUID = 1L;
	
	private static final String SUCCESS_CODE ="E000000";
	private static final String ERROR_CODE ="E000996";
	private static final String contEdu ="E000996";
	
	private ShixinOl shixinService;
	private ZhixinOl zhixinService;
	
	private IQhService qhService;
	
	public AuthenticateDao autheticDaoHandl;
	
	String customerId ="";
	
	private int credooScore ;//好信度分值
	private int rskScore;//好信度欺诈
//	private String authsubProduct;//鉴通子项目
//	
//	public String getAuthsubProduct() {
//		return authsubProduct;
//	}
//
//	public void setAuthsubProduct(String authsubProduct) {
//		this.authsubProduct = authsubProduct;
//	}

	public int getCredooScore() {
		return credooScore;
	}

	public void setCredooScore(int credooScore) {
		this.credooScore = credooScore;
	}

	public int getRskScore() {
		return rskScore;
	}

	public void setRskScore(int rskScore) {
		this.rskScore = rskScore;
	}

	public void setQhService(IQhService qhService) {
		this.qhService = qhService;
	}

	public AuthenticateDao getAutheticDaoHandl() {
		return autheticDaoHandl;
	}
	public void setAutheticDaoHandl(AuthenticateDao autheticDaoHandl) {
		this.autheticDaoHandl = autheticDaoHandl;
	}

	public void setZhixinService(ZhixinOl zhixinService) {
		this.zhixinService = zhixinService;
	}

	public void setShixinService(ShixinOl shixinService) {
		this.shixinService = shixinService;
	}

	public void executeAuthenticateResult(String idNo,String name,String resonCd,String mobileNo,String remoteIP,String cardCode,String company,String edu){
		
		boolean blnFlag =true;
		String strStauts ="PENDINZX";
		
		ArrayList<Customer> arrayList = new ArrayList<Customer>();

		arrayList=autheticDaoHandl.queryForCustomer();
		
		System.out.println("====size========="+arrayList.size());
		//
		for (int i =0; i < arrayList.size(); i++){
			
			customerId =arrayList.get(i).getCustomerId();
			String customerStatus =arrayList.get(i).getCustomerStatus();
			//身份证号
			if ("".equals(idNo) || idNo ==null){
				idNo =arrayList.get(i).getCustomerCerCode();
			}
			//姓名
			if ("".equals(name) || name ==null){
				name =arrayList.get(i).getCustomerName();
			}
			//手机号
			if ("".equals(mobileNo) || mobileNo ==null){
				mobileNo =arrayList.get(i).getMobileNo();
			}
			
			if ("PENDINSX".equals(customerStatus.trim()) || "PENDINZX".equals(customerStatus.trim())){
				
				log.info("=====执行查询=====start==");
				//执行查询
				String reString = zhixinService.getZhixin(name,idNo);
				
				if ("执行".equals(reString)){
					blnFlag=false;
				}

				System.out.println("========执行查询============"+blnFlag);
				if (!blnFlag){
					updateStatus(customerId,"OVER", "2","PENDINZX");
					continue;
				}else{
					System.out.println("========PENDINSX====");
					updateStatus(customerId,"PENDINSX", "1","PENDINSX");
					strStauts="PENDINSX";
				}
				log.info("=====执行查询===== end==");
				
				log.info("=====失信查询=====start==");
				//失信
				String sxString =shixinService.getShixin(name,idNo);
				
				if ("失信".equals(sxString)){
					blnFlag=false;
				}
				System.out.println("========失信查询============"+blnFlag);
				if (!blnFlag){
					updateStatus(customerId,"OVER", "2","PENDINSX");
					continue;
				}else{
					System.out.println("========PENDINQH====");
					updateStatus(customerId,"PENDINQH", "1","PENDINQH");
					strStauts="PENDINQH";
				}
				log.info("=====失信查询=====end==");
			}

			if (blnFlag){
//				//前海接口
				//风险度提示interface
				log.info("=====风险度提示=====start==");
				if ("PENDINQH".equals(strStauts) || "PENDINQH_RISK".equals(customerStatus.trim())){
					if (!callQianHaiRisk(idNo,name,resonCd)){
						continue;
					}
				}
				log.info("=====风险度提示=====end==");
				//好信度
				log.info("=====好信度=====start==");
				if ("PENDINQH".equals(strStauts) || "PENDINQH_CREDIT".equals(customerStatus.trim())){
					if (!callQianHaiCredit(idNo,name,resonCd,mobileNo,cardCode)){
						continue;
					}
				}
				log.info("=====好信度=====end==");
				//好信度欺诈
				log.info("=====好信度欺诈=====start==");
				if ("PENDINQH".equals(strStauts) || "PENDINQH_CHEAT".equals(customerStatus.trim())){
					if (!callQianHaiFake(idNo,name,resonCd,mobileNo,remoteIP)){
						continue;
					}
				}
				log.info("=====好信度欺诈=====end==");
//				//鉴通
//				log.info("=====鉴通=====start==");
//				if ("PENDINQH".equals(strStauts) || "PENDINQH_AUTHEN".equals(customerStatus.trim())){
//					if (!callQianHaiAuthen(idNo,name,resonCd,mobileNo,company,edu)){
//						continue;
//					}
//				}
//				log.info("=====鉴通=====end==");
				//常贷客
				log.info("=====常贷客=====start==");
				if ("PENDINQH".equals(strStauts) || "PENDINQH_LEND".equals(customerStatus.trim())){
					if (!callQianHaiLender(idNo,name)){
						continue;
					}
				}
				log.info("=====常贷客=====end==");
				
				log.info("=====银行卡评分=====start==");
				//银行卡评分
				if ("PENDINQH".equals(strStauts) || "PENDINQH_CREDITCARD".equals(customerStatus.trim())){
					if (!callQianHaiCreditCard(idNo,name,cardCode,resonCd,mobileNo)){
						continue;
					}
				}
				log.info("=====银行卡评分=====end==");
				//执行结束
				updateStatus(customerId,"OVER", "1","ALL");
			}
		}
	}
	
	 /**
     * 更新状态
     * @param  null
     * @param  null
     * @return time
     */
	
	private void updateStatus(String customerId,String custatus,String authStatus,String authType){
		
		Customer customer= new Customer();
		
		customer.setCustomerId(customerId);
		customer.setCustomerStatus(custatus);
		customer.setAuthtype(authType);
		customer.setAuthenticateStatus(authStatus);
		
		System.out.println(customer.getCustomerId());
		System.out.println(customer.getCustomerStatus());
		System.out.println(customer.getAuthtype());
		System.out.println(customer.getAuthenticateStatus());
		
		log.info("=====改变状态====start==="+customer.getCustomerStatus());
		if (!"OVER".equals(custatus)){
			autheticDaoHandl.updateForCustomerStatus(customer);
		}else{
			
			autheticDaoHandl.callBlackProcedue(customer);
			log.info("=====成功===");
		}
		log.info("=====改变状态====end==="+customer.getCustomerStatus());
	}
	
	 /**
     * 9004-风险度提示接口
     * @param  idNo
     * @param  name
     * @param  resonCd
     * @return time
     */
	
	private boolean callQianHaiRisk(String idNo,String name,String resonCd){
		log.info("=====callQianHaiRisk=====Start==");
		Util util = new Util();
		String transNo =util.getDate()+getTranNo();
		String tranDate =util.getSysDate("yyyy-MM-dd HH:mm:ss");
		boolean flag =true;
		//autheticDaoHandl.
		MessageHead messageHead = new MessageHead();
		//messageHead.setChnlId("qhcs-dcs");//固定
		//messageHead.setTransNo(transNo);//自增
		messageHead.setTransNo(transNo);//自增
		//messageHead.setTransDate(tranDate);
		messageHead.setTransDate(tranDate);
		//messageHead.setAuthCode("CRT001A2");//固定
		messageHead.setAuthDate(util.getSysDate("yyyy-MM-dd"));
		
		MessageBody messageBody= new MessageBody();
		messageBody.setBatchNo(getBathNo());
		RiskDegreeReqBodyMsg RiskReqBodyMsg = new RiskDegreeReqBodyMsg();
		RiskReqBodyMsg.setIdNo(idNo);
		//RiskReqBodyMsg.setIdNo("410329197511045073");
		RiskReqBodyMsg.setIdType("0");
		RiskReqBodyMsg.setName(name);
		//RiskReqBodyMsg.setName("张三");
		RiskReqBodyMsg.setReasonCode(resonCd);//01--贷款审批
		RiskReqBodyMsg.setEntityAuthCode("123qwe2122");
		RiskReqBodyMsg.setEntityAuthDate(util.getSysDate("yyyy-MM-dd"));
		RiskReqBodyMsg.setSeqNo(transNo);
		List records = new ArrayList();
		records.add(RiskReqBodyMsg);
		messageBody.setRecords(records);
		Message res = qhService.riskDegreeService(messageHead, messageBody,customerId);

		ArrayList<Record> list = new ArrayList<Record>();
		list=(ArrayList<Record>) res.getMessageBody().getRecords();
		RiskDegreeResBodyMsg riskRes =null;
		String errCode ="";
		if (res != null){
			String transCode = res.getMessageHead().getRtCode();
			for (int i =0;i<list.size(); i++){
				riskRes =(RiskDegreeResBodyMsg)list.get(i);
				errCode=riskRes.getErCode();
			}
			
			if (SUCCESS_CODE.equals(transCode)){
				if (SUCCESS_CODE.equals(errCode)){
					if (riskRes.getRskMark() != null && !"".equals(riskRes.getRskMark())){
						if (!riskRes.getRskMark().contains("99")){
							updateStatus(customerId,"OVER","2","PENDINQH_RISK");
							flag=false;
						}
					}				
				}	
			}else{
				flag =false;
			}
		}
		
		if (!flag){
			updateStatus(customerId,"OVER","2","PENDINQH_RISK");
		}else{
			updateStatus(customerId,"PENDINQH_RISK","1","PENDINQH_RISK");
		}
		//updateStatus(customerId,"PENDINQH_RISK","1","PENDINQH_RISK");
			//flag =false;

//		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
//		log.info(JSONUtils.toJSONString(res.getMessageBody()));
		log.info("=====callQianHaiRisk=====end==");
		return flag;
	}
	
	 /**
     *  好信度数据
     * @param  idNo
     * @param  name
     * @param  resonCd
     * @param  mobileNo
     * @return time
     */
	
	private boolean callQianHaiCredit(String idNo,String name,String resonCd,String mobileNo,String cardCode){
		
		log.info("=====callQianHaiCredit=====Start==");
		boolean flag =true;
		Util util = new Util();
		String transNo =util.getDate()+getTranNo();
		String tranDate =util.getSysDate("yyyy-MM-dd HH:mm:ss");
		//autheticDaoHandl.
		MessageHead messageHead = new MessageHead();
		//messageHead.setChnlId("qhcs-dcs");//固定
		messageHead.setTransNo(transNo);//自增
		messageHead.setTransDate(tranDate);
		//messageHead.setAuthCode("CRT001A2");//固定
		messageHead.setAuthDate(tranDate);
		
		MessageBody messageBody= new MessageBody();
		messageBody.setBatchNo(getBathNo());
		//好信度
		GoodReliabilityReqBodyMsg creditReqBodyMsg = new GoodReliabilityReqBodyMsg();
		creditReqBodyMsg.setIdNo(idNo);
		//RiskReqBodyMsg.setIdNo("410329197511045073");
		creditReqBodyMsg.setIdType("0");
		creditReqBodyMsg.setName(name);
		creditReqBodyMsg.setMobileNo(mobileNo);
		//RiskReqBodyMsg.setName("张三");
		creditReqBodyMsg.setCardNo(cardCode);
		creditReqBodyMsg.setReasonNo(resonCd);
		creditReqBodyMsg.setEntityAuthCode("123qwe2122");
		creditReqBodyMsg.setEntityAuthDate(util.getSysDate("yyyy-MM-dd"));
		creditReqBodyMsg.setSeqNo(transNo);
		List records = new ArrayList();
		records.add(creditReqBodyMsg);
		messageBody.setRecords(records);
		Message res = qhService.goodReliabilityService(messageHead, messageBody,customerId);

		ArrayList<Record> list = new ArrayList<Record>();
		list=(ArrayList<Record>) res.getMessageBody().getRecords();
		GoodReliabilityResBodyMsg creditRes =null;
		String errCode ="";
		if (res != null){
			String transCode = res.getMessageHead().getRtCode();
			for (int i =0;i<list.size(); i++){
				creditRes =(GoodReliabilityResBodyMsg)list.get(i);
				errCode=creditRes.getErCode();
			}
			
			if (SUCCESS_CODE.equals(transCode)){
				if (SUCCESS_CODE.equals(errCode)){
					if (creditRes.getCredooScore() != null && !"".equals(creditRes.getCredooScore())
							&& !"-1".equals(creditRes.getCredooScore())){
						String score =creditRes.getCredooScore();
						if (Integer.parseInt(score) < credooScore ){
							
							flag=false;
						}
					}				
				}	
			}else{
				flag =false;
			}
		}
		
		if (!flag){
			updateStatus(customerId,"OVER","2","PENDINQH_CREDIT");
		}else{
			updateStatus(customerId,"PENDINQH_CREDIT","1","PENDINQH_CREDIT");
		}
		
		//flag =false;
//		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
//		log.info(JSONUtils.toJSONString(res.getMessageBody()));
		log.info("=====callQianHaiCredit=====end==");
		return flag;
	}
	
	 /**
     *  好信欺诈度
     * @param  idNo
     * @param  name
     * @param  resonCd
     * @param  mobileNo
     * @return code
     */
	
	private boolean callQianHaiFake(String idNo,String name,String resonCd,String mobileNo,String remoteIP){
		
		log.info("=====callQianHaiFake=====Start==");
		boolean flag =true;
		Util util = new Util();
		String transNo =util.getDate()+getTranNo();
		String tranDate =util.getSysDate("yyyy-MM-dd HH:mm:ss");
		//autheticDaoHandl.
		MessageHead messageHead = new MessageHead();
		//messageHead.setChnlId("qhcs-dcs");//固定
		messageHead.setTransNo(transNo);//自增
		messageHead.setTransDate(tranDate);
		//messageHead.setAuthCode("CRT001A2");//固定
		messageHead.setAuthDate(util.getSysDate("yyyy-MM-dd"));
		
		MessageBody messageBody= new MessageBody();
		messageBody.setBatchNo(getBathNo());
		//好信度欺诈
		CheatReqBodyMsg cheatReqBodyMsg = new CheatReqBodyMsg();
		cheatReqBodyMsg.setIdNo(idNo);
		//RiskReqBodyMsg.setIdNo("410329197511045073");
		cheatReqBodyMsg.setIdType("0");
		cheatReqBodyMsg.setName(name);
		cheatReqBodyMsg.setIp(remoteIP);
		//RiskReqBodyMsg.setName("张三");
		cheatReqBodyMsg.setMobileNo(mobileNo);
		cheatReqBodyMsg.setReasonNo(resonCd);
		cheatReqBodyMsg.setEntityAuthCode("123qwe2122");
		cheatReqBodyMsg.setEntityAuthDate(util.getSysDate("yyyy-MM-dd"));
		cheatReqBodyMsg.setSeqNo(transNo);
		List records = new ArrayList();
		records.add(cheatReqBodyMsg);
		messageBody.setRecords(records);
		Message res = qhService.cheatService(messageHead, messageBody,customerId);
		ArrayList<Record> list = new ArrayList<Record>();
		list=(ArrayList<Record>) res.getMessageBody().getRecords();
		CheatResBodyMsg cheatRes =null;
		String errCode ="";
		if (res != null){
			String transCode = res.getMessageHead().getRtCode();
			for (int i =0;i<list.size(); i++){
				cheatRes =(CheatResBodyMsg)list.get(i);
				errCode=cheatRes.getErCode();
			}
			
			if (SUCCESS_CODE.equals(transCode)){
//				if (SUCCESS_CODE.equals(errCode)){
//					if (cheatRes.getRskScore() != null && !"".equals(cheatRes.getRskScore())){
//						String score =cheatRes.getRskScore();
//						if (Integer.parseInt(score) > rskScore){
//							
//							flag=false;
//						}
//					}				
//				}	
			}else{
				flag =false;
			}
		}
		
		if (!flag){
			updateStatus(customerId,"OVER","2","PENDINQH_CHEAT");
		}else{
			updateStatus(customerId,"PENDINQH_CHEAT","1","PENDINQH_CHEAT");
		}

			//flag =false;
//		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
//		log.info(JSONUtils.toJSONString(res.getMessageBody()));
		log.info("=====callQianHaiFake=====end==");
		return flag;
	}
	
	 /**
     *  好信一鉴通
     * @param  idNo
     * @param  name
     * @param  resonCd
     * @return code
     */
	private boolean callQianHaiAuthen(String idNo,String name,String resonCd,String mobileNo,String company,String edu){

		log.info("=====callQianHaiAuthen=====Start==");
		boolean flag =true;
		String authsubProduct ="0000000101001101";
		Util util = new Util();
		String transNo =util.getDate()+getTranNo();
		String tranDate =util.getSysDate("yyyy-MM-dd HH:mm:ss");
		//autheticDaoHandl.
		MessageHead messageHead = new MessageHead();
		//messageHead.setChnlId("qhcs-dcs");//固定
		messageHead.setTransNo(transNo);//自增
		messageHead.setTransDate(tranDate);
		//messageHead.setAuthCode("CRT001A2");//固定
		messageHead.setAuthDate(util.getSysDate("yyyy-MM-dd"));
		
		if ("".equals(company) || company ==null){
			authsubProduct ="0000000101001001";
		}
		
		if ("".equals(edu) || edu ==null){
			authsubProduct ="0000000001001001";
		}
		
		MessageBody messageBody= new MessageBody();
		messageBody.setBatchNo(getBathNo());
		messageBody.setSubProductInc(authsubProduct);
		
		//鉴通
		AuthenticateReqBodyMsg authReqBodyMsg = new AuthenticateReqBodyMsg();
	
		authReqBodyMsg.setIdNo(idNo);
		//RiskReqBodyMsg.setIdNo("410329197511045073");
		authReqBodyMsg.setIdType("0");
		authReqBodyMsg.setName(name);
		//RiskReqBodyMsg.setName("张三");
		authReqBodyMsg.setMobileNo(mobileNo);
		authReqBodyMsg.setReasonCode(resonCd);
		authReqBodyMsg.setEntityAuthCode("123qwe2122");
		authReqBodyMsg.setEntityAuthDate(util.getSysDate("yyyy-MM-dd"));
		authReqBodyMsg.setSeqNo(transNo);
		
		if (!"".equals(company) && company !=null){
			authReqBodyMsg.setCompany(company);
		}
		
		if (!"".equals(edu) && edu !=null){
			authReqBodyMsg.setEductionBckgrd(edu);
		}
		
		List records = new ArrayList();
		records.add(authReqBodyMsg);
		messageBody.setRecords(records);
		
		Message res = qhService.authenticateService(messageHead, messageBody,customerId);
		ArrayList<Record> list = new ArrayList<Record>();
		AuthenticateResBodyMsg authResBodyMsg =null;
		String errCode ="";
		if (res != null){
			String transCode = res.getMessageHead().getRtCode();
			list=(ArrayList<Record>) res.getMessageBody().getRecords();
			for (int i =0;i<list.size(); i++){
				authResBodyMsg =(AuthenticateResBodyMsg)list.get(i);
				errCode=authResBodyMsg.getErrorInfo();
			}
			System.out.print(transCode);
			if (!SUCCESS_CODE.equals(transCode)){
				//updateStatus(customerId,"PENDINQH_AUTHEN","1","PENDINQH_AUTHEN");
				flag =false;
			}

		}
		if (!flag){
			updateStatus(customerId,"OVER","2","PENDINQH_AUTHEN");
		}else{
			updateStatus(customerId,"PENDINQH_AUTHEN","1","PENDINQH_AUTHEN");
		}
		
		log.info("=====callQianHaiAuthen=====end==");
		return flag;
	}
	
	
	 /**
     * 常贷客数据
     * @param  idNo
     * @param  name
     * @param  resonCd
     * @return code
     */
	
	private boolean callQianHaiLender(String idNo,String name){
		
		log.info("=====callQianHaiLender=====Start==");
		boolean flag =true;
		Util util = new Util();
		String transNo =util.getDate()+getTranNo();
		String tranDate =util.getSysDate("yyyy-MM-dd HH:mm:ss");

		MessageHead messageHead = new MessageHead();
		//messageHead.setChnlId("qhcs-dcs");//固定
		messageHead.setTransNo(transNo);//自增
		messageHead.setTransDate(tranDate);
		//messageHead.setAuthCode("CRT001A2");//固定
		messageHead.setAuthDate(util.getSysDate("yyyy-MM-dd"));
		
		MessageBody messageBody= new MessageBody();
		messageBody.setBatchNo(getBathNo());

		LendCoustomerReqBodyMsg lendReqBodyMsg = new LendCoustomerReqBodyMsg();
	
		lendReqBodyMsg.setIdNo(idNo);
		lendReqBodyMsg.setIdType("0");
		lendReqBodyMsg.setName(name);
		lendReqBodyMsg.setEntityAuthCode("123qwe2122");
		lendReqBodyMsg.setEntityAuthDate(util.getSysDate("yyyy-MM-dd"));
		lendReqBodyMsg.setSeqNo(transNo);
		List records = new ArrayList();
		records.add(lendReqBodyMsg);
		messageBody.setRecords(records);
		Message res = qhService.lendCoustomerService(messageHead, messageBody,customerId);
		
		ArrayList<Record> list = new ArrayList<Record>();
		list=(ArrayList<Record>) res.getMessageBody().getRecords();
		LendCoustomerResBodyMsg lendResBodyMsg =null;
		String errCode ="";
		if (res != null){
			String transCode = res.getMessageHead().getRtCode();
			for (int i =0;i<list.size(); i++){
				lendResBodyMsg =(LendCoustomerResBodyMsg)list.get(i);
				errCode=lendResBodyMsg.getErCode();
			}
			
			if (!SUCCESS_CODE.equals(transCode)){
				flag =false;
			}
		}
		if (!flag){
			updateStatus(customerId,"OVER","2","PENDINQH_LEND");
		}else{
			updateStatus(customerId,"PENDINQH_LEND", "1","PENDINQH_LEND");
		}
			//flag =false;
//		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
//		log.info(JSONUtils.toJSONString(res.getMessageBody()));
		log.info("=====callQianHaiLender=====end==");
		return flag;
	}
	
	 /**
     * 银行卡评分
     * @param  idNo
     * @param  name
     * @param  accountNum 银行卡号
     * @param  resonCd
     * @return code
     */
	
	public boolean callQianHaiCreditCard(String idNo,String name,String accountNum,String resonCd,String mobileNo){
		
		if ("".equals(customerId) || customerId == null){
			customerId="123456";
		}
		//customerId="1234566";
		log.info("=====callQianHaiCreditCard=====Start==");
		boolean flag =true;
		Util util = new Util();
		String transNo =util.getDate()+getTranNo();
		String tranDate =util.getSysDate("yyyy-MM-dd HH:mm:ss");
		MessageHead messageHead = new MessageHead();
		//messageHead.setChnlId("qhcs-dcs");//固定
		messageHead.setTransNo(transNo);//自增
		messageHead.setTransDate(tranDate);
		//messageHead.setAuthCode("CRT001A2");//固定
		messageHead.setAuthDate(tranDate);
		
		MessageBody messageBody= new MessageBody();
		messageBody.setBatchNo(getBathNo());

		BankCardScoreReqBodyMsg bankReqBodyMsg = new BankCardScoreReqBodyMsg();
		bankReqBodyMsg.setAccountNo(accountNum);
		bankReqBodyMsg.setIdNo(idNo);
		bankReqBodyMsg.setIdType("0");
		bankReqBodyMsg.setName(name);
		bankReqBodyMsg.setMobileNo(mobileNo);
		bankReqBodyMsg.setReasonCode(resonCd);
		bankReqBodyMsg.setEntityAuthCode("123qwe2122");
		bankReqBodyMsg.setEntityAuthDate(util.getSysDate("yyyy-MM-dd"));
		bankReqBodyMsg.setSeqNo(transNo);
		List records = new ArrayList();
		records.add(bankReqBodyMsg);
		messageBody.setRecords(records);
		Message res = qhService.bankCardScoreService(messageHead, messageBody,customerId);

		ArrayList<Record> list = new ArrayList<Record>();

		BankCardScoreResBodyMsg bankCardResBodyMsg =null;
		if (res != null){
			String transCode = res.getMessageHead().getRtCode();
			list=(ArrayList<Record>) res.getMessageBody().getRecords();
			if (!SUCCESS_CODE.equals(transCode)){
				flag =false;
			}
		}
		
		if (!flag){
			updateStatus(customerId,"OVER","2","PENDINQH_CREDITCARD");
		}else{
			updateStatus(customerId,"PENDINQH_CREDITCARD","1","PENDINQH_CREDITCARD");
		}

			//flag =false;
//		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
//		log.info(JSONUtils.toJSONString(res.getMessageBody()));
		log.info("=====callQianHaiCreditCard=====end==");
		return flag;
	}
	
	
	 /**
     * 生成交易流水号
     * @return tranNo
     */
	
	private String getTranNo(){		
	   String tranNo ="";
       Random random= new Random();
       
       tranNo =String.valueOf(random.nextInt(99)*3);
       
       if (tranNo.length() < 2){
    	   tranNo = "0"+tranNo;
       }else if (tranNo.length()>2){
    	   tranNo =tranNo.substring(1,3);
       }
       
       return tranNo;

	}
	
	 /**
     * 生成批次号
     * @return batchNo
     */
	private String getBathNo(){	
		
		   String batchNo ="";
		   String arrayChar[] ={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T",
				   				"U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n",
				   				"o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8",
				   				"9","0",};	   
	       Random random= new Random();      
	       batchNo =String.valueOf(random.nextInt(9999)*3);
	       for (int i=0 ; i< 6; i ++){
	    	   
	    	   int idx =random.nextInt(arrayChar.length);
	    	   batchNo=arrayChar[idx]+batchNo;
	       }
	       return batchNo;
	}
	
//	public static void main(String[] args) {
//		
//		AuthenticateService sce = new AuthenticateService();
//	    System.out.println(sce.getBathNo());
//	}

}
