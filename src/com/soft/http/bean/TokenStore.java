package com.soft.http.bean;

import java.util.HashMap;
import java.util.Map;

import com.soft.jxl.bean.Token;
public class TokenStore {
	//jxl接口token存储
	private static Map<String,Token> token_map = new HashMap<String,Token>();
	
	/**
	 * 获取聚信立token
	 * @author lujf
	 * @param key
	 * @return
	 * @date 2016年5月26日
	 */
	public static Token getJlxToken(String key){
		return token_map.get(key);
	}
	
	/**
	 * 设置聚信立token
	 * @author lujf
	 * @param key
	 * @param token
	 * @date 2016年5月26日
	 */
	public static void setJlxToken(String key,Token token){
		token_map.put(key, token);
	}
}
