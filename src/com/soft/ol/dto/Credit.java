package com.soft.ol.dto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.soft.ol.common.Util;

public class Credit implements Serializable { 
	/**
	 * 信誉度提示
	 */
	private String strCredit;
	/**
	 * 组织机构代码
	 */
	private String strOrgId;
	
	/**
	 * 法定代表人
	 */
	private String strCorporate;
	
	/**
	 * 企业名称
	 */
	private String strCorpName;
	
	/**
	 * 区域
	 */
	private String strAear;
	
	/**
	 * 注册地址
	 */
	private String registAddress;
	
	/**
	 * 营业执照
	 */
	private String license;	
	
	/**
	 * 经营范围
	 */
	private String scope;	
	
	public String getStrCredit() {
		return strCredit;
	}

	public void setStrCredit(String strCredit) {
		this.strCredit = strCredit;
	}

	public String getStrOrgId() {
		return strOrgId;
	}

	public void setStrOrgId(String strOrgId) {
		this.strOrgId = strOrgId;
	}

	public String getStrCorporate() {
		return strCorporate;
	}

	public void setStrCorporate(String strCorporate) {
		this.strCorporate = strCorporate;
	}

	public String getStrCorpName() {
		return strCorpName;
	}

	public void setStrCorpName(String strCorpName) {
		this.strCorpName = strCorpName;
	}

	public String getStrAear() {
		return strAear;
	}

	public void setStrAear(String strAear) {
		this.strAear = strAear;
	}

	public String getRegistAddress() {
		return registAddress;
	}

	public void setRegistAddress(String registAddress) {
		this.registAddress = registAddress;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
