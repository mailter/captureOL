package com.soft.ol.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.soft.ol.dao.BlackListDao;
import com.soft.ol.dto.Black;
import com.soft.ol.dto.Customer;
import com.soft.ol.dto.Proxy;


public class BlackListDaoImpl extends SqlMapClientDaoSupport implements BlackListDao{

	@Override
	public long insertBlackForPerson(Black obj) {
		int count=0;
		getSqlMapClientTemplate().insert("insertPersonBlack",obj);
		return count;
	}

	@Override
	public long insertBlackForCorp(Black obj) {
		// TODO Auto-generated method stub
		int count=0;
		getSqlMapClientTemplate().insert("insertCorpBlack",obj);
		return count;
	}

	@Override
	public ArrayList<Black> queryForBlackList() {
		// TODO Auto-generated method stub
		 ArrayList<Black> list = (ArrayList<Black>) getSqlMapClientTemplate().queryForList("queryblackList");
		 return list;
	}
	
	@Override
	public ArrayList<Proxy> queryForProxy() {
		// TODO Auto-generated method stub
		 ArrayList<Proxy> list = (ArrayList<Proxy>) getSqlMapClientTemplate().queryForList("queryProxyList");
		 return list;
	}
	
	@Override
	public int updatePoxy(Proxy obj) {
		// TODO Auto-generated method stub
		int total = getSqlMapClientTemplate().update("updateProxyForshixin",obj); 
		return total;
	}

	@Override
	public long updateBlackForCorp(Black obj) {
		// TODO Auto-generated method stub
		int total = getSqlMapClientTemplate().update("updateblackListForcorp",obj); 
		return total;
	}

	@Override
	public long updateBlackForPerson(Black obj) {
		// TODO Auto-generated method stub
		int total = getSqlMapClientTemplate().update("updateblackListForPerson",obj); 
		return total;
	}

	@Override
	public long deleteBlackForPerson(String id) {
		// TODO Auto-generated method stub
		int count=0;
		getSqlMapClientTemplate().delete("deleteBlackByid",id);
		return count;
	}
	
	@Override
	public void callBlackProcedue(Black obj) {
		// TODO Auto-generated method stub
		 int returnValue=0;

		HashMap paramMap = new HashMap();
			
		paramMap.put("pcorp_name",obj.getName());
		System.out.println(paramMap.get("pcorp_name"));
		paramMap.put("porg_id", obj.getCardNum());
		System.out.println(paramMap.get("porg_id"));
		paramMap.put("pcourt",obj.getCourt());
		System.out.println(paramMap.get("pcourt"));
		paramMap.put("pexecute_base",obj.getExecuteBase());
		System.out.println(paramMap.get("pexecute_base"));
		paramMap.put("pexecute_code",obj.getExecuteCode());
		System.out.println(paramMap.get("pexecute_code"));
		paramMap.put("pexecute_base_corp", obj.getExecuteCorp());
		System.out.println(paramMap.get("pexecute_base_corp"));
		paramMap.put("pcause_create_date", obj.getCauseCreateDate());
		System.out.println(paramMap.get("pcause_create_date"));
		paramMap.put("pduty", obj.getDuty());
		System.out.println(paramMap.get("pduty"));
		paramMap.put("pperformance", obj.getPerformance());
		System.out.println(paramMap.get("pperformance"));
		paramMap.put("pbehavier", obj.getBehavier());
		System.out.println(paramMap.get("pbehavier"));
		paramMap.put("ppub_time", obj.getPubTime());
		System.out.println(paramMap.get("ppub_time"));
		paramMap.put("pprovince", obj.getProvince());	
		System.out.println(paramMap.get("pprovince"));
		paramMap.put("pdata_cause", "高法院失信");
		
		if ("581".equals(obj.getType())){
			paramMap.put("ptype", "2");
			paramMap.put("partificial_person",obj.getArtificialPerson());
			System.out.println(paramMap.get("ptype"));
			System.out.println(paramMap.get("partificial_person"));
		}else{
			paramMap.put("ptype", "1");
			paramMap.put("partificial_person","");
			paramMap.put("psex", obj.getSex());
			paramMap.put("page", obj.getAge());
			
			System.out.println(paramMap.get("ptype"));
			System.out.println(paramMap.get("psex"));
			System.out.println(paramMap.get("page"));
			
		}

		paramMap.put("pperformedPart", obj.getPerformedPart());
		paramMap.put("punperformPart", obj.getUnperformPart());
	
		paramMap.put("retunCode", returnValue);
		
		getSqlMapClientTemplate().insert("call_black_online", paramMap);
			
		System.out.println(paramMap.get("retunCode"));
		}
	
	@Override
	public ArrayList<Customer> queryForCustomer() {
		// TODO Auto-generated method stub
		 ArrayList<Customer> list = (ArrayList<Customer>) getSqlMapClientTemplate().queryForList("queryCustomer");
		 return list;
	}
	
	
	public long updateCustomer(Customer obj) {
		// TODO Auto-generated method stub
		int code = getSqlMapClientTemplate().update("updateForCustomer",obj); 
		return code;
	}
	
	public long updateAuthenticate(Customer obj) {
		// TODO Auto-generated method stub
		int code = getSqlMapClientTemplate().update("updateForAuthenticate",obj); 
		return code;
	}
	
}
