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
	
	private ShixinOl shixinService;
	private ZhixinOl zhixinService;
	
	private IQhService qhService;
	
	public AuthenticateDao autheticDaoHandl;
	
	String customerId ="";
	
	private int credooScore ;//好信度分值
	private int rskScore;//好信度欺诈
	
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

	public void executeAuthenticateResult(String idNo,String name,String resonCd,String mobileNo,String remoteIP,String cardCode){
		
		boolean blnFlag =true;
		
		ArrayList<Customer> arrayList = new ArrayList<Customer>();
		
		arrayList=autheticDaoHandl.queryForCustomer();
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
				
				if ("执行".endsWith(reString)){
					blnFlag=false;
				}

				System.out.println("========执行查询============"+blnFlag);
				if (!blnFlag){
					updateStatus(customerId,"OVER", "2","PENDINZX");
					continue;
				}else{
					updateStatus(customerId,"PENDINSX", "1","PENDINSX");
				}
				log.info("=====执行查询===== end==");
				
				log.info("=====失信查询=====start==");
				//失信
				String sxString =shixinService.getShixin(name,idNo);
				
				if ("失信".endsWith(sxString)){
					blnFlag=false;
				}
				System.out.println("========失信查询============"+blnFlag);
				if (!blnFlag){
					updateStatus(customerId,"OVER", "2","PENDINSX");
					continue;
				}else{
					updateStatus(customerId,"PENDINQH", "1","PENDINQH");
				}
				log.info("=====失信查询=====end==");
			}

			
			if (blnFlag){
				//前海接口
				//风险度提示interface
				//if (!callQianHaiRisk("440102198301114447","米么联调","01")){
				log.info("=====风险度提示=====start==");
				if (!callQianHaiRisk(idNo,name,resonCd)){
					updateStatus(customerId,"OVER","2","PENDINQH_RISK");
					continue;
				}
				log.info("=====风险度提示=====end==");
				//好信度
				//if (!callQianHaiCredit("211403198704148220","张小","01","13266693365")){
				log.info("=====好信度=====start==");
				if (!callQianHaiCredit(idNo,name,resonCd,mobileNo,cardCode)){
					updateStatus(customerId,"OVER","2","PENDINQH_CREDIT");
					continue;
				}
				log.info("=====好信度=====end==");
				//好信度欺诈
				//if (!callQianHaiFake("211403198704148220","张小","01","13266693365")){
				log.info("=====好信度欺诈=====start==");
				if (!callQianHaiFake(idNo,name,resonCd,mobileNo,remoteIP)){
					updateStatus(customerId,"OVER","2","PENDINQH_CHEAT");
					continue;
				}
				log.info("=====好信度欺诈=====end==");
				//鉴通
				log.info("=====鉴通=====start==");
				if (!callQianHaiAuthen(idNo,name,resonCd,mobileNo)){
					updateStatus(customerId,"OVER","2","PENDINQH_AUTHEN");
					continue;
				}
				log.info("=====鉴通=====end==");
				//常贷客
				//if (!callQianHaiLender("410329197511045073","张三")){
				log.info("=====常贷客=====start==");
				if (!callQianHaiLender(idNo,name)){
					updateStatus(customerId,"OVER","2","PENDINQH_LENDER");
					continue;
				}
				log.info("=====常贷客=====end==");
				//银行卡评分
				if (!callQianHaiCreditCard(idNo,name,cardCode,resonCd,mobileNo)){
					updateStatus(customerId,"OVER","2","PENDINQH_CREDITCARD");
					continue;
				}

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
		autheticDaoHandl.callBlackProcedue(customer);
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
		messageHead.setChnlId("qhcs-dcs");//固定
		//messageHead.setTransNo(transNo);//自增
		messageHead.setTransNo(transNo);//自增
		//messageHead.setTransDate(tranDate);
		messageHead.setTransDate(tranDate);
		messageHead.setAuthCode("CRT001A2");//固定
		messageHead.setAuthDate(util.getSysDate("yyyy-MM-dd"));
		
		MessageBody messageBody= new MessageBody();
		messageBody.setBatchNo(getBathNo());
		//好信常贷客
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
		String transCode = res.getMessageHead().getRtCode();
		ArrayList<Record> list = new ArrayList<Record>();
		list=(ArrayList<Record>) res.getMessageBody().getRecords();
		RiskDegreeResBodyMsg riskRes =null;
		String errCode ="";
		if (res != null){
			for (int i =0;i<list.size(); i++){
				riskRes =(RiskDegreeResBodyMsg)list.get(i);
				errCode=riskRes.getErCode();
			}
			
			if (transCode.equals("E000000")){
				if (errCode.equals("E000000")){
					if (riskRes.getRskMark() != null && !"".equals(riskRes.getRskMark())){
						if (!riskRes.getRskMark().contains("99")){
							flag=false;
						}
					}				
				}	
			}
		}
		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
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
		messageHead.setChnlId("qhcs-dcs");//固定
		messageHead.setTransNo(transNo);//自增
		messageHead.setTransDate(tranDate);
		messageHead.setAuthCode("CRT001A2");//固定
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
		String transCode = res.getMessageHead().getRtCode();
		ArrayList<Record> list = new ArrayList<Record>();
		list=(ArrayList<Record>) res.getMessageBody().getRecords();
		GoodReliabilityResBodyMsg creditRes =null;
		String errCode ="";
		if (res != null){
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
			}
		}
		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
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
		messageHead.setChnlId("qhcs-dcs");//固定
		messageHead.setTransNo(transNo);//自增
		messageHead.setTransDate(tranDate);
		messageHead.setAuthCode("CRT001A2");//固定
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
		String transCode = res.getMessageHead().getRtCode();
		ArrayList<Record> list = new ArrayList<Record>();
		list=(ArrayList<Record>) res.getMessageBody().getRecords();
		CheatResBodyMsg cheatRes =null;
		String errCode ="";
		if (res != null){
			for (int i =0;i<list.size(); i++){
				cheatRes =(CheatResBodyMsg)list.get(i);
				errCode=cheatRes.getErCode();
			}
			
			if (SUCCESS_CODE.equals(transCode)){
				if (SUCCESS_CODE.equals(errCode)){
					if (cheatRes.getRskScore() != null && !"".equals(cheatRes.getRskScore())){
						String score =cheatRes.getRskScore();
						if (Integer.parseInt(score) > rskScore){
							flag=false;
						}
					}				
				}	
			}
		}
		
		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
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
	private boolean callQianHaiAuthen(String idNo,String name,String resonCd,String mobileNo){
		
		log.info("=====callQianHaiAuthen=====Start==");
		boolean flag =true;
		Util util = new Util();
		String transNo =util.getDate()+getTranNo();
		String tranDate =util.getSysDate("yyyy-MM-dd HH:mm:ss");
		//autheticDaoHandl.
		MessageHead messageHead = new MessageHead();
		messageHead.setChnlId("qhcs-dcs");//固定
		messageHead.setTransNo(transNo);//自增
		messageHead.setTransDate(tranDate);
		messageHead.setAuthCode("CRT001A2");//固定
		messageHead.setAuthDate(util.getSysDate("yyyy-MM-dd"));
		
		MessageBody messageBody= new MessageBody();
		messageBody.setBatchNo(getBathNo());
		messageBody.setSubProductInc("0000000101001001");
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
		List records = new ArrayList();
		records.add(authReqBodyMsg);
		messageBody.setRecords(records);
		Message res = qhService.authenticateService(messageHead, messageBody,customerId);
		String transCode = res.getMessageHead().getRtCode();
		ArrayList<Record> list = new ArrayList<Record>();
		list=(ArrayList<Record>) res.getMessageBody().getRecords();
		AuthenticateResBodyMsg authResBodyMsg =null;
		String errCode ="";
		if (res != null){
			for (int i =0;i<list.size(); i++){
				authResBodyMsg =(AuthenticateResBodyMsg)list.get(i);
				errCode=authResBodyMsg.getErrorInfo();
			}
			
			if (SUCCESS_CODE.equals(transCode)){
				if (SUCCESS_CODE.equals(errCode)){
					if (authResBodyMsg.getErrorInfo() != null && !"".equals(authResBodyMsg.getErrorInfo())){
						
					}				
				}	
			}
		}
		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
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
		messageHead.setChnlId("qhcs-dcs");//固定
		messageHead.setTransNo(transNo);//自增
		messageHead.setTransDate(tranDate);
		messageHead.setAuthCode("CRT001A2");//固定
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
		String transCode = res.getMessageHead().getRtCode();
		ArrayList<Record> list = new ArrayList<Record>();
		list=(ArrayList<Record>) res.getMessageBody().getRecords();
		LendCoustomerResBodyMsg lendResBodyMsg =null;
		String errCode ="";
		if (res != null){
			for (int i =0;i<list.size(); i++){
				lendResBodyMsg =(LendCoustomerResBodyMsg)list.get(i);
				errCode=lendResBodyMsg.getErCode();
			}
			
//			if (SUCCESS_CODE.equals(transCode)){
//				if (SUCCESS_CODE.equals(errCode)){
//					if (authResBodyMsg.getErrorInfo() != null && !"".equals(authResBodyMsg.getErrorInfo())){
//
//					}				
//				}	
//			}
		}
		
		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
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
	
	private boolean callQianHaiCreditCard(String idNo,String name,String accountNum,String resonCd,String mobileNo){
		
		log.info("=====callQianHaiCreditCard=====Start==");
		boolean flag =true;
		Util util = new Util();
		String transNo =util.getDate()+getTranNo();
		String tranDate =util.getSysDate("yyyy-MM-dd HH:mm:ss");
		MessageHead messageHead = new MessageHead();
		messageHead.setChnlId("qhcs-dcs");//固定
		messageHead.setTransNo(transNo);//自增
		messageHead.setTransDate(tranDate);
		messageHead.setAuthCode("CRT001A2");//固定
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
		String transCode = res.getMessageHead().getRtCode();
		ArrayList<Record> list = new ArrayList<Record>();
		list=(ArrayList<Record>) res.getMessageBody().getRecords();
		BankCardScoreResBodyMsg bankCardResBodyMsg =null;
		if (res != null){
			for (int i =0;i<list.size(); i++){
				bankCardResBodyMsg =(BankCardScoreResBodyMsg)list.get(i);
				//errCode=bankCardResBodyMsg.getErCode();
			}
		}
		System.out.println(JSONUtils.toJSONString(res.getMessageBody()));
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
