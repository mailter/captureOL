package com.soft.qh.service;

import com.soft.qh.bean.Message;
import com.soft.qh.bean.MessageBody;
import com.soft.qh.bean.MessageHead;
import com.soft.qh.bean.SecurityInfo;

public interface IQhService {
	//好信度服务地址
	String GOOD_RELIABILITY_SERVICE = "do/dmz/query/credoo/v1/MSC8005";
	
	//地址通数据接口
	String ADDRESS_T_SERVICE = "do/dmz/query/address/v1/MSC8007";
	//风险度提升接口（查询）
	String RISK_DEGREE_SERVICE = "do/dmz/query/rskdoo/v1/MSC8036";
	//风险度提升接口（报送）
	String RISK_DEGREE_SUBMIT_SERVICE = "do/dmz/submit/rskdoo/v1/MSC9004";
	//常贷客接口
	String LENDCOUSTOMER_SERVICE = "do/dmz/query/loanee/v1/MSC8037";
	//好信一鉴通接口
	String AUTHENTICATE_SERVICE = "do/dmz/verify/eChkPkgs/v1/MSC8107";
	
	//好信度欺诈接口
	String CHEAT_SERVICE = "do/dmz/verify/antiFraudDoo/v1/MSC8075";
	//银行卡评分
	String BANK_CARD_SCORE_SERVICE = "do/dmz/query/ubzc2m/v1/MSC8012";
	
	/**
	 * 好信度数据数据接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 */
	public Message goodReliabilityService(MessageHead messageHead,MessageBody messageBody,String customerId);
	
	
	/**
	 * 地址通数据数据接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 */
	public Message addressService(MessageHead messageHead,MessageBody messageBody,String customerId);
	
	
	/**
	 * 风险度提升接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 * @return
	 */
	public Message riskDegreeService(MessageHead messageHead,MessageBody messageBody,String customerId);
	
	
	
	/**
	 * 常贷客数据数据接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 * @return
	 */
	public Message lendCoustomerService(MessageHead messageHead,MessageBody messageBody,String customerId);
	
	
	/**
	 * 好信一鉴通数据接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 * @return
	 */
	public Message	authenticateService(MessageHead messageHead,MessageBody messageBody,String customerId);
	
	
	/**
	 * 风险度报送接口
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @date 2016年06月02日
	 * @return
	 */
	public Message riskDegreeSubmitService(MessageHead messageHead,MessageBody messageBody,String customerId);
	
	/**
	 * 好信欺诈数据接口
	 * @param messageHead
	 * @param messageBody
	 * @return
	 */
	public Message cheatService(MessageHead messageHead,MessageBody messageBody,String customerId);
	
	
	/**
	 * 银行卡评分接口
	 * @param messageHead
	 * @param messageBody
	 * @return
	 */
	public Message bankCardScoreService(MessageHead messageHead,MessageBody messageBody,String customerId);
	
}
