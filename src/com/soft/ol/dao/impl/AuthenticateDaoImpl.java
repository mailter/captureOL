package com.soft.ol.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.soft.ol.dao.AuthenticateDao;
import com.soft.ol.dto.Customer;


public class AuthenticateDaoImpl extends SqlMapClientDaoSupport implements AuthenticateDao{

//
//	@Override
//	public ArrayList<Proxy> queryForProxy() {
//		// TODO Auto-generated method stub
//		 ArrayList<Proxy> list = (ArrayList<Proxy>) getSqlMapClientTemplate().queryForList("queryProxyList");
//		 return list;
//	}
//	
//	@Override
//	public int updatePoxy(Proxy obj) {
//		// TODO Auto-generated method stub
//		int total = getSqlMapClientTemplate().update("updateProxyForshixin",obj); 
//		return total;
//	}

	
	public void callBlackProcedue(Customer obj) {
		// TODO Auto-generated method stub
		int returnValue=0;

		HashMap paramMap = new HashMap();
		paramMap.put("pcustomerId", Integer.parseInt(obj.getCustomerId()));
		paramMap.put("pcustomerstatus", obj.getCustomerStatus());
		if (!"".equals(obj.getAuthenticateStatus()) && obj.getAuthenticateStatus()!=null){
			paramMap.put("pauthstatus", Integer.parseInt(obj.getAuthenticateStatus()));
		}
		
		paramMap.put("pauthtype", obj.getAuthtype());
		paramMap.put("returnCode", returnValue);
		
		getSqlMapClientTemplate().insert("call_update_status", paramMap);
		
		System.out.println(paramMap.get("returnCode"));
	}
	
	@Override
	public ArrayList<Customer> queryForCustomer() {
		// TODO Auto-generated method stub
		 ArrayList<Customer> list = (ArrayList<Customer>) getSqlMapClientTemplate().queryForList("queryCustomer");
		 return list;
	}
	
	@Override
	public int querySequence() {
		// TODO Auto-generated method stub
		 int  sequence = Integer.parseInt((String) getSqlMapClientTemplate().queryForObject("querySequence"));
		 return sequence;
	}

	

	
}
