package com.soft.ol.dao;

import java.util.ArrayList;

import com.soft.ol.dto.Court;
import com.soft.ol.dto.ExcuteForZj;

public interface CoutrDao {
	public long insertCourtInfo(ArrayList<Court> list,String dbHandl,int insertCount);
	public ArrayList<Court> queryForCourt();
	public long insertExecute(final ArrayList<ExcuteForZj> list);
}
