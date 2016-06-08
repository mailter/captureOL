package com.soft.http;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import com.soft.http.bean.RequestHead;
import com.soft.http.expextion.CaptureException;
import com.soft.util.StringUtil;
/**
 * 请求操作
 * @author lujf
 * 2016年5月26日
 */
public class HttpConnect {
	// socket超时时间
	public final int SOCKET_TIME_OUT = 30 * 1000;
	// 连接超时时间
	public final int CONNECT_TIME_OUT = 30 * 1000;

	// 请求类型
	protected enum RequestType {
		get, post;
	}

	// 返回类型
	protected enum ResultType {
		string, bytes;
	}

	/**
	 * 
	 * @author lujf
	 * @param rul
	 *            请求地址
	 * @param requestType
	 *            请求类型
	 * @param param
	 *            参数
	 * @param resultType
	 *            返回类型
	 * @param encode
	 *            编码
	 * @param cookies
	 * @param requestHeads
	 *            消息头
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @date 2016年5月25日
	 */
	public Object request(String rul, String requestType,
			Map<String, String> param, String resultType, String encode,
			List<Cookie> cookies, List<RequestHead> requestHeads)
			throws HttpException, IOException {
		if (RequestType.get.toString().equals(requestType)) {
			return getMethodRequest(rul, param, resultType, encode, cookies,
					requestHeads);
		}
		return postMethodRequest(rul, param, resultType, encode, cookies,
				requestHeads);
	}
	
	/**
	 * 
	 * @author lujf
	 * @param rul
	 * @param param
	 * @param resultType
	 * @param encode
	 * @param cookies
	 * @param requestHeads
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @date 2016年5月26日
	 */
	public Object request(String rul, String param, String resultType,
			String encode, List<Cookie> cookies, List<RequestHead> requestHeads)
			throws HttpException, IOException {
		return postMethodRequest(rul, param, resultType, encode, cookies,
				requestHeads);
	}

	/**
	 * 
	 * @author lujf
	 * @param getUrl
	 * @param param
	 * @param resultType
	 * @param encode
	 * @param cookies
	 * @param requestHeads
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @date 2016年5月25日
	 */
	protected Object getMethodRequest(String getUrl, Map<String, String> param,
			String resultType, String encode, List<Cookie> cookies,
			List<RequestHead> requestHeads) throws HttpException, IOException {
		CloseableHttpClient httpClient = this.getClient(getUrl);
		try {
			// 设置参数
			if (param != null && param.size() > 0) {
				for (Map.Entry<String, String> entry : param.entrySet()) {
					// cookie中的参数
					if (entry.getKey().indexOf("#") != -1) {
						continue;
					}
					// 判断url中是否带有#号的参数

					if (getUrl.indexOf("#" + entry.getKey()) != -1) {
						getUrl = getUrl.replace("#" + entry.getKey(),
								URLEncoder.encode(entry.getValue(), encode));
						continue;
					}

					if (getUrl.indexOf("?") == -1) {
						getUrl += "?" + entry.getKey().trim() + "="
								+ URLEncoder.encode(entry.getValue(), encode);
					} else {
						getUrl += "&" + entry.getKey().trim() + "="
								+ URLEncoder.encode(entry.getValue(), encode);
					}
				}
			}
			HttpGet httpGet = new HttpGet(getUrl);
			httpGet.setConfig(RequestConfig.custom()
					.setSocketTimeout(SOCKET_TIME_OUT)
					.setConnectTimeout(CONNECT_TIME_OUT).build());
			HttpContext context = new BasicHttpContext();
			CookieStore cookieStore = new BasicCookieStore();
			context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

			setHead(context, cookies, requestHeads, param, httpGet);
			HttpResponse response = httpClient.execute(httpGet, context);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				List<Cookie> cookies_rs = cookieStore.getCookies();
				if (cookies_rs != null && cookies_rs.size() > 0) {
					for (Cookie cookie : cookies_rs) {
						cookies.add(cookie);
					}
				}
				if (ResultType.string.toString().equals(resultType)) {
					return EntityUtils.toString(response.getEntity(), encode);
				} else if (ResultType.bytes.toString().equals(resultType)) {
					return EntityUtils.toByteArray(response.getEntity());
				}
				return EntityUtils.toString(response.getEntity(), encode);

			} else {
				throw new CaptureException(response.getStatusLine()
						.getStatusCode() + "", "", "请求" + getUrl + "失败!");
			}
		} finally {
			httpClient.close();
		}

	}

	/**
	 * post提交字符串数据
	 * 
	 * @author lujf
	 * @param postUrl
	 * @param param
	 * @param resultType
	 * @param encode
	 * @param cookies
	 * @param requestHeads
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @date 2016年5月26日
	 */
	protected Object postMethodRequest(String postUrl, String param,
			String resultType, String encode, List<Cookie> cookies,
			List<RequestHead> requestHeads) throws HttpException, IOException {
		CloseableHttpClient httpClient = this.getClient(postUrl);
		try {
			HttpPost httpPost = new HttpPost(postUrl);
//			httpPost.setConfig(RequestConfig.custom()
//					.setSocketTimeout(SOCKET_TIME_OUT)
//					.setConnectTimeout(CONNECT_TIME_OUT).build());
			HttpContext context = new BasicHttpContext();
			CookieStore cookieStore = new BasicCookieStore();
			context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
			StringEntity params = new StringEntity(param, encode);
			// 设置头部信息
			if (requestHeads != null && requestHeads.size() > 0) {
				setHead(context, cookies, requestHeads,
						new HashMap<String, String>(), httpPost);
			}
			httpPost.setEntity(params);
			HttpResponse response = httpClient.execute(httpPost, context);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 获取cookie
				List<Cookie> cookies_rs = cookieStore.getCookies();
				if (cookies_rs != null && cookies_rs.size() > 0) {
					for (Cookie cookie : cookies_rs) {
						cookies.add(cookie);
					}
				}
				if (ResultType.string.toString().equals(resultType)) {
					return EntityUtils.toString(response.getEntity(), encode);
				} else if (ResultType.bytes.toString().equals(resultType)) {
					return EntityUtils.toByteArray(response.getEntity());
				}
				return EntityUtils.toString(response.getEntity(), encode);
			} else {
				throw new CaptureException(response.getStatusLine()
						.getStatusCode() + "", "", "请求" + postUrl + "失败!");
			}
		} finally {
			httpClient.close();
		}
	}

	/**
	 * post请求
	 * 
	 * @author lujf
	 * @param postUrl
	 *            请求地址
	 * @param param
	 *            请求参数
	 * @param resultType
	 *            结果类型
	 * @param encode
	 *            编码格式
	 * @param cookies
	 * @param requestHeads
	 *            消息头
	 * @return object
	 * @throws HttpException
	 * @throws IOException
	 * @date 2016年5月25日
	 */
	protected Object postMethodRequest(String postUrl,
			Map<String, String> param, String resultType, String encode,
			List<Cookie> cookies, List<RequestHead> requestHeads)
			throws HttpException, IOException {
		CloseableHttpClient httpClient = this.getClient(postUrl);
		try {

			List<String> removeParamKey = new ArrayList<String>();

			if (postUrl.indexOf("#") != -1) {
				if (param != null && param.size() > 0) {
					for (Map.Entry<String, String> entry : param.entrySet()) {
						// 判断url中是否带有#号的参数
						if (!"".equals(entry.getKey())
								&& postUrl.indexOf("#" + entry.getKey()) != -1) {
							postUrl = postUrl
									.replace("#" + entry.getKey(), URLEncoder
											.encode(entry.getValue(), encode));
							removeParamKey.add(entry.getKey());
							continue;
						}
					}
				}
			}

			for (String remove_key : removeParamKey) {
				param.remove(remove_key);
			}

			HttpPost httpPost = new HttpPost(postUrl);
			httpPost.setConfig(RequestConfig.custom()
					.setSocketTimeout(SOCKET_TIME_OUT)
					.setConnectTimeout(CONNECT_TIME_OUT).build());
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// 设置参数
			if (param != null && param.size() > 0) {
				for (Map.Entry<String, String> entry : param.entrySet()) {
					// 带#号的key是cookie与head中的值
					if (entry.getKey().indexOf("#") != -1) {
						continue;
					}
					params.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
			}
			HttpContext context = new BasicHttpContext();
			CookieStore cookieStore = new BasicCookieStore();
			context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
			// 设置头部信息

			if (requestHeads != null && requestHeads.size() > 0) {
				setHead(context, cookies, requestHeads, param, httpPost);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(params, encode));
			HttpResponse response = httpClient.execute(httpPost, context);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 获取cookie
				List<Cookie> cookies_rs = cookieStore.getCookies();
				if (cookies_rs != null && cookies_rs.size() > 0) {
					for (Cookie cookie : cookies_rs) {
						cookies.add(cookie);
					}
				}
				if (ResultType.string.toString().equals(resultType)) {
					return EntityUtils.toString(response.getEntity(), encode);
				} else if (ResultType.bytes.toString().equals(resultType)) {
					return EntityUtils.toByteArray(response.getEntity());
				}
				return EntityUtils.toString(response.getEntity(), encode);
			} else {
				throw new CaptureException(response.getStatusLine()
						.getStatusCode() + "", "", "请求" + postUrl + "失败!");
			}
		} finally {
			httpClient.close();
		}
	}

	/**
	 * 设置头部信息
	 * 
	 * @author lujf
	 * @param context
	 * @param cookies
	 * @param requestHeadls
	 *            请求头列表
	 * @param map
	 *            请求头内容参数
	 * @param request
	 * @date 2016年5月25日
	 */
	protected void setHead(HttpContext context, List<Cookie> cookies,
			List<RequestHead> requestHeadls, Map<String, String> map,
			HttpRequest request) {
		RequestHead requestHead = null;
		String val = "";
		if (requestHeadls != null && requestHeadls.size() > 0) {
			for (int j = 0; j < requestHeadls.size(); j++) {
				requestHead = requestHeadls.get(j);
				val = requestHead.getVal();
				// cookie的参数不设置
				if ("1".equals(requestHead.getType())) {
					continue;
				}
				// 判断是否含有输入参数
				if (val.indexOf("#") != -1) {
					// 如果是http带的参数
					if (val.indexOf("http") != -1 || val.indexOf("https") != -1) {
						String[] params = StringUtil.getUrlParamVal(val);
						for (String param : params) {
							val = val.replace(
									param,
									map.get(param) == null ? "" : map
											.get(param));
						}
					} else {
						// 如果是单个参数
						val = map.get(val) == null ? "" : map.get(val);
					}
				}
				if (!"".equals(val)) {
					request.setHeader(requestHead.getHeadName(), val);
				}

			}
		}

		// 设置cookie
		if (cookies != null && cookies.size() > 0) {
			CookieStore cookieStore = new BasicCookieStore();
			context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
			for (Cookie cookie : cookies) {
				cookieStore.addCookie(cookie);
			}
		}
	}

	/**
	 * 获取连接对象
	 * 
	 * @author lujf
	 * @param url
	 * @return
	 * @date 2016年5月25日
	 */
	protected CloseableHttpClient getClient(String url) {
		if (url.indexOf("https") != -1) {
			try {
				SSLContext sslContext = new SSLContextBuilder()
						.loadTrustMaterial(null, new TrustStrategy() {
							// 信任所有证书
							public boolean isTrusted(X509Certificate[] chain,
									String authType)
									throws CertificateException {
								return true;
							}
						}).build();
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
						sslContext);
				CloseableHttpClient httpclient = HttpClients.custom()
						.setSSLSocketFactory(sslsf).build();
				return httpclient;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return HttpClients.createDefault();
	}
}
