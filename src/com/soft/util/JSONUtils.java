package com.soft.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * json工具类
 * 
 * @author lujf 2016年5月27日
 */
public class JSONUtils {
	/**
	 * 将List对象序列化为JSON文本
	 * 
	 * @author lujf
	 * @param list
	 * @return
	 * @date 2016年5月27日
	 */
	public static <T> String toJSONString(List<T> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	/**
	 * 将对象序列化为JSON文本
	 * 
	 * @author lujf
	 * @param object
	 * @return
	 * @date 2016年5月27日
	 */
	public static String toJSONString(Object object) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject.toString();

	}
	
	
	/**
	 * 获取指定键的字符串
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getStringByKey(String json,String key){
		JSONObject jsonObject = JSONObject.fromObject(json);
		return jsonObject.getString(key);
	}
	/**
	 * 
	 * 不转换空字符串或空对象
	 * @author lujf
	 * @param object
	 * @return
	 * @date 2016年5月27日
	 */
	public static String toJSONStringExpNull(Object object) {

		JsonConfig config = new JsonConfig();  
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				// TODO Auto-generated method stub
				if(arg2 != null && !"".equals(arg2)){
					return false;
				}
				return true;
			}
		});
		JSONObject jsonObject = JSONObject.fromObject(object,config);
		return jsonObject.toString();

	}

	/**
	 * 将JSON对象数组序列化为JSON文本
	 * 
	 * @author lujf
	 * @param jsonArray
	 * @return
	 * @date 2016年5月27日
	 */
	public static String toJSONString(JSONArray jsonArray) {
		return jsonArray.toString();
	}

	/**
	 * 将JSON对象序列化为JSON文本
	 * 
	 * @author lujf
	 * @param jsonObject
	 * @return
	 * @date 2016年5月27日
	 */
	public static String toJSONString(JSONObject jsonObject) {
		return jsonObject.toString();
	}

	/**
	 * 将对象转换为List对象
	 * 
	 * @author lujf
	 * @param object
	 * @return
	 * @date 2016年5月27日
	 */
	public static List toArrayList(Object object) {
		List arrayList = new ArrayList();
		JSONArray jsonArray = JSONArray.fromObject(object);
		Iterator it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();
			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				Object key = keys.next();
				Object value = jsonObject.get(key);
				arrayList.add(value);
			}
		}
		return arrayList;
	}

	/**
	 * 将对象转换为Collection对象
	 * 
	 * @author lujf
	 * @param object
	 * @return
	 * @date 2016年5月27日
	 */
	public static Collection toCollection(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return JSONArray.toCollection(jsonArray);
	}

	/**
	 * 将对象转换为JSON对象数组
	 * 
	 * @author lujf
	 * @param object
	 * @return
	 * @date 2016年5月27日
	 */
	public static JSONArray toJSONArray(Object object) {
		return JSONArray.fromObject(object);
	}

	/**
	 * 将对象转换为JSON对象
	 * 
	 * @author lujf
	 * @param object
	 * @return
	 * @date 2016年5月27日
	 */
	public static JSONObject toJSONObject(Object object) {
		return JSONObject.fromObject(object);
	}

	/**
	 * 一处不字段
	 * 
	 * @param json
	 * @param fields
	 * @return
	 */
	public static String removeFields(String json, String[] fields) {
		JSONObject jsonObject = JSONUtils.toJSONObject(json);
		for (String field : fields) {
			jsonObject.remove(field);
		}
		return jsonObject.toString();
	}

	/**
	 * 将对象转换为HashMap
	 * 
	 * @author lujf
	 * @param object
	 * @return
	 * @date 2016年5月27日
	 */
	public static HashMap toHashMap(Object object) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		JSONObject jsonObject = JSONUtils.toJSONObject(object);
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			data.put(key, value);
		}
		return data;
	}

	/**
	 * 将对象转换为List
	 * 
	 * @author lujf
	 * @param object
	 * @return
	 * @date 2016年5月27日
	 */
	public static List<Map<String, Object>> toList(Object object) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = JSONArray.fromObject(object);
		for (Object obj : jsonArray) {
			JSONObject jsonObject = (JSONObject) obj;
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator it = jsonObject.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = jsonObject.get(key);
				map.put((String) key, value);
			}
			list.add(map);
		}
		return list;

	}

	/**
	 * 将JSON对象数组转换为传入类型的List
	 * 
	 * @author lujf
	 * @param jsonArray
	 * @param objectClass
	 * @return
	 * @date 2016年5月27日
	 */
	public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
		return (List) JSONArray.toCollection(jsonArray, objectClass);
	}

	/**
	 * 将JSON对象转换为传入类型的对象
	 * 
	 * @author lujf
	 * @param jsonObject
	 * @param beanClass
	 * @return
	 * @date 2016年5月27日
	 */
	public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/**
	 * json字符串转换为相应对象
	 * 
	 * @author lujf
	 * @param jsonStr
	 * @param beanClass
	 * @return
	 * @date 2016年5月27日
	 */
	public static <T> T toBean(String jsonStr, Class<T> beanClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/**
	 * 将将对象转换为传入类型的对象
	 * 
	 * @author lujf
	 * @param object
	 * @param beanClass
	 * @return
	 * @date 2016年5月27日
	 */
	public static <T> T toBean(Object object, Class<T> beanClass) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/**
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @author lujf
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName
	 *            从实体类在主实体类中的属性名称
	 * @param detailClass
	 *            从实体类型
	 * @return
	 * @date 2016年5月27日
	 */
	public static <T, D> T toBean(String jsonString, Class<T> mainClass,
			String detailName, Class<D> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray = (JSONArray) jsonObject.get(detailName);
		T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
		List<D> detailList = JSONUtils.toList(jsonArray, detailClass);
		try {
			BeanUtils.setProperty(mainEntity, detailName, detailList);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}
		return mainEntity;
	}

	/**
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @author lujf
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName1
	 *            从实体类在主实体类中的属性
	 * @param detailClass1
	 *            从实体类型
	 * @param detailName2
	 *            从实体类在主实体类中的属性
	 * @param detailClass2
	 *            从实体类型
	 * @return
	 * @date 2016年5月27日
	 */
	public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass,
			String detailName1, Class<D1> detailClass1, String detailName2,
			Class<D2> detailClass2) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
		T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
		List<D1> detailList1 = JSONUtils.toList(jsonArray1, detailClass1);
		List<D2> detailList2 = JSONUtils.toList(jsonArray2, detailClass2);
		try {
			BeanUtils.setProperty(mainEntity, detailName1, detailList1);
			BeanUtils.setProperty(mainEntity, detailName2, detailList2);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}
		return mainEntity;
	}

	/**
	 * 
	 * @author lujf
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName1
	 *            从实体类在主实体类中的属性
	 * @param detailClass1
	 *            从实体类型
	 * @param detailName2
	 *            从实体类在主实体类中的属性
	 * @param detailClass2
	 *            从实体类型
	 * @param detailName3
	 *            从实体类在主实体类中的属性
	 * @param detailClass3
	 *            从实体类型
	 * @return
	 * @date 2016年5月27日
	 */

	public static <T, D1, D2, D3> T toBean(String jsonString,

	Class<T> mainClass, String detailName1, Class<D1> detailClass1,

	String detailName2, Class<D2> detailClass2, String detailName3,
			Class<D3> detailClass3) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
		JSONArray jsonArray3 = (JSONArray) jsonObject.get(detailName3);
		T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
		List<D1> detailList1 = JSONUtils.toList(jsonArray1, detailClass1);
		List<D2> detailList2 = JSONUtils.toList(jsonArray2, detailClass2);
		List<D3> detailList3 = JSONUtils.toList(jsonArray3, detailClass3);
		try {
			BeanUtils.setProperty(mainEntity, detailName1, detailList1);
			BeanUtils.setProperty(mainEntity, detailName2, detailList2);
			BeanUtils.setProperty(mainEntity, detailName3, detailList3);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}
		return mainEntity;
	}

	/**
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @author lujf
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailClass
	 *            存放了多个从实体在主实体中属性名称和类型
	 * @return
	 * @date 2016年5月27日
	 */
	public static <T> T toBean(String jsonString, Class<T> mainClass,
			HashMap<String, Class> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
		for (Object key : detailClass.keySet()) {
			try {
				Class value = (Class) detailClass.get(key);
				BeanUtils.setProperty(mainEntity, key.toString(), value);
			} catch (Exception ex) {
				throw new RuntimeException("主从关系JSON反序列化实体失败！");
			}
		}
		return mainEntity;
	}
}
