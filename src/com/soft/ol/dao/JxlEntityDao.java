package com.soft.ol.dao;

import java.util.List;
import java.util.Map;

import com.soft.jxl.bean.AppCheck;
/**
 * 聚信立接口数据存储
 * @author lujf
 *
 */
public interface JxlEntityDao {
	/**
	 * 插入用户基本信息分析数据
	 * @author lujf
	 * @param appCheck
	 */
	public void insertAppCheck(AppCheck appCheck);
	
	/**
	 * 插入通话详细
	 * @author lujf
	 * @param list
	 */
	public void insertCallInfo(List<Map<String,Object>> list);
	
}
