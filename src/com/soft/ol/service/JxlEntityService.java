package com.soft.ol.service;

import java.util.List;
import java.util.Map;

import com.soft.jxl.bean.AppCheck;
import com.soft.ol.dao.JxlEntityDao;

/**
 * 聚信立数据库服务
 * @author lujf
 *
 */
public class JxlEntityService {
	
	private JxlEntityDao jxlEntityDao;
	
	
	
	public JxlEntityDao getJxlEntityDao() {
		return jxlEntityDao;
	}



	public void setJxlEntityDao(JxlEntityDao jxlEntityDao) {
		this.jxlEntityDao = jxlEntityDao;
	}



	/**
	 * 基本信息以及通话记录保存
	 * @param appCheck
	 * @param contactList
	 */
	public void insertInfoAndCallInfo(AppCheck appCheck,List<Map<String, Object>> contactList){
		jxlEntityDao.insertAppCheck(appCheck);
		if(contactList != null){
			for(Map<String, Object> dataMap : contactList){
				dataMap.put("uinfo_id", appCheck.getId());
				//jxlEntityDao.insertCallInfo(dataMap);
			}
		}
		jxlEntityDao.insertCallInfo(contactList);
	}
}
