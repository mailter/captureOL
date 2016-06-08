package com.soft.qh.bean;


import com.soft.qh.auth.DataSecurityUtil;
import com.soft.util.JSONUtils;

public class MessageUtil {
	
	/**
	 * @author lujf
	 * @param messageHead
	 * @param messageBody
	 * @param securityInfo
	 * @param keyStr
	 * @param transformation
	 * @return
	 * @throws Exception
	 * @date 2016年5月27日
	 */
	public  String  createGoodReliabilityReqMsg(MessageHead messageHead,MessageBody messageBody,SecurityInfo securityInfo,String keyStr,String transformation) throws Exception{
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		buf.append("\"header\":");
		buf.append(JSONUtils.toJSONString(messageHead));
		buf.append(",");
		String encBusiData = DataSecurityUtil.encrypt(JSONUtils.toJSONString(messageBody).getBytes(), keyStr,transformation);
		buf.append("\"busiData\":\"");
		buf.append(encBusiData);
		buf.append(",");
		buf.append("\"securityInfo\":");
 
		buf.append("}");
		return buf.toString();
	}
}
