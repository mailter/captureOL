package com.soft.jxl.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.HttpException;
import org.apache.http.cookie.Cookie;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soft.http.HttpConnect;
import com.soft.http.bean.RequestHead;
import com.soft.http.bean.TokenStore;
import com.soft.http.expextion.CaptureException;
import com.soft.jxl.bean.CustomInfo;
import com.soft.jxl.bean.DataMapping;
import com.soft.jxl.bean.SubmitCaptureReq;
import com.soft.jxl.bean.TaskCache;
import com.soft.jxl.bean.Token;
import com.soft.util.JSONUtils;
import com.soft.util.StringUtil;
import com.soft.jxl.bean.AppCheck;
import com.soft.ol.service.JxlEntityService;
/**
 * 聚信立接口实现类
 * 
 * @author lujf 2016年5月26日
 */
public class JlxServiceImp implements JlxService {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());

	private HttpConnect httpConnect = new HttpConnect();;
	// 客户名称
	private String orgName = "pinxing";
	// 客户标识码
	private String clientSecret = "76f5a521f5104983b5c9b21a57abfdae";
	// 过期失效时间
	private String hours = "1";
	// 聚信立接口地址
	private String jlxInterfaceUri = "https://www.juxinli.com/";
	
	private JxlEntityService jxlEntityService;
	
	
	
	
	public void setHttpConnect(HttpConnect httpConnect) {
		this.httpConnect = httpConnect;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public void setJlxInterfaceUri(String jlxInterfaceUri) {
		this.jlxInterfaceUri = jlxInterfaceUri;
	}

	public void setJxlEntityService(JxlEntityService jxlEntityService) {
		this.jxlEntityService = jxlEntityService;
	}

	/**
	 * 获取token
	 * @author lujf
	 * @return
	 * @date 2016年5月26日
	 */
	public Token getToken() {
		Token token = TokenStore.getJlxToken(clientSecret);
		if (token == null || token.expire()) {
			// 从接口获取
			token = this.getTokenFromRpc();
		}
		if(token==null){
			throw new CaptureException("jlx00000008", "token获取失败", "token获取失败");
		}
		return token;
	}

	/**
	 * 
	 * @author lujf
	 * @return
	 * @date 2016年5月26日
	 */
	public Token getTokenFromRpc() {
		Map<String, String> param = new HashMap<String, String>();
		param.put("org_name", this.orgName);
		param.put("client_secret", this.clientSecret);
		param.put("hours", this.hours);
		List<RequestHead> requestHeads = new ArrayList<RequestHead>();
		requestHeads.add(new RequestHead("Content-Type", "application/json"));
		try {
			Token token = new Token();
			token.setCreateTime(new Date());
			String json = getJsonFromRpc(jlxInterfaceUri + TOKEN_SERVICE,
					param, requestHeads);

			if (!"".equals(json)) {
				if("true".equals(JSONUtils.getStringByKey(json,"success"))){
					token.setAccessToken(JSONUtils.getStringByKey(json,"access_token"));
					token.setExpiresIn(JSONUtils.getStringByKey(json,"expires_in"));
					token.setNote(JSONUtils.getStringByKey(json,"note"));
					TokenStore.setJlxToken(clientSecret, token);
					return token;
				}else{
					log.debug(JSONUtils.getStringByKey(json,"note"));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	/**
	* @Title: access_report_data 
	* @Description: 根据用户基本信息返回JSON分析数据 
	* @param @return    设定文件 
	* @return Token    返回类型 
	* @throws
	 */
	public String accessReportData(String name,String idcard,String phone){
		Token token = getToken();
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("client_secret", this.clientSecret);
		param.put("access_token", token.getAccessToken());
		param.put("name", name);
		param.put("idcard", idcard);
		param.put("phone", phone);
		
		List<RequestHead> requestHeads = new ArrayList<RequestHead>();
		requestHeads.add(new RequestHead("Content-Type", "application/json"));
		try {
			String json = getJsonFromRpc(jlxInterfaceUri + BASE_INFO_SERVICE,
					param, requestHeads);
			if (!"".equals(json)) {
				if("true".equals(JSONUtils.getStringByKey(json,"success"))){
					saveAccessReportData(JSONUtils.getStringByKey(json,"report_data"),name,idcard,phone);
					return json;
				}else{
					log.debug(JSONUtils.getStringByKey(json,"note"));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	/**
	* @Title: access_raw_data 
	* @Description: 根据用户基本信息返回JSON分析数据 
	* @param @return    设定文件 
	* @return Token    返回类型 
	* @throws
	 */
	public String accessRawData(String name,String idcard,String phone){
		Token token = getToken();
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("client_secret", this.clientSecret);
		param.put("access_token", "52c52b8180a64b528de14fa15ab4f48a");
		param.put("name", name);
		param.put("idcard", idcard);
		param.put("phone", phone);
		
		List<RequestHead> requestHeads = new ArrayList<RequestHead>();
		requestHeads.add(new RequestHead("Content-Type", "application/json"));
		try {
			String json = getJsonFromRpc(jlxInterfaceUri + INFO_OF_OPERATOR_SERVICE,
					param, requestHeads);
			if (!"".equals(json)) {
				
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	
	
	/**
	 * 获取webSit
	 * @param name
	 * @param idCard
	 * @param phone
	 * @return
	 */
	public String getWebSit(String name,String idCard,String phone){
		List<RequestHead> requestHeads = new ArrayList<RequestHead>();
		requestHeads.add(new RequestHead("Content-Type", "application/json"));
		List<Cookie> cookies = new ArrayList<Cookie>();
		String req="{\"basic_info\":{\"name\":\""+name+"\",\"id_card_num\":\""+idCard+"\",\"cell_phone_num\":\""+phone+"\"}}";
		String json = getJsonFromRpc(jlxInterfaceUri+RETURN_WEB_SIT_SERVICE+"/"+orgName,
				JSONUtils.toJSONString(req), requestHeads);
		return json;
	}
	
	
	/**
	 * 提交采集请求
	 * @param submitCaptureReq
	 * @return
	 */
	public String submitCaptureInfo(SubmitCaptureReq submitCaptureReq) {
		String param = JSONUtils.toJSONString(submitCaptureReq);
		List<RequestHead> requestHeads = new ArrayList<RequestHead>();
		requestHeads.add(new RequestHead("Content-Type", "application/json"));
		List<Cookie> cookies = new ArrayList<Cookie>();
		String json = getJsonFromRpc(jlxInterfaceUri
				+ this.SUBMIT_CAPTURE_INFO_SERVICE,
				JSONUtils.toJSONString(submitCaptureReq), requestHeads);
		return json;
	}
	
	
	/**
	 * 提交采集信息
	 * @param name
	 * @param idCard
	 * @param phone
	 * @return
	 */
	public String submitCapture(String name,String idCard,String account,String password,String token,String website,String captcha){
		
		SubmitCaptureReq submitCaptureReq = new SubmitCaptureReq();
		submitCaptureReq.setAccount(account);
		submitCaptureReq.setPassword(password);
		if(captcha == null || "".equals(captcha)){
			//获取短信验证码以及采集信息
			String websit = this.getWebSit(name, idCard, account);
			if(!"true".equals(JSONUtils.getStringByKey(websit,"success"))){
				return websit;
			}
			String data = JSONUtils.getStringByKey(websit, "data");
			String datasource = JSONUtils.getStringByKey(data, "datasource");
			String web_site = JSONUtils.getStringByKey(datasource, "website");
			String web_token = JSONUtils.getStringByKey(data, "token");
			submitCaptureReq.setWebsite(web_site);		
			submitCaptureReq.setToken(web_token);
			submitCaptureReq.setType("");
			submitCaptureReq.setCaptcha("");
			String rs = this.submitCaptureInfo(submitCaptureReq);
			if(!"true".equals(JSONUtils.getStringByKey(websit,"success"))){
				return  rs;
			}else{
				submitCaptureReq.setSuccess("true");
				return JSONUtils.toJSONString(submitCaptureReq);
			}		
		}else{
			//提交采集信息
			submitCaptureReq.setWebsite(website);
			submitCaptureReq.setToken(token);
			submitCaptureReq.setType("SUBMIT_CAPTCHA");
			submitCaptureReq.setPassword(password);
			submitCaptureReq.setCaptcha(captcha);
			String rs = this.submitCaptureInfo(submitCaptureReq);
			//判断是否成功
			if("true".equals(JSONUtils.getStringByKey(rs,"success"))){
				String data = JSONUtils.getStringByKey(rs, "data");
				//判断是否准备采集
				if(data != null && "10008".equals(JSONUtils.getStringByKey(data, "process_code"))){
					//提交采集成功的数据放到获取分析数据队列中
					TaskCache.customerLst.offer(new CustomInfo(name, idCard, account));
				}
			}
			return rs;
		}
	 
	}
	
	
	
	/**
	 * 
	* @Title: resetPassword 
	* @Description:重置密码接口
	* @param
	* @return String
	* @author Lujf
	* @2016年6月16日@上午9:51:17
	* @throws
	 */
	public String resetPassword(String token,String name,String idCard,String account,String password,String captcha,String website,String contact1,String contact2,String contact3){
		Map<String,String> param = new HashMap<String,String>();
		param.put("account", account);
		List<RequestHead> requestHeads = new ArrayList<RequestHead>();
		requestHeads.add(new RequestHead("Content-Type", "application/json"));
		if(captcha == null || "".equals(captcha)){
			String websit = this.getWebSit(name, idCard, account);
			if(!"true".equals(JSONUtils.getStringByKey(websit,"success"))){
				return websit;
			}
			String data = JSONUtils.getStringByKey(websit, "data");
			String datasource = JSONUtils.getStringByKey(data, "datasource");
			String web_site = JSONUtils.getStringByKey(datasource, "website");
			String web_token = JSONUtils.getStringByKey(data, "token");
			param.put("type", "");
			param.put("website", web_site);
			param.put("token",web_token);
			param.put("captcha", "");	
			String json = getJsonFromRpc(jlxInterfaceUri
					+ this.RESERT_PASSWORD_SERVICE,
					JSONUtils.toJSONStringExpNull(param), requestHeads);
			if(!"true".equals(JSONUtils.getStringByKey(websit,"success"))){
				return  json;
			}else{
				param.put("success", "true");
				return JSONUtils.toJSONString(param);
			}		
		}
		param.put("token",token);
		param.put("password", password);
		param.put("captcha", captcha);
		param.put("website", website);
		param.put("type", "SUBMIT_RESET_PWD");
		param.put("contact1", contact1);
		param.put("contact2", contact2);
		param.put("contact3", contact3);
		List<Cookie> cookies = new ArrayList<Cookie>();
		String rs = getJsonFromRpc(jlxInterfaceUri
				+ this.RESERT_PASSWORD_SERVICE,
				JSONUtils.toJSONStringExpNull(param), requestHeads);
		
		return rs;
		
	}
	
	
	/**
	 * 保存基本信息移动运营商分析数据
	 * @author lujf
	 * @param json
	 *
	 */
	public void saveAccessReportData(String json,String name,String idcard,String phone){
		 //信息核对数据
		 String appcheck = JSONUtils.getStringByKey(json,"application_check");
		 List<Map<String, Object>> applicationCheck = JSONUtils.toList(appcheck);
		 //行为检测
		 String behcheck = JSONUtils.getStringByKey(json,"behavior_check");
		 
		 List<Map<String, Object>> behaviorCheck = JSONUtils.toList(behcheck);
		 
		 List<Map<String,Object>> rs = new ArrayList<Map<String,Object>>();
		 
		 if(applicationCheck != null){
			 rs.addAll(applicationCheck);
		 }
		 
		 if(behaviorCheck != null){
			 rs.addAll(behaviorCheck);
		 }
		 
		 Map<String,String> dataMap = new HashMap<String,String>();
		 
		 for(Map<String,Object> entity : rs){
			 dataMap.put((String)entity.get("check_point"), (String)entity.get("result"));
		 }
		 
		 AppCheck appCheck = new AppCheck();
		 parseColumn(DataMapping.appCheckMaping,appCheck, dataMap);
		 appCheck.setName(name);
		 appCheck.setIdCard(idcard);
		 appCheck.setMobile(phone);
		 appCheck.setJson(json);
		 appCheck.setCreateDate(new Date());
		 String contList = JSONUtils.getStringByKey(json,"contact_list");
		 List<Map<String, Object>> contactList = JSONUtils.toList(contList);	 
		 jxlEntityService.insertInfoAndCallInfo(appCheck, contactList);
		 
		 
	}

	/**
	 * 接口调用
	 * 
	 * @author lujf
	 * @param url
	 * @param param
	 * @param requestHeads
	 * @return
	 * @date 2016年5月26日
	 */
	public String getJsonFromRpc(String url, Map<String, String> param,
			List<RequestHead> requestHeads) {
		List<Cookie> cookies = new ArrayList<Cookie>();
		String json = "";
		try {
			json = (String) httpConnect.request(url, "get", param, "string",
					"UTF-8", cookies, requestHeads);
		} catch (HttpException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (CaptureException e) {
			log.error(e.getErrorCode() + ":" + e.getMessage());
		}
		return json;
	}
	
	
	
	/**
	 * 接口调用
	 * 
	 * @author lujf
	 * @param url
	 * @param param
	 * @param requestHeads
	 * @return
	 * @date 2016年6月6日
	 */
	public String getJsonFromRpc(String url, String param,
			List<RequestHead> requestHeads) {
		List<Cookie> cookies = new ArrayList<Cookie>();
		String json = "";
		try {
			json = (String) httpConnect.request(url, param, "post", "UTF-8",
					cookies, requestHeads);
		} catch (HttpException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (CaptureException e) {
			log.error(e.getErrorCode() + ":" + e.getMessage());
		}
		return json;
	}
	
	

	/**
	 * 数据回填
	 * 
	 * @param columns
	 *            列集合
	 * @param obj
	 *            对象
	 * @param dataMap
	 *            数据map
	 * @return
	 * @throws CaptureException
	 */
	public <T> T parseColumn(Map<String,String> columns, T obj,
			Map<String, String> dataMap) {
		if (columns == null) {
			return null;
		}
		
		for(Map.Entry<String, String> entity : columns.entrySet()){
			String property = entity.getKey();
			String property_val = entity.getValue();
			String val = dataMap.get(property_val.trim());
			try {
				if (obj.getClass().getDeclaredField(property) != null && val != null && !"".equals(val)){
					Method method = obj
							.getClass()
							.getMethod(
									"set" + StringUtil.upperCaseFirst(property),
									obj.getClass().getDeclaredField(property)
											.getType());
					
					method.invoke(obj,val);
 
				}
			} catch (Exception e) {
				log.error("动态调用失败："+property+":"+val);
			}
			
		}
		 
		
	 
		return obj;
	}
	
	
	
	public static void main(String[] arg){
		JlxServiceImp jlxService = new JlxServiceImp();
//		Token token = jlxService.getToken();
//		System.out.println(token.getAccessToken());
		//String str = jlxService.accessReportData("蔡杭军", "339011197809199014", "15558163511");
		String json = jlxService.resetPassword("","蔡杭军", "339011197809199014", "18806756337", "", "", "", "", "", "");
		Map<String,String> map = JSONUtils.toBean(json, Map.class);
//		
		Scanner sc = new Scanner(System.in);
		String capture = sc.nextLine();
//		
		String res = jlxService.resetPassword(map.get("token"),"蔡杭军","339011197809199014", map.get("account"), "888999", capture, map.get("website"), null, null, null);
 		System.out.println(res+"this is result");
		
//		jlxService.access_raw_data("蔡振", "339011197809199014", "18806756337");
	}
}
