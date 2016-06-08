package com.soft.qh.bean;

import java.util.List;

import com.soft.qh.Record;
/**
 * 消息体
 * @author lujf
 * 2016年5月27日
 */
public class MessageBody {
	//批次号
	private String batchNo;
	
	//子产品信息
	private String subProductInc;
	//记录数
	private List<Record> records;
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public List<Record> getRecords() {
		return records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	public String getSubProductInc() {
		return subProductInc;
	}
	public void setSubProductInc(String subProductInc) {
		this.subProductInc = subProductInc;
	}
	
	
	
	
}
