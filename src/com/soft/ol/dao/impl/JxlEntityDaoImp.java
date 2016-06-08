package com.soft.ol.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.soft.jxl.bean.AppCheck;
import com.soft.ol.dao.JxlEntityDao;
 

public class JxlEntityDaoImp extends SqlMapClientDaoSupport implements JxlEntityDao{

	
	/**
	 * 插入用户基本信息分析数据
	 * @author lujf
	 * @param appCheck
	 */
	public void insertAppCheck(AppCheck appCheck){
		getSqlMapClientTemplate().insert("insertAppCheck",appCheck);
	}
	
	
	
	/**
	 * 插入通话详细
	 * @author lujf
	 * @param list
	 */
	public void insertCallInfo(List<Map<String,Object>> list){
		getSqlMapClientTemplate().insert("insertCallInfo",list);
	}
	
}
