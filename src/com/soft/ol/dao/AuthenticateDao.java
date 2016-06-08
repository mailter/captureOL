package com.soft.ol.dao;

import java.util.ArrayList;

import com.soft.ol.dto.Black;
import com.soft.ol.dto.Customer;
import com.soft.ol.dto.Proxy;

public interface AuthenticateDao {

	public void callBlackProcedue(Customer obj);
	public ArrayList<Customer> queryForCustomer();
	public int querySequence();

}
