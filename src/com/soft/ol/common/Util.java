package com.soft.ol.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Util {
	private static Log log = LogFactory.getLog(Util.class);
	private static final String FILE_NAME="application.properties";
	private static Properties pp=null;
	
	public static void init() {
		InputStream in = Util.class.getClassLoader().getResourceAsStream(FILE_NAME);
		pp = new Properties();
		try{
			pp.load(in);			
		}catch(IOException e){
			log.info("read the datasource property file error");
			e.printStackTrace();
		}		
	}
	
	public static Properties getProperties() {
		if(pp==null){
			init();
		}
		return pp;
	}
	
	public String subStringLen(){
		Properties p = this.getProperties();
		String len= p.getProperty("SUBSTRUNGLEN");
		return len;	
	}
	
	public String reCompanyCode(){
		Properties p = this.getProperties();
		String cityCode= p.getProperty("CITYCODE");
		String companyCode= p.getProperty("COMPANY");
		String code =cityCode+companyCode;
		return code;	
	}
	
	public String imagePath(){
		Properties p = this.getProperties();
		String path=p.getProperty("IMAGEPATH");
		return path;
	}
	
	public String cabinetPath(){
		Properties p = this.getProperties();
		String path=p.getProperty("CABINETPATH");
		return path;
	}
	
	public static String zeroAddzdy(String string,int data){
		String orderId = "";
		String zero = "";
		int strlength = string.length();
		if(strlength >= data){
			orderId = string.substring(0,data);  
		}else{
			int i = data - strlength;
			for (int j = 0; j < i; j++) {
				zero = zero + "0";
			}
			orderId = zero + string;
		}
		return orderId;
	}
	
	public String covertDouble (String strPrice){
		DecimalFormat df = new DecimalFormat("0.00");
		String price =df.format(Double.parseDouble(strPrice));
		return price;
	}
	
	public String strReplace(String strOld,String strNew,String type,String synbol){
		int num1= strOld.indexOf(type);
		int num2= strOld.indexOf(synbol);
		String tempStr=strOld.substring(num1, num2);
		strOld.replace(tempStr, strNew);
		return strOld.replace(tempStr, strNew);
	}
	
	public Boolean deleteImage(String imagePath){
		Boolean flag=false;
		File theFile = new File(imagePath);
	    if (theFile.exists()){
	    	flag= theFile.delete();
	    }
		return flag;
		
	}
	
	public String geRundomPassWord(){
		String strPw="0";
		strPw=String.valueOf(Math.random());
		return strPw.substring(3,9);
		
	}

	

	public JSONArray jsonConvert(List list){
		JSONArray jsonArray=JSONArray.fromObject(list);
		
		return jsonArray;		
	}
	
	public JSONArray getjsonArray(String jsonString,String type){
		
		JSONObject jsonObj = JSONObject.fromObject(jsonString); 
		
		JSONArray jsonarr = jsonObj.getJSONArray(type);
		return jsonarr;  

	}
	
	public JSONObject getjsonObj(String jsonString){
		
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		return jsonObj; 
	}
	
	
	public String getjsonArrayForCredit(String jsonString){
		
		JSONObject jsonObj = JSONObject.fromObject(jsonString); 
		
		String str = jsonObj.getString("信誉度提示");
		return str;  

	}
	
	public String  getjsonDate(String jsonString,String type){
		
		JSONObject jsonObj = JSONObject.fromObject(jsonString); 
		
		String dataCount = String.valueOf(jsonObj.get(type));

		return dataCount; 

	}
	
	public boolean isNumeric(String str){
		   boolean flag = false;
		   if(!"".equals(str) && str != null){
		         Pattern pattern = Pattern.compile("[0-9]*");
		         Matcher isNum = pattern.matcher(str);
		         if(isNum.matches()){
		        	 flag=true;
		         }
		   }
		   return flag;
	 }
	

	/**
	 * 格式化时间
	 * @author 001334
	 * @param  String
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public String translationData(String strDate){
		Date date = null;                    
		String dateString="";
		DateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日");    
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			date = format1.parse(strDate);
			dateString = format2.format(date);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return dateString; 
	}
	
	public int getSystemDate(String  strFlag){
		Calendar c = Calendar.getInstance();
		if ("1".equals(strFlag)) {
			int year = c.get(Calendar.YEAR);
			return year;
		}else if("2".equals(strFlag)){
			int month = c.get(Calendar.MONTH); 
			return month;
		}else if ("3".equals(strFlag)){
			int date = c.get(Calendar.DATE); 
			return date;
		}else if ("4".equals(strFlag)){
			int hour = c.get(Calendar.HOUR_OF_DAY); 
			return hour;
		}else if ("5".equals(strFlag)){
			int minute = c.get(Calendar.MINUTE); 
			return minute;
		}else if ("6".equals(strFlag)){
			int second = c.get(Calendar.SECOND); 
			return second;
		}else{
			return 0;
		}

	}
	
    /**
     * Decode锟�
     *
     * @param str鏂囧瓧锟�
     * @param 澶夋彌鍓嶃伄鏂囧瓧锟�
     * @return 绲愭灉鏂囧瓧锟�
     */
    public static String paraFormatDecoder(String str) {

        String convertString =str;
        try {
                if (!str.isEmpty()){
                    convertString=URLDecoder.decode(str,"UTF-8");
                }
        } catch (UnsupportedEncodingException e) {
            // TODO 鑷嫊鐢熸垚銇曘倢锟�catch 銉栥儹銉冦偗
            e.printStackTrace();
        }
        return convertString;
    }
    
    public static String paraFormatEncode(String str) {

        String convertString =str;
        try {
                if (!str.isEmpty()){
                    convertString=URLEncoder.encode(str,"UTF-8");
                }
        } catch (UnsupportedEncodingException e) {
            // TODO 鑷嫊鐢熸垚銇曘倢锟�catch 銉栥儹銉冦偗
            e.printStackTrace();
        }
        return convertString;
    }
    
    //获得当前日期GMT
    public static String getStringTime(){
		
    	Calendar cd = Calendar.getInstance();  
    	SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d yyyy HH:mm:ss 'GMT'", Locale.US);  
    	sdf.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 设置时区为GMT  
    	String str = sdf.format(cd.getTime());  
    	String date=StringUtils.substringBeforeLast(str,"G");
    	String time=date.substring(date.indexOf(":")-2);
    	date=date.substring(0,date.indexOf(":")-2);
    	
    	str=paraFormatEncode(paraFormatEncode(date))+time.trim()+paraFormatEncode(paraFormatEncode(" "))+"GMT"+"+0800";
    	str=str.replace("%2B", "%20");
    	//http://shixin.court.gov.cn/image.jsp?date=Mon%20Apr%2025%202016%2018:16:51%20GMT+0800
    	System.out.println(str);
    	return str;

    }
    
	
    /**
     * 获取年月日毫秒
     *
     * @param  null
     * @param  null
     * @return time
     */
    public static String getDate()
    {
    	Date date=new Date();
    	DateFormat format=new SimpleDateFormat("yyMMddS");
    	String time=format.format(date)+System.currentTimeMillis();
    	System.out.println(time.length());
    	return time;
    }
    
    /**
     * 获系统日期
     * @param  null
     * @param  null
     * @return time
     */
    public static String getSysDate(String fmt)
    {
    	Date date=new Date();
    	DateFormat format=new SimpleDateFormat(fmt);
    	String sysDate=format.format(date);
    	return sysDate;
    }
	
	public static void main(String[] args) throws IOException {
		System.out.println(getSysDate("yyyy-MM-dd HH:mm:ss"));
	}
	
	//http://www.itaotm.com/search!page.php?pageNo=1&l=20160328113347&gjfls=9%3B16%3B29%3B35%3B36%3B38%3B39%3B41%3B42&gjfl=0&seat=%E4%B8%8D%E9%99%90%E6%9D%A1%E4%BB%B6&searchKey=%E4%B8%AD%E6%96%B0%E5%8A%9B%E5%90%88
	//http://www.itaotm.com/search!page.php?pageNo=2&l=20160328112413&gjfls=9%3B16%3B29%3B35%3B36%3B38%3B39%3B41%3B42&gjfl=0&seat=%E4%B8%8D%E9%99%90%E6%9D%A1%E4%BB%B6&searchKey=%E4%B8%AD%E6%96%B0%E5%8A%9B%E5%90%88

} 
	

