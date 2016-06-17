package com.soft.ol.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.soft.ol.common.Util;
import com.soft.ol.dao.ExecutDao;
import com.soft.ol.dto.Black;
import com.soft.ol.dto.Customer;
import com.soft.ol.dto.ExcuteCase;
import com.soft.ol.dto.HttpClientParam;
import com.soft.ol.impl.HttpClientImp;



public class ZhixinOl{

	private static String host="zhixing.court.gov.cn";
	String refer="http://zhixing.court.gov.cn/search/";
	String proxy="";
	int port =80;
	String code="";
	Util util= new Util();
	ArrayList list = new ArrayList();
	Black black = new Black();
	
	public ExecutDao execDaoHandl;
	
	public HttpClientImp httpclientHandl;

	public void setHttpclientHandl(HttpClientImp httpclientHandl) {
		this.httpclientHandl = httpclientHandl;
	}

	public ExecutDao getExecDaoHandl() {
		return execDaoHandl;
	}

	public void setExecDaoHandl(ExecutDao execDaoHandl) {
		this.execDaoHandl = execDaoHandl;
	}

	//HttpClientImp httpclientHandl = new HttpClientImp();
	
	public String getZhixin(String name,String idNum){
		
		String strFlag ="正常";
		String url="http://zhixing.court.gov.cn/search/";
		String cookie = "";
		int i=0;
		while (true){
			cookie = httpclientHandl.getCookieByWebClient(url,"2");
			if (!"404".equals(cookie)){
				break;
			}else{
				if (i==3){
					System.out.println("=========="+cookie+"=========="+i);
					strFlag="404";
					return strFlag;
				}
				i++;
			}
		}
		
		//获取验证码
		String tempCode =getVlidCode(cookie);
		code =tempCode.split("@")[0];
		if (tempCode.split("@")[1].contains("JSESSIONID")){
			String arrayCookie []=cookie.split(";");
			for (int k =0; k<arrayCookie.length; k++){
				if (k==0){
					cookie =arrayCookie[k]+"; "+tempCode.split("@")[1]+"; ";
				}else{

					cookie = cookie+arrayCookie[k]+"; ";
				}
				
			}
			cookie =StringUtils.substringBeforeLast(cookie,";");
			//cookie=tempCode.split("@")[1]+"; "+cookie;
		}
		
		System.out.println("========cookie====="+cookie);
		//获取listId
		list=getListIdBypost(cookie,name,idNum);
		//有执行
		if (list!=null && list.size()>0){
			strFlag="执行";
		}
		
		return strFlag;
	}
	
//	for (int i=0; i<list.size();){
//	boolean flag=true;
//	if (flag){
//		flag=getJesonResult(String.valueOf(list.get(i)), cookie);
//		i++;
//	}else{
//		continue;
//	}
//	
//}
	
	/**
	 * 获取验证码
	 * @param cookie
	 * @return
	 */
	private String getVlidCode(String cookie){
		Random random = new Random();
		boolean flag =false;
		int idx = random.nextInt(99);
		String url="http://zhixing.court.gov.cn/search/security/jcaptcha.jpg?"+idx;
		while (!flag){
			code =httpclientHandl.gethtmlByGet("UTF-8",url, refer,host,proxy,port,cookie,"1");
			if (!"".equals(code) && code != null){
				if (!util.isNumeric(code.split("@")[0])){
					continue;
				}else{
					flag=true;
				}
			}
		}
		return code;
	}
	
	/**
	 * post获取列表
	 * @param cookie
	 * @return
	 */
	  private ArrayList getListIdBypost(String cookie,String companyName,String idNum){
		   
		boolean blnFlag =false;
		int i =0;
		String url="http://zhixing.court.gov.cn/search/newsearch";
		String accept ="text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
		ArrayList<HttpClientParam> paramList = new ArrayList<HttpClientParam>();
		ArrayList arryList = new ArrayList();
		Map<String,String> map = new HashMap<String,String>();
		map.put("searchCourtName", "全国法院（包含地方各级法院）");
		map.put("selectCourtId", "1");
		map.put("selectCourtArrange", "1");
		map.put("pname", companyName);
		map.put("cardNum", idNum);
		map.put("j_captcha", code);
		paramList=SetParamForHttpClient(map);
		
		 while (!blnFlag){
			 String result= httpclientHandl.gethtmlByPost(url, "UTF-8", refer, host, accept, paramList, proxy, port,cookie);
			 if (!"".equals(result) && result!=null && result.contains("案号")){
				 arryList=getContentByjsoup(result); 
				 blnFlag=true;
			 }else{
				 if (i == 10){
					 break;
				 }
				 if (result.contains("</script>") && !result.contains("验证码错误，请重新输入！")){
					 cookie = httpclientHandl.getCookieByWebClient(url,"2");
				 }
				  String tempCode =getVlidCode(cookie);
				  code =tempCode.split("@")[0];
//					if (tempCode.split("@")[1].contains("JSESSIONID")){
//						cookie=tempCode.split("@")[1]+"; "+cookie;
//					}
				  map.put("searchCourtName", "全国法院（包含地方各级法院）");
				  map.put("selectCourtId", "1");
				  map.put("selectCourtArrange", "1");
				  map.put("pname", companyName);
				  map.put("cardNum", "");
				  map.put("j_captcha", code);
				  paramList=SetParamForHttpClient(map);
			 }
			 i++;
		 }

		 return arryList;

	   }
	   
		 public ArrayList<HttpClientParam> SetParamForHttpClient(Map<String, String> map){
			 
			 ArrayList<HttpClientParam> arrylist = new ArrayList<HttpClientParam>();
			 for (int i=0; i< map.size(); i++){
				 HttpClientParam param = new HttpClientParam();
				 if (i == 0){
					 param.setParamName("searchCourtName"); 
					 param.setParamValue(map.get("searchCourtName"));
				 }else if (i == 1){
					 param.setParamName("selectCourtId"); 
					 param.setParamValue(map.get("selectCourtId"));
				 }else if (i == 2){
					 param.setParamName("selectCourtArrange"); 
					 param.setParamValue(map.get("selectCourtArrange"));
				 }else if (i == 3){
					 param.setParamName("pname"); 
					 param.setParamValue(map.get("pname"));
				 }else if (i == 4){
					 param.setParamName("cardNum"); 
					 param.setParamValue(map.get("cardNum"));
				 }else if (i == 5){
					param.setParamName("j_captcha"); 
					param.setParamValue(map.get("j_captcha"));
				 }
				 arrylist.add(param);
			 }
			return arrylist;
		 }
		 
		private ArrayList getContentByjsoup(String html){
			ArrayList arraylist = new ArrayList();
			Document doc=Jsoup.parse(html);
			Elements eles=doc.select("table.Resultlist").get(0).select("a.View");
			for(int i=0;i<eles.size(); i++){
				arraylist.add(eles.get(i).attr("id"));
			}
			return arraylist;
		}
		
		private boolean getJesonResult(String id,String cookie){
			boolean flag=true;
			JSONObject obj=null;
			
			code =getVlidCode(cookie);

			code =code.split("@")[0];
			String url="http://zhixing.court.gov.cn/search/newdetail?id="+id+"&j_captcha="+code;
			String result =httpclientHandl.gethtmlByGet("UTF-8",url, refer,host,proxy,port,cookie,"2");
			if ("".equals(result) || result == null || result.contains("{}")){
				cookie = httpclientHandl.getCookieByWebClient(url,"2");
				String tmpCode =getVlidCode(cookie);
				code =tmpCode.split("@")[0];
				if (tmpCode.split("@")[1].contains("JSESSIONID")){
					cookie=tmpCode.split("@")[1]+"; "+cookie;
				}
				flag=false;
			}else if (result.contains("caseCode")){
				setExecvalue(execDaoHandl,result,obj,id);
			}
			
			return flag;
		}
		
		
		public void setExecvalue(ExecutDao executHandl, String result,JSONObject obj,String id){
			   
			Util util= new Util();
			obj=util.getjsonObj(result);
			ExcuteCase execObj = new ExcuteCase();
			execObj.setSpideId(id);
				//姓名
			execObj.setPname(String.valueOf(obj.get("pname")));
				//身份证号
			execObj.setPartyCardNum(String.valueOf(obj.get("partyCardNum")));;
				//案号
			execObj.setCaseCode(String.valueOf(obj.get("caseCode")));
				//状态
			//execObj.setCaseState(String.valueOf(obj.get("caseState")));
				//执行法庭
			execObj.setExecCourtName(String.valueOf(obj.get("execCourtName")));
				//执行标的
			execObj.setExecMoney(String.valueOf(obj.get("execMoney")));
				//立案时间
			execObj.setCaseCreateTime(String.valueOf(obj.get("caseCreateTime")));
				
			if (execObj.getPname().length()>4){
				execObj.setType("2");
			}else{
				execObj.setType("1");
			}
			executHandl.callExecuteProcedue(execObj);
		}
		
			
//		public static void main(String[] args) throws Exception {
//			ZhixinOl factory = new ZhixinOl();
//			factory.getZhixin("周树人","");
//		}
	
}
