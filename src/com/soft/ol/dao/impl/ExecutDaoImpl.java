package com.soft.ol.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.soft.ol.dao.ExecutDao;
import com.soft.ol.dto.ExcuteCase;


public class ExecutDaoImpl extends SqlMapClientDaoSupport implements ExecutDao{

	@Override
	public void callExecuteProcedue(ExcuteCase obj) {
		// TODO Auto-generated method stub
		 int returnValue=0;

		HashMap paramMap = new HashMap();
			
		paramMap.put("pname",obj.getPname());
		System.out.println(paramMap.get("pname"));
		paramMap.put("pcard_id", obj.getPartyCardNum());
		System.out.println(paramMap.get("pcard_id"));
		paramMap.put("pcourt",obj.getExecCourtName());
		paramMap.put("preg_date",obj.getCaseCreateTime());
		System.out.println("=======preg_date========="+paramMap.get("preg_date"));
		paramMap.put("pcase_no",obj.getCaseCode());
		paramMap.put("pexec_money",obj.getExecMoney());	
		paramMap.put("ptype", Integer.parseInt(obj.getType()));
		paramMap.put("retunCode", returnValue);
		
		getSqlMapClientTemplate().insert("call_exec_online", paramMap);
			
		System.out.println(paramMap.get("retunCode"));
		}
	
}
