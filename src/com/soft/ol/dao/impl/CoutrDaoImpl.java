package com.soft.ol.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.soft.ol.dao.CoutrDao;
import com.soft.ol.dto.Court;
import com.soft.ol.dto.ExcuteForZj;




public class CoutrDaoImpl extends SqlMapClientDaoSupport implements CoutrDao{

	public long insertCourtInfo( final ArrayList<Court> list,final String dbHandl,final int num) {
		// TODO Auto-generated method stub
		int count=0;
		count=(Integer)this.getSqlMapClientTemplate().execute(new SqlMapClientCallback(){ 

				public Object doInSqlMapClient(SqlMapExecutor executor) 
			            throws SQLException { 
			    executor.startBatch(); 
			    int batch = 0; 
			    for(Court court:list){ 
			    	executor.insert(dbHandl,court); 
			    	batch++; 
			    	if(batch==num){ 
			    		executor.executeBatch(); 
			    		batch = 0; 
			    	} 
			    } 
			    executor.executeBatch(); 
			    return batch; 
			} 
		 }); 
		return count;
	}
	
	
	public long insertExecute(final ArrayList<ExcuteForZj> list) {
		// TODO Auto-generated method stub
		int count=0;
		count=(Integer)this.getSqlMapClientTemplate().execute(new SqlMapClientCallback(){ 

				public Object doInSqlMapClient(SqlMapExecutor executor) 
			            throws SQLException { 
			    executor.startBatch(); 
			    int batch = 0; 
			    for(ExcuteForZj executeList:list){ 
			    	executor.insert("insertExecute",executeList); 
			    	batch++; 
			    	//2500条批量提交一次。 
			    	if(batch==2500){ 
			    		executor.executeBatch(); 
			    		batch = 0; 
			    	} 
			    } 
			    executor.executeBatch(); 
			    return batch; 
			} 
		 }); 
		return count;
	}
	
	@Override
	public ArrayList<Court> queryForCourt() {
		// TODO Auto-generated method stub
		 ArrayList<Court> list = (ArrayList<Court>) getSqlMapClientTemplate().queryForList("queryCourt");
		 return list;
	}


	



}
