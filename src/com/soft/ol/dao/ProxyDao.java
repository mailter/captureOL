package com.soft.ol.dao;

import java.util.ArrayList;

import com.soft.ol.dto.Proxy;

public interface ProxyDao {
	public ArrayList<Proxy> queryForProxy();
	public int updatePoxy(Proxy obj);
	public int updateAllProxy();
}
