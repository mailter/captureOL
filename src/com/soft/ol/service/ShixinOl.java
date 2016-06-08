package com.soft.ol.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.soft.ol.common.Util;
import com.soft.ol.dao.BlackListDao;
import com.soft.ol.dto.Customer;
import com.soft.ol.dto.HttpClientParam;
import com.soft.ol.impl.HttpClientImp;



public class ShixinOl{
	private static Logger log = (Logger) LogManager.getLogger(ShixinOl.class);
	
	String host ="shixin.court.gov.cn";
	String refer="http://shixin.court.gov.cn/";
	String url="http://shixin.court.gov.cn/";
	String proxy="";
	int port =80;
	String code="";
	String cookie = "";
	Util util= new Util();
	ArrayList list = new ArrayList();
	//Black black = new Black();
	
	public BlackListDao blackDaoHandl;
	
	public HttpClientImp httpclientHandl;

	public HttpClientImp getHttpclientHandl() {
		return httpclientHandl;
	}

	public void setHttpclientHandl(HttpClientImp httpclientHandl) {
		this.httpclientHandl = httpclientHandl;
	}

	public BlackListDao getBlackDaoHandl() {
		return blackDaoHandl;
	}

	public void setBlackDaoHandl(BlackListDao blackDaoHandl) {
		this.blackDaoHandl = blackDaoHandl;
	}

	//HttpClientImp httpclientHandl = new HttpClientImp();

	public String getShixin(String name,String idNum){
		String strFlag ="正常";
		ArrayList<Customer> arrayList = new ArrayList<Customer>();
		
		int i=0;
		while (true){
			cookie = httpclientHandl.getCookieByWebClient(url,"1");
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
		code =getVlidCode(cookie);
		//获取listId
		list=getListIdBypost(cookie,name,idNum);
			//有失信
		if (list!=null && list.size()>0){
			strFlag="失信";
		}
		return strFlag;
	}
	
//	for (int j=0; j<list.size();){
//	boolean flag=true;
//	if (flag){
//		flag=getJesonResult(String.valueOf(list.get(j)), cookie);
//		j++;
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
		boolean flag=false;

		String date=util.getStringTime();
		String url="http://shixin.court.gov.cn/image.jsp?date="+date;

		while (!flag){
			code =httpclientHandl.gethtmlByGet("UTF-8",url, refer,host,proxy,port,cookie,"1");
			if (!util.isNumeric(code.split("@")[0])){
				continue;
			}else{
				code=code.split("@")[0];
				flag=true;
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
		int i=0;
		String url="http://shixin.court.gov.cn/findd";
		String accept ="text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
		ArrayList<HttpClientParam> paramList = new ArrayList<HttpClientParam>();
		ArrayList arryList = new ArrayList();
		Map<String,String> map = new HashMap<String,String>();
		map.put("pName", companyName);
		map.put("pCardNum", idNum);
		map.put("pProvince", "0");
		map.put("pCode", code);
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
					 cookie = httpclientHandl.getCookieByWebClient(url,"1");
				 }
				  code =getVlidCode(cookie);
				  map.put("pName", companyName);
				  map.put("pCardNum", idNum);
				  map.put("pProvince", "0");
				  map.put("pCode", code);
				  paramList=SetParamForHttpClient(map);
			 }	
		 }
  
		 return arryList;
	   }
	   
		private ArrayList<HttpClientParam> SetParamForHttpClient(Map<String, String> map){
			 
			 ArrayList<HttpClientParam> arrylist = new ArrayList<HttpClientParam>();
			 for (int i=0; i< map.size(); i++){
				 HttpClientParam param = new HttpClientParam();
				 if (i == 0){
					 param.setParamName("pName"); 
					 param.setParamValue(map.get("pName"));
				 }else if (i == 1){
					 param.setParamName("pCardNum"); 
					 param.setParamValue(map.get("pCardNum"));
				 }else if (i == 2){
					 param.setParamName("pProvince"); 
					 param.setParamValue(map.get("pProvince"));
				 }else if (i == 3){
					 param.setParamName("pCode"); 
					 param.setParamValue(map.get("pCode"));
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
		
//		private boolean getJesonResult(String id,String cookie){
//			boolean flag=true;
//			JSONObject obj=null;
//			String url="http://shixin.court.gov.cn/findDetai?id="+id+"&pCode="+code;
//			String result =httpclientHandl.gethtmlByGet("UTF-8",url, refer,host,proxy,port,cookie,"2");
//			if ("".equals(result) || result == null || result.contains("{}")){
//				cookie = httpclientHandl.getCookieByWebClient(url,"1");
//				code =getVlidCode(cookie);
//				flag=false;
//			}else if (result.contains("iname")){
//				black=setBlackListvalue(black,result,obj);
//				black.setSpideId(String.valueOf(id));
//				blackDaoHandl.callBlackProcedue(black);
//			}
//			
//			return flag;
//		}
		
		
//		private Black setBlackListvalue(Black blackObj, String result,JSONObject obj){
//			   
//				Util util= new Util();
//
//				obj=util.getjsonObj(result);
//				
//				//姓名
//				blackObj.setName(String.valueOf(obj.get("iname")));
//				//身份证号
//				blackObj.setCardNum(String.valueOf(obj.get("cardNum")));;
//				//省份
//				blackObj.setProvince(String.valueOf(obj.get("areaName")));;
//				//性别
//				blackObj.setSex(String.valueOf(obj.get("sexy")));;
//				//年龄
//				blackObj.setAge(String.valueOf(obj.get("age")));;
//				//法院
//				blackObj.setCourt(String.valueOf(obj.get("courtName")));;
//				//执行依据文号
//				blackObj.setExecuteBase(String.valueOf(obj.get("gistId")));
//				//执行案号
//				blackObj.setExecuteCode(String.valueOf(obj.get("caseCode")));
//				//执行依据单位
//				blackObj.setExecuteCorp(String.valueOf(obj.get("gistUnit")));
//				//被执行人的履行情况
//				blackObj.setPerformance(String.valueOf(obj.get("performance")));
//				//发布时间
//				blackObj.setPubTime(util.translationData(String.valueOf(obj.get("publishDate"))));
//				//生效法律文书确定的义务
//				blackObj.setDuty(String.valueOf(obj.get("duty")));
//				//失信被执行人行为具体情形
//				blackObj.setBehavier(String.valueOf(obj.get("disruptTypeName")));
//				//立案时间
//				blackObj.setCauseCreateDate(util.translationData(String.valueOf(obj.get("regDate"))));
//				
//				blackObj.setBehavier(String.valueOf(obj.get("disruptTypeName")));
//				if (!"".equals(obj.get("performedPart")) && obj.get("performedPart") !=null){
//					blackObj.setPerformedPart(String.valueOf(obj.get("performedPart")));
//				}else{
//					blackObj.setPerformedPart("");
//				}
//				
//				if (!"".equals(obj.get("unperformPart")) && obj.get("unperformPart") !=null){
//					blackObj.setUnperformPart(String.valueOf(obj.get("unperformPart")));
//				}else{
//					blackObj.setUnperformPart("");
//				}
//
//				blackObj.setType(String.valueOf(obj.get("partyTypeName")));
//
//				 if ("581".equals(blackObj.getType())){
//					 //企业法人 
//					 blackObj.setArtificialPerson(String.valueOf(obj.get("businessEntity")));;
//				 }
//				
//				return blackObj;
//
//		   }
//		
		
		public static void main(String[] args) throws Exception {
			ShixinOl factory = new ShixinOl();
			factory.getShixin("王海军","4129251975");
		}
	
}
