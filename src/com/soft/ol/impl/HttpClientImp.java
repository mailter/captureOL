package com.soft.ol.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.soft.capture.util.PicGrey;
import com.soft.ol.dto.HttpClientParam;



public class HttpClientImp implements IHttpClient{
	
	private static Integer SC_OK = 200; 
	private static String cookie ="_gscu_274171321=30967506b9vhjq12; _gscs_274171321=58643633ho7wxf18|pv:5; _gscbrs_274171321=1; Hm_lvt_c4cb5f597b36c5db42909a369cbaab8e=1457923170,1458008437,1458643634,1458643697; Hm_lpvt_c4cb5f597b36c5db42909a369cbaab8e=1458643704";
	
	@Override
	public String gethtmlByPost(String url, String encoding,String p_referer, String host,String accept,ArrayList<HttpClientParam> list,String proxy,int port,String cookie){
		// TODO Auto-generated method stub
		String entity="";
		String responseMsg = "";
        List<NameValuePair> params = new ArrayList<NameValuePair>();  
		CloseableHttpClient httpClient=null;
		
		if (!"".equals(proxy) && proxy != null){
			HttpHost Proxy = new HttpHost(proxy,port);
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(Proxy);
			httpClient = HttpClients.custom().setRoutePlanner(routePlanner).build();
		}else{
			httpClient=HttpClients.createDefault(); 
		}
		

		HttpPost postMethod =null;
		postMethod=new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(8000).build();//设置请求和传输超时时间
		if (url.contains("http://wenshu.court.gov.cn/List/ListContent")){
			postMethod.setConfig(requestConfig); 
			postMethod.setHeader("Host", host);
			postMethod.setHeader("Connection", "keep-alive");
			postMethod.setHeader("Accept","*/*");
			postMethod.setHeader("Accept-Encoding", "gzip, deflate");
			postMethod.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
			postMethod.setHeader("Origin", "http://www.court.gov.cn");
			postMethod.setHeader("X-Requested-With", "XMLHttpRequest");
			postMethod.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
			postMethod.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			postMethod.setHeader("Referer", p_referer);
		}else{
			postMethod.setConfig(requestConfig);
			postMethod.setHeader("Host", host);
			postMethod.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
			postMethod.setHeader("Accept",accept);
			postMethod.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			postMethod.setHeader("Accept-Encoding","gzip, deflate");
			postMethod.setHeader("Referer", p_referer);
			postMethod.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			postMethod.setHeader("Connection", "keep-alive");
		}

		if (!"".equals(cookie) && cookie!=null){
			postMethod.setHeader("Cookie",cookie);
		}
		for (int i =0; i < list.size(); i++){
			
	        params.add(new BasicNameValuePair(list.get(i).getParamName(), list.get(i).getParamValue()));  

		}
				
		try {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   postMethod.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
	       HttpResponse response = httpClient.execute(postMethod);
//				Header[] aa =response.getHeaders("Set-Cookie");
//				if (aa.length >0){
//					entity=aa[0].getValue().split(";")[0];
//				
//			}
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
		System.out.println(responseMsg);

		return responseMsg;
	}
	
	/**
	 * GET方式获取网站内容
	 * @throws IOException 
	 * @throws HttpException 
	 *
	 * */
	public String gethtmlByGet(String encode,String p_url, String p_referer, String host,String Proxy,int port,String cookie,String flag){
			int retry = 0;
			String entity="";
			String return_str = "";
			CloseableHttpClient httpClient=null;
			
			if (!"".equals(Proxy) && Proxy != null){
				HttpHost proxy = new HttpHost(Proxy,port);
				DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
				httpClient = HttpClients.custom().setRoutePlanner(routePlanner).build();
			}else{
				httpClient=HttpClients.createDefault(); 
			}

			HttpGet getMethod =null;
			getMethod=new HttpGet(p_url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(8000).build();//设置请求和传输超时时间
			getMethod.setConfig(requestConfig);
			
			getMethod.setHeader("Host", host);
			getMethod.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
			if ("1".equals(flag)){
				getMethod.setHeader("Accept","image/png,image/*;q=0.8,*/*;q=0.5");
			}else if ("2".equals(flag)){
				getMethod.setHeader("Accept","*/*");
				getMethod.setHeader("X-Requested-With","XMLHttpRequest");
			}else if ("3".equals(flag)){
				getMethod.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			}else if ("4".equals(flag)){
				getMethod.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			}			
			getMethod.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			getMethod.setHeader("Accept-Encoding","gzip, deflate");

			if (p_referer != null && !"".equals(p_referer)) {
				getMethod.setHeader("Referer", p_referer);
			}
			
			if (cookie != null && !"".equals(cookie)) {
				getMethod.setHeader("Cookie", cookie);
			}
			
			getMethod.setHeader("Connection", "keep-alive");

		try {	
			HttpResponse response = httpClient.execute(getMethod);
			Header[] aa =response.getHeaders("Set-Cookie");
			if (aa.length >0){
				entity=aa[0].getValue().split(";")[0];
			}
			

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				if (p_url.contains("http://shixin.court.gov.cn/image.jsp") || 
					p_url.contains("http://zhixing.court.gov.cn/search/security/jcaptcha.jpg")){
					
					byte[] imageEntity= EntityUtils.toByteArray(response.getEntity());
					return_str =getVildCode(imageEntity);
					return_str=return_str+"@"+entity;
				}else{
					return_str = EntityUtils.toString(response.getEntity());
				}
			}
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				getMethod.releaseConnection();
			}
		getMethod.releaseConnection();

		return return_str;
	}
	
	@Override
	public String gethtmlByWebClient(String url, String ip, int port) {
		BrowserVersion bv = BrowserVersion.CHROME;
		bv.setBrowserLanguage("zh");
		WebClient webClient = new WebClient(bv, ip, port);
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
		} catch (Exception e) {
			return "40004";
		}
		String html = page.asXml();
		return html;
	}
	
	public String gethtmlByWebClientNotByProxy(String url) {
		BrowserVersion bv = BrowserVersion.CHROME;
		bv.setBrowserLanguage("zh");
		WebClient webClient = new WebClient(bv);
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
		} catch (Exception e) {
			return "40004";
		}
		String html = page.asXml();
		return html;
	} 
	
	

	@Override
	public String getCookieByWebClient(String url,String flag) {
		
		String cookie="";
		String initCookie="";
		String html = "";
		if ("1".equals(flag)){
			initCookie="_gscu_1049835508=31997994p3fooj24; _gscu_482111853=50060058kcfesx13; _gscu_1241932522=52473849vnne4n32; Hm_lvt_9e03c161142422698f5b0d82bf699727=1461308796; ";
		}else if ("2".equals(flag)){
			//initCookie="_gscu_1049835508=31997994p3fooj24; _gscu_482111853=50060058kcfesx13; _gscu_1241932522=52473849vnne4n32; __utma=93735126.751545381.1456974123.1456974123.1456974123.1; __utmz=93735126.1456974123.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Hm_lvt_9e03c161142422698f5b0d82bf699727=1461308796,1461912309; _gscs_1049835508=61912308zdscx416|pv:1; ";
			initCookie="yunsuo_session_verify=95b0b6926ae3ea5c61b7790107bfc682; Hm_lvt_9e03c161142422698f5b0d82bf699727=1464771259,1465183473; _gscu_1049835508=64771276pmu7p012";
		}else if ("3".equals(flag)){
			//initCookie="_gscu_1049835508=31997994p3fooj24; _gscu_482111853=50060058kcfesx13; _gscu_1241932522=52473849vnne4n32; _gscu_2116842793=56710260kuebds10; Hm_lvt_9e03c161142422698f5b0d82bf699727=1461308796,1461912309; ";
			initCookie="_gscu_482111853=496299504ske1d22; _gscu_1241932522=50261046iiejmb17; __utma=61363882.1451150868.1456899203.1456899203.1457003494.2; __utmz=61363882.1456899203.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); _gscu_1049835508=33744202zpf7o015; Hm_lvt_9e03c161142422698f5b0d82bf699727=1461210524,1461898435,1462262294,1462343075; ";
		}
		BrowserVersion bv=null;
		Set set=new HashSet();
		if ("3".equals(flag) || "4".equals(flag)){
			bv = BrowserVersion.CHROME;
		}else{
			bv = BrowserVersion.FIREFOX_24;
		}
		
	
		bv.setBrowserLanguage("zh");
		WebClient webClient = new WebClient(bv);
		//WebClient webClient = new WebClient(bv, "101.226.249.237", 80);
		webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(true);
		//webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setTimeout(40000);
		webClient.waitForBackgroundJavaScript(10000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = null;
		try {
			page = webClient.getPage(url);
			if ("4".equals(flag)){
				html = page.asXml();
				System.out.println(html);
					return html;
			}
			
			
		} catch (Exception e) {
			return "404";
		}
		
		//得到cookie

        CookieManager CM = webClient.getCookieManager(); 
        Set<Cookie> cookies_ret =  CM.getCookies();
        
        Iterator<Cookie> i=cookies_ret.iterator();
        String tmpPass ="";
        
        if ("2".equals(flag)){
        	
        	String tempccpassport="";
        	String wzwschallenge="";
        	String jession="";
        	String rout ="";
        	String yunsuo ="";
            while (i.hasNext()){
            	
            	String strCookie =String.valueOf(i.next());
            	System.out.println(strCookie);
            	if (strCookie.contains("ccpassport")){
            		tmpPass=StringUtils.substringBefore(strCookie,";");
            	}else if (strCookie.contains("yunsuo_session_verify")){
            		yunsuo =StringUtils.substringBefore(strCookie,";");
            	}else if (strCookie.contains("rout")){
            		rout =StringUtils.substringBefore(strCookie,";");
            	}
            	initCookie =rout+"; " +initCookie;
                System.out.println("============"+initCookie);
            }
        }else if ("3".equals(flag)){

            boolean cookieFlag =false;
            String tmpChallenge="";
            String tmpgs="";
            String tmpsession="";
            String tmpsession2="";
            String tmpFirm="";
            while (i.hasNext()){
                	
                String strCookie =String.valueOf(i.next());
                System.out.println(strCookie);
                if (strCookie.contains("ccpassport")){
                	tmpPass=StringUtils.substringBefore(strCookie,";") +"; ";
                }else if (strCookie.contains("yunsuo_session_verify")){
                	if(!cookieFlag){
                		tmpsession=StringUtils.substringBefore(strCookie,";")+"; ";
                		cookieFlag=true;
                	}else{
                		tmpsession2 =StringUtils.substringBefore(strCookie,";");
                	}	
                }else if (strCookie.contains("wzwschallenge")){
                		tmpChallenge=StringUtils.substringBefore(strCookie,";")+"; ";
                }else if (strCookie.contains("_gsref_2116842793") || strCookie.contains("_gscs_2116842793") 
                			|| strCookie.contains("_gscu_2116842793") || strCookie.contains("_gscbrs_2116842793")){
                		if (!"".equals(tmpgs) && tmpgs!=null){
                			tmpgs= tmpgs+"; "+StringUtils.substringBefore(strCookie,";");
                		}else{
                			tmpgs=StringUtils.substringBefore(strCookie,";");
                		}			
                }else if(strCookie.contains("wzwsconfirm") || strCookie.contains("wzwstemplate")){
                		if (!"".equals(tmpFirm) && tmpFirm!=null){
                			tmpFirm=tmpFirm+StringUtils.substringBefore(strCookie,";")+"; ";
                		}else{
                			tmpFirm=StringUtils.substringBefore(strCookie,";")+"; ";
                		}	
                	}
      
               }
            	initCookie =initCookie+tmpsession+tmpgs+tmpFirm+tmpChallenge+tmpPass+tmpsession2;
                System.out.println("============"+initCookie);
        }else{
        	String tempccpassport="";
        	String wzwschallenge="";
        	String jession="";
        	String rout ="";
        	String yunsuo ="";
            while (i.hasNext()){
            	
            	String strCookie =String.valueOf(i.next());
            	System.out.println(strCookie);
            	if (strCookie.contains("ccpassport")){
            		tmpPass=StringUtils.substringBefore(strCookie,";");
            	}else if (strCookie.contains("wzwschallenge")){
            		wzwschallenge=StringUtils.substringBefore(strCookie,";");
            	}else if (strCookie.contains("JSESSIONID")) {
            		jession=StringUtils.substringBefore(strCookie,";");
            	}else if (strCookie.contains("rout")) {
            		rout=StringUtils.substringBefore(strCookie,";");
            	}else if (strCookie.contains("yunsuo_session_verify")) {
            		yunsuo=StringUtils.substringBefore(strCookie,";");
            	}
            	
            	if (!"".equals(yunsuo) && yunsuo !=null){
            		initCookie =yunsuo+";" +rout+";" +jession;
            	}
  
                System.out.println("============"+initCookie);
            }
        }

		return initCookie;

	}
	
	 /**  
     * 用post方法获取页面内容  
     * @throws MalformedURLException  
     * @throws IOException  
     * @throws SAXException  
     */    
    public static void testPostMethod(String url)  {    

    }  
	
	/**
	 * 验证码识别
	 * @param byteIn
	 * @return
	 */

	public String getVildCode(byte[] byteIn) {
		String s = "C:/code.jpg";
		String code ="";
		ByteArrayInputStream in = new ByteArrayInputStream(byteIn);	
		try {
			BufferedImage image = ImageIO.read(in);
			if(image !=null ){
				ImageIO.write(image, "jpg", new File(s));
				PicGrey pic = new PicGrey(null,image);
				code=pic.getSpideCode();
			}
			System.out.println("========="+code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}
}


