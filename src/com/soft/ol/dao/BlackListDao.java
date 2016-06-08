package com.soft.ol.dao;

import java.util.ArrayList;

import com.soft.ol.dto.Black;
import com.soft.ol.dto.Customer;
import com.soft.ol.dto.Proxy;
public interface BlackListDao {
	public long insertBlackForPerson(Black obj);
	public long insertBlackForCorp(Black obj);
	public long updateBlackForCorp(Black obj);
	public long updateBlackForPerson(Black obj);
	public long deleteBlackForPerson(String id);
	public int updatePoxy(Proxy obj);
	
	public ArrayList<Black> queryForBlackList();
	public ArrayList<Proxy> queryForProxy();
	public void callBlackProcedue(Black obj);
	public ArrayList<Customer> queryForCustomer();
	public long updateCustomer(Customer obj);
	public long updateAuthenticate(Customer obj);
}
