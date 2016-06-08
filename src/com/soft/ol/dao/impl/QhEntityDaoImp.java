package com.soft.ol.dao.impl;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.soft.ol.dao.QhEntityDao;


public class QhEntityDaoImp extends SqlMapClientDaoSupport implements QhEntityDao{

	/* 
	 * 插入好信度信息
	 * @see com.zx.ol.dao.QhEntityDao#insertgoodreliability(java.util.Map)
	 */
	@Override
	public void insertgoodreliability(Map<String, String> dataMap) {
		getSqlMapClientTemplate().insert("insertgoodreliability",dataMap);
	}

	/* 插入地址通数据数据
	 * @see com.zx.ol.dao.QhEntityDao#insertaddress(java.util.Map)
	 */
	@Override
	public void insertaddress(Map<String, String> dataMap) {
		getSqlMapClientTemplate().insert("insertaddress",dataMap);
		
	}

	/* 风险度查询数据入库
	 * @see com.zx.ol.dao.QhEntityDao#insertriskDegree(java.util.Map)
	 */
	@Override
	public void insertriskDegree(Map<String, String> dataMap) {
		getSqlMapClientTemplate().insert("insertriskDegree",dataMap);
		
	}

	/* 常贷客数据数据入库
	 * @see com.zx.ol.dao.QhEntityDao#insertlendCoustomer(java.util.Map)
	 */
	@Override
	public void insertlendCoustomer(Map<String, String> dataMap) {
		getSqlMapClientTemplate().insert("insertlendCoustomer",dataMap);
		
	}
	

	/*  好信一鉴通数据插入
	 * @see com.zx.ol.dao.QhEntityDao#insertauthenticate(java.util.Map)
	 */
	public void insertauthenticate(Map<String,String> dataMap){
		getSqlMapClientTemplate().insert("insertauthenticate",dataMap);
	}

	/*好信一欺诈度数据插入
	 * @see com.zx.ol.dao.QhEntityDao#insertcheat(java.util.Map)
	 */
	public void insertcheat(Map<String,String> dataMap){
		getSqlMapClientTemplate().insert("insertcheat",dataMap);
	}

	/* 
	 * 银行卡评分
	 * @see com.zx.ol.dao.QhEntityDao#insertbankcardscore(java.util.Map)
	 */
	@Override
	public void insertbankcardscore(Map<String, String> dataMap) {
		getSqlMapClientTemplate().insert("insertbankcardscore",dataMap);
		
	}
	
	
	
}
