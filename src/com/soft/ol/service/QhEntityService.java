package com.soft.ol.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soft.qh.Record;
import com.soft.qh.bean.GoodReliabilityResBodyMsg;
import com.soft.qh.bean.Message;
import com.soft.qh.bean.MessageBody;
import com.soft.util.JSONUtils;
import com.soft.ol.dao.QhEntityDao;

/**
 * 前海数据保存
 * @author lujf
 *
 */
public class QhEntityService {
	
	private QhEntityDao qhEntityDao;
	
	
	public void setQhEntityDao(QhEntityDao qhEntityDao) {
		this.qhEntityDao = qhEntityDao;
	}


	/**
	 *  插入好信度信息
	 * @param messageBody
	 */
	public void insertgoodreliability(Message message,String customerId){
		Map dataMap = JSONUtils.toHashMap(message.getMessageHead());
		MessageBody messageBody = message.getMessageBody();
		if(messageBody != null){
			List<Record> datalist = messageBody.getRecords();
			for(int i = 0 ; i < datalist.size(); i++){
				Map dsaveMap = JSONUtils.toHashMap(datalist.get(i));
				dsaveMap.put("batchNo", messageBody.getBatchNo());
				dsaveMap.putAll(dataMap);
				dsaveMap.put("customerId", customerId);
				qhEntityDao.insertgoodreliability(dsaveMap);
			}
			
		}		
	}
	
	
	/**
	 * 插入地址通数据
	 * @param message
	 */
	public void insertaddress(Message message,String customerId){
		Map dataMap = JSONUtils.toHashMap(message.getMessageHead());
		MessageBody messageBody = message.getMessageBody();
		if(messageBody != null){
			List<Record> datalist = messageBody.getRecords();
			for(int i = 0 ; i < datalist.size(); i++){
				Map dsaveMap = JSONUtils.toHashMap(datalist.get(i));
				dsaveMap.put("batchNo", messageBody.getBatchNo());
				dsaveMap.putAll(dataMap);
				dsaveMap.put("customerId", customerId);
				qhEntityDao.insertaddress(dsaveMap);
			}
			
		}	
	}
	
	
	/**
	 * 插入风险度数据
	 * @param message
	 */
	public void insertriskDegree(Message message,String customerId){
		Map dataMap = JSONUtils.toHashMap(message.getMessageHead());
		MessageBody messageBody = message.getMessageBody();
		if(messageBody != null){
			List<Record> datalist = messageBody.getRecords();
			for(int i = 0 ; i < datalist.size(); i++){
				Map dsaveMap = JSONUtils.toHashMap(datalist.get(i));
				dsaveMap.put("batchNo", messageBody.getBatchNo());
				dsaveMap.putAll(dataMap);
				dsaveMap.put("customerId", customerId);
				qhEntityDao.insertriskDegree(dsaveMap);
			}
			
		}	
	}
	
	/**
	 * 常贷客数据数据入库
	 * @param message
	 */
	public void insertlendCoustomer(Message  message,String customerId){
		Map dataMap = JSONUtils.toHashMap(message.getMessageHead());
		MessageBody messageBody = message.getMessageBody();
		if(messageBody != null){
			List<Record> datalist = messageBody.getRecords();
			for(int i = 0 ; i < datalist.size(); i++){
				Map dsaveMap = JSONUtils.toHashMap(datalist.get(i));
				dsaveMap.put("batchNo", messageBody.getBatchNo());
				dsaveMap.putAll(dataMap);
				dsaveMap.put("customerId", customerId);
				qhEntityDao.insertlendCoustomer(dsaveMap);
			}
			
		}	
	}
	
	/**
	 * 好信一鉴通数据插入
	 * @param message
	 */
	public void insertauthenticate(Message  message,String customerId){
		Map dataMap = JSONUtils.toHashMap(message.getMessageHead());
		MessageBody messageBody = message.getMessageBody();
		if(messageBody != null){
			List<Record> datalist = messageBody.getRecords();
			for(int i = 0 ; i < datalist.size(); i++){
				Map dsaveMap = JSONUtils.toHashMap(datalist.get(i));
				dsaveMap.put("batchNo", messageBody.getBatchNo());
				dsaveMap.putAll(dataMap);
				dsaveMap.put("customerId", customerId);
				qhEntityDao.insertauthenticate(dsaveMap);
			}
			
		}	
	}
	
	/**
	 * 好信欺诈度数据插入
	 * @param message
	 */
	public void insertcheat(Message  message,String customerId){
		Map dataMap = JSONUtils.toHashMap(message.getMessageHead());
		MessageBody messageBody = message.getMessageBody();
		if(messageBody != null){
			List<Record> datalist = messageBody.getRecords();
			for(int i = 0 ; i < datalist.size(); i++){
				Map dsaveMap = JSONUtils.toHashMap(datalist.get(i));
				dsaveMap.put("batchNo", messageBody.getBatchNo());
				dsaveMap.putAll(dataMap);
				dsaveMap.put("customerId", customerId);
				qhEntityDao.insertcheat(dsaveMap);
			}
			
		}	
	}
	
	/**
	 * 银行卡评分数据插入
	 * @param message
	 */
	public void insertbankcardscore(Message  message,String customerId){
		Map dataMap = JSONUtils.toHashMap(message.getMessageHead());
		MessageBody messageBody = message.getMessageBody();
		if(messageBody != null){
			List<Record> datalist = messageBody.getRecords();
			for(int i = 0 ; i < datalist.size(); i++){
				Map dsaveMap = JSONUtils.toHashMap(datalist.get(i));
				dsaveMap.put("batchNo", messageBody.getBatchNo());
				dsaveMap.putAll(dataMap);
				dsaveMap.put("customerId", customerId);
				qhEntityDao.insertbankcardscore(dsaveMap);
			}
			
		}	
	}
	
}
