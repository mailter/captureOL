package com.soft.jxl.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据映射
 * @author lujf
 *
 */
public class DataMapping {
	public static Map<String,String> appCheckMaping = new HashMap<String,String>(){{
		put("isBindOper", "是否绑定运营商");
		put("isBindEc", "是否绑定电商");
		put("isBindTripweb", "是否绑定出行网站");
		put("isBindXxweb", "是否绑定学信网");
		put("isBindPf", "是否绑定公积金");
		put("isBindNuminfo", "是否提交登记号码信息");
		put("idcardIsVaild", "身份证号码是否有效");
		put("nameIsmatchOper", "姓名是否与运营商数据匹配");
		put("idcardIsmatchOper", "身份证号码是否与运营商数据匹配");
		put("nameIsmatchXxweb", "姓名是否与学信网数据匹配");
		put("idccardIsmatchXxweb", "身份证号码是否与学信网数据匹配");
		put("nameIsmatchPf", "姓名是否与公积金数据匹配");
		put("idcardIsmatchPf", "身份证号码是否与公积金数据匹配");
		put("blistOfCourt", "申请人姓名+身份证是否出现在法院黑名单");
		put("blistOfBank", "申请人姓名+身份证是否出现在金融服务类机构黑名单");
		put("mobilblistOfBank", "申请人姓名+手机号码是否出现在金融服务类机构黑名单");
		put("circleOfFs", "朋友圈在哪里");
		put("macauCallInfo", "澳门电话通话情况");
		put("callPoliceInfo", "110话通话情况 ");
		put("callLawyerInfo", "律师号码通话情况 ");
		put("callCourtInfo", "法院号码通话情况");
		put("phoneUedDate", "号码使用时间");
		put("buyLotteryInfo", "彩票购买情况");
		put("address", "居住地址定位");
		put("workAddress", "工作地址定位");
		put("nocturnalInfo", "夜间活动情况");
		put("loanNumberContact", "贷款类号码联系情况");
	}};
}
