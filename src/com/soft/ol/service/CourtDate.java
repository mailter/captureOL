package com.soft.ol.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.soft.ol.common.Util;
import com.soft.ol.dao.CoutrDao;
import com.soft.ol.dto.Court;
import com.soft.ol.impl.HttpClientImp;


public class CourtDate {
	private static Logger log = (Logger) LogManager.getLogger(CourtDate.class);
	private static Integer SC_OK = 200; 
	final private static int recordNumber = 5000; 
	
	private CoutrDao courtDaoHandl;
	
//	private HttpClientImp  httpclientHandl;
//	
//	public HttpClientImp getHttpclientHandl() {
//		return httpclientHandl;
//	}
//
//	public void setHttpclientHandl(HttpClientImp httpclientHandl) {
//		this.httpclientHandl = httpclientHandl;
//	}
	HttpClientImp httpclientHandl = new HttpClientImp();
	public CoutrDao getCourtDaoHandl() {
		return courtDaoHandl;
		
	}

	public void setCourtDaoHandl(CoutrDao courtDaoHandl) {
		this.courtDaoHandl = courtDaoHandl;
	}

	//浙江
	public void CourtDataForOL(String companyName,String flag){
		
		CourtDate web = new CourtDate();
    	Util util= new Util();
    	JSONArray array=new JSONArray();
    	
    	String url="http://www.zjsfgkw.cn/Notice/NoticeKTSearch";
		Map<String,String> map = new HashMap<String,String>();
		ArrayList<Court> arrylist = new ArrayList<Court>();
		String cookie="";
		//String cookie = getCookieByWebClient();
		int page=1;
		for (int i=0; i < page;){
			if (!"".equals(companyName) && companyName !=null){
				 map.put("pageno",String.valueOf(i+1));
				 map.put("pagesize",String.valueOf(10));
				 map.put("cbfy","全部");
				 if ("1".equals(flag)){
					 map.put("yg",companyName);
					 map.put("bg","");
				 }else{
					 map.put("yg","");
					 map.put("bg",companyName);
				 }

				 String responseSec=gethtmlByPost(url,10000,"utf-8",map,cookie);
				 System.out.println("==result=="+responseSec);
				 if (!"".equals(responseSec) && responseSec !=null){
					  array= util.getjsonArray(responseSec,"list"); 
					   //取得数据总数
					  String dataNum=util.getjsonDate(responseSec,"total");
					   //计算页数
					  page=CalculatPage(Integer.parseInt(dataNum),10);
					  if (page <=0){
						  System.out.println("=====浙江法院无开庭记录======="+companyName);
						  log.info("=====浙江法院无开庭记录======="+companyName);
						  break;
					  }
				  }
				   
				  for (int j =0;j < array.size();j++){
					   Court court = new Court();
					   //法院
					   court.setStrCourt((String)array.getJSONObject(j).get("FY"));
					   //法庭
					   court.setStrTribunal((String)array.getJSONObject(j).get("FT"));
					   //开庭日期
					   court.setOpenDate((String)array.getJSONObject(j).get("KTRQ"));
					   //排期日期
					   court.setArrDate((String)array.getJSONObject(j).get("PQRQ"));
					   //案号
					   court.setCaseId((String)array.getJSONObject(j).get("AH"));
					   //案由
					   court.setCaseContent((String)array.getJSONObject(j).get("AY"));				   
					   //承办部门
					   court.setCaseDept((String)array.getJSONObject(j).get("CBBM"));
					   //审判长
					   court.setJudge((String)array.getJSONObject(j).get("SPZ"));
					   //原告
					   court.setPlaintiff(StringUtils.substringAfterLast((String)array.getJSONObject(j).get("YG"),":" ));
					   //被告
					   court.setDefendant(StringUtils.substringAfterLast((String)array.getJSONObject(j).get("BG"),":" ));
					   //被告
					   court.setDataFrom("浙江");
					   arrylist.add(court);
				  }
				  
				  if(i+1==page){
					 // courtDaoHandl.callProcedue(arrylist);
					  arrylist.clear(); 
				  }
			}
			i++;
		}	  
	}
	
	
	private String gethtmlByPost(String url,int timeout,String encode,Map<String,String> map,String cookie){
		// TODO Auto-generated method stub
		String entity="";
		String responseMsg = "";
		CloseableHttpClient httpClient=null;
		String host="www.zjsfgkw.cn";
		//String cookie ="Hm_lvt_c4cb5f597b36c5db42909a369cbaab8e=1461208377,1463645579,1463646263; _gscu_274171321=30980751z65s7u30; _gscs_274171321=63645579pnxwx020|pv:2; _gscbrs_274171321=1; Hm_lpvt_c4cb5f597b36c5db42909a369cbaab8e=1463646263";
		               
//		//if (!"".equals(proxy) && proxy != null){
//			HttpHost Proxy = new HttpHost("60.191.98.244",80);
//			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(Proxy);
//			httpClient = HttpClients.custom().setRoutePlanner(routePlanner).build();
////		}else{
////			httpClient=HttpClients.createDefault(); 
////		}
		httpClient=HttpClients.createDefault(); 

		HttpPost postMethod =null;
		postMethod=new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();//设置请求和传输超时时间
		postMethod.setConfig(requestConfig);
		
		postMethod.setHeader("Host", host);
		postMethod.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");
		postMethod.setHeader("Accept","*/*");
		
		postMethod.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		postMethod.setHeader("Accept-Encoding","gzip, deflate");
		postMethod.setHeader("Referer", "http://www.zjsfgkw.cn/Notice/NoticeKTList");
		postMethod.setHeader("Content-Type",
								"application/x-www-form-urlencoded; charset=UTF-8");
		postMethod.setHeader("X-Requested-With", "XMLHttpRequest");
		postMethod.setHeader("Connection", "keep-alive");
		//postMethod.setHeader("Cookie",cookie);
        List<NameValuePair> params = new ArrayList<NameValuePair>();  
        params.add(new BasicNameValuePair("pageno", map.get("pageno")));  
        params.add(new BasicNameValuePair("pagesize", map.get("pagesize")));  
        params.add(new BasicNameValuePair("cbfy", map.get("cbfy")));
        if (!"".equals(map.get("yg")) && map.get("yg") !=null){
            params.add(new BasicNameValuePair("yg", map.get("yg")));  
            params.add(new BasicNameValuePair("bg", ""));
        }else if(!"".equals(map.get("bg")) && map.get("bg") !=null){
            params.add(new BasicNameValuePair("yg", ""));  
            params.add(new BasicNameValuePair("bg", map.get("bg")));
        }
        params.add(new BasicNameValuePair("spz",""));
        params.add(new BasicNameValuePair("jarq1",""));
        params.add(new BasicNameValuePair("jarq2",""));

				
		try {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
	       HttpResponse response = httpClient.execute(postMethod);
	       System.out.println("=====code======="+response.getStatusLine().getStatusCode());
	       log.info("=====code======="+response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				
				responseMsg = EntityUtils.toString(response.getEntity()); 
			}
			
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				postMethod.releaseConnection();
			}
		postMethod.releaseConnection();

		return responseMsg;
	}
	
	
	 public static int CalculatPage(int num,int pagesize){
		 
		 int number = num%pagesize;
		 
		 if (num%pagesize !=0){
			 number= num/pagesize+1;
		 }else{
			 number=num/pagesize;
 		 }
		 
		return number;
	 }
	 
	 
		public String getCookieByWebClient() {
			String url="http://www.zjsfgkw.cn/Notice/NoticeKTList";
			String cookie="";
			String initCookie="";
			String html = "";

			BrowserVersion bv=null;
			Set set=new HashSet();
//			if ("3".equals(flag) || "4".equals(flag)){
//				bv = BrowserVersion.CHROME;
//			}else{
				bv = BrowserVersion.FIREFOX_24;
//			}
			
		
			bv.setBrowserLanguage("zh");
			WebClient webClient = new WebClient(bv);
			//WebClient webClient = new WebClient(bv, "101.226.249.237", 80);
			webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setRedirectEnabled(true);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setTimeout(30000);
			webClient.waitForBackgroundJavaScript(10000);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			HtmlPage page = null;
			try {
				page = webClient.getPage(url);
//				if ("4".equals(flag)){
//					html = page.asXml();
//					System.out.println(html);
//						return html;
//				}
				
				
			} catch (Exception e) {
				return "404";
			}
			
			//得到cookie

	        CookieManager CM = webClient.getCookieManager(); 
	        Set<Cookie> cookies_ret =  CM.getCookies();
	        String temp="";
	        String _gscu_274171321="";
	        String _gscs_274171321="";
	        String _gscbrs_274171321 ="1";
	        String Hm_lpvt ="";
	        Iterator<Cookie> i=cookies_ret.iterator();
            while (i.hasNext()){
            	String strCookie =String.valueOf(i.next());
            	System.out.println(strCookie);
            	if (strCookie.contains("Hm_lvt_c4cb5f597b36c5db42909a369cbaab8e")){
            		temp=StringUtils.substringBefore(strCookie,";");
            	}else if (strCookie.contains("_gscu_274171321")){
            		_gscu_274171321=StringUtils.substringBefore(strCookie,";");
            	}else if (strCookie.contains("_gscs_274171321")){
            		_gscs_274171321=StringUtils.substringBefore(strCookie,";");
            	}else if (strCookie.contains("Hm_lpvt_c4cb5f597b36c5db42909a369cbaab8e")){
            		Hm_lpvt=StringUtils.substringBefore(strCookie,";");
            	}
            }
            	
            initCookie =temp+"; "+_gscu_274171321+"; "+_gscs_274171321+"; "+Hm_lpvt;
	        return initCookie;
		}
	 
	public static void main(String[] args) throws IOException {
	  
		//	  for (int i = 0; i < objects.length; i++) {
		//		  objects[i]
		//		  System.out.println(objects[i]);  
		//	  }
		CourtDate aa = new CourtDate();
		aa.CourtDataForOL("王瑞国","1");
	

		//System.out.println("---"+web.getWebContent("http://www.zjcredit.gov.cn:8080/zjcreditweb/html/eDetail.jsp?id=EB3490FF0437F3E0"));
		//web.getWebContent("http://www.zjsfgkw.cn/Notice/NoticeKTList");
	
	}
}