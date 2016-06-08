package com.soft.util;
  
public class StringUtil {
	/**
	 * 从url获取参数值
	 * 
	 * @param url
	 * @return
	 */
	public static String[] getUrlParamVal(String url) {
		String[] param_arr = null;
		String[] param_val = null;
		if (url.indexOf("?") != -1) {
			String params_str = url.substring(url.indexOf("?") + 1,
					url.length());
			if (params_str.indexOf("&") != -1) {
				param_arr = params_str.split("&");
			} else {
				param_arr = new String[] { params_str };
			}

			if (param_arr != null && param_arr.length > 0) {
				param_val = new String[param_arr.length];
				int i = 0;
				for (String key : param_arr) {
					if (key.indexOf("=") != -1) {
						String[] paramAndVal = key.split("=");
						if (paramAndVal.length > 1) {
							param_val[i] = paramAndVal[1];
						} else {
							param_val[i] = "";
						}

					}
					i++;
				}
			}
		}

		return param_val;
	}
	
	
	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upperCaseFirst(String str) {
		if (str != null && str.length() > 0) {
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}

	
}
