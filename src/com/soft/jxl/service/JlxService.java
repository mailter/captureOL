package com.soft.jxl.service;
/**
 * 聚信立接口
 * @author lujf
 * 2016年5月25日
 */
public interface JlxService {
	//获取访问权限
	String 	TOKEN_SERVICE = "api/access_report_token";
	//用户基本信息
	String  BASE_INFO_SERVICE = "api/access_report_data";
	//用户基本信息返回运营商数据
	String  INFO_OF_OPERATOR_SERVICE = "api/access_raw_data";
	//提交采集请求服务
	String SUBMIT_CAPTURE_INFO_SERVICE = "orgApi/rest/v2/messages/collect/req";
	//提交申请表单获取回执信息
	String RETURN_WEB_SIT_SERVICE = "orgApi/rest/v2/applications";
}
