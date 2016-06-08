package com.soft.ol.dao;

import java.util.Map;

public interface QhEntityDao {
	/**
	 * 插入好信度信息
	 * @author lujf
	 * @param dataMap
	 */
	public void insertgoodreliability(Map<String,String> dataMap);
	
	/**
	 * 插入地址通数据数据
	 * @author lujf
	 * @param dataMap
	 */
	public void insertaddress(Map<String,String> dataMap);
	
	
	/**
	 * 风险度查询数据入库
	 * @param dataMap
	 */
	public void insertriskDegree(Map<String,String> dataMap);
	
	
	/**
	 * 常贷客数据数据入库
	 * @param dataMap
	 */
	public void insertlendCoustomer(Map<String,String> dataMap);
	
	/**
	 *  好信一鉴通数据插入
	 * @param dataMap
	 */
	public void insertauthenticate(Map<String,String> dataMap);
	
	
	/**
	 *  好信一欺诈度数据插入
	 * @param dataMap
	 */
	public void insertcheat(Map<String,String> dataMap);
	
	/**
	 * 银行卡评分
	 * @param dataMap
	 */
	public void insertbankcardscore(Map<String,String> dataMap);
	
}
