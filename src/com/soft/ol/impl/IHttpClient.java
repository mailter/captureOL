package com.soft.ol.impl;

import java.util.ArrayList;
import java.util.Map;

import com.soft.ol.dto.HttpClientParam;

public interface IHttpClient {
	public String gethtmlByGet(String encode,String p_url, String p_referer, String host,String Proxy,int port,String cookie,String flag);
	public String gethtmlByPost(String url, String encoding,String p_referer, String host,String accept,ArrayList<HttpClientParam> list,String proxy,int port,String cookie);
	public String gethtmlByWebClient(String url,String ip,int port);
	public String gethtmlByWebClientNotByProxy(String url);
	public String getCookieByWebClient(String url,String flag);
	
	
}
