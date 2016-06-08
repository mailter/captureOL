package com.soft.ol.dao.impl;

import java.util.ArrayList;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.soft.ol.dao.ProxyDao;
import com.soft.ol.dto.Proxy;

public class ProxyDaoImpl extends SqlMapClientDaoSupport implements ProxyDao {
	@Override
	public ArrayList<Proxy> queryForProxy() {
		ArrayList<Proxy> list = (ArrayList<Proxy>) getSqlMapClientTemplate()
				.queryForList("queryProxyExcList");
		return list;
	}
	
	@Override
	public int updatePoxy(Proxy obj) {
		int total = getSqlMapClientTemplate().update("updateProxy", obj);
		return total;
	}
	
	@Override
	public int updateAllProxy() {
		int total = getSqlMapClientTemplate().update("updateAllProxy");
		return total;
	}

}

