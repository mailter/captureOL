package com.soft.ol.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * �õ���ǰ����Ϊ���ڼ�
     */
    public static String getCurrentWeek(){
        Calendar now=Calendar.getInstance();
        int day= now.get(Calendar.DAY_OF_WEEK)-1;
        String week="";
        switch (day) {
            case 0:week="������";break;
            case 1:week="����һ";break;
            case 2:week="���ڶ�";break;
            case 3:week="������";break;
            case 4:week="������";break;
            case 5:week="������";break;
            case 6:week="������";break;
        }
        return week;
    }
    /**
     * �õ�һ������Ϊ���ڼ�
     */
    public static int  getCurrentWeek1(String date1)throws ParseException{
        if(null==date1||"".equals(date1))return -1;
        Calendar now=Calendar.getInstance();
        now.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date1));
        int day= now.get(Calendar.DAY_OF_WEEK)-1;
        int  week=0;
        switch (day) {
            case 0:week=0;break;
            case 1:week=1;break;
            case 2:week=2;break;
            case 3:week=3;break;
            case 4:week=4;break;
            case 5:week=5;break;
            case 6:week=6;break;
        }
        return week;
    }
    /**
     * �õ���ǰ����
     */
    public static String getCurrentDate(){

        return getCurrentDate("yyyy-MM-dd");
    }

    /**
     * �õ���ǰʱ��
     */
    public static String getCurrentTime(){
        return getCurrentDate("HH:mm:ss");
    }

    /**
     * �õ���ǰ���ڼ�ʱ��
     */
    public static String getCurrentDateTime(){
        return getCurrentDate("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * �õ���ǰ����,��ʽ���û��Զ���,Ĭ��Ϊ��ʽΪ��yyyy-MM-dd HH:mm:ss
     * @param dateFormat
     */
    public static String getCurrentDate(String dateFormat){

        if(dateFormat==null) dateFormat="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        Calendar now=Calendar.getInstance();

        return sdf.format(now.getTime());

    }
    public static String formatDate(String date,String dateFormat)throws ParseException{
    	if(null==date||"".equals(date)||date.length()<9)return "";
        if(dateFormat==null) dateFormat="yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        Calendar now=Calendar.getInstance();
        now.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        return sdf.format(now.getTime());  
    }
    

    /**
     * �õ�ָ������N��������
     */
    public static String getAddDateSpec(String dateType,String dateStr,int amount)
            throws ParseException{

        int dateWay=0;
        dateType=dateType.toUpperCase();
        if(dateType.equals("YEAR")) dateWay=Calendar.YEAR;
        if(dateType.equals("MONTH")) dateWay=Calendar.MONTH;
        if(dateType.equals("DATE")) dateWay=Calendar.DATE;
        Calendar now=Calendar.getInstance();
        now.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
        now.add(dateWay,amount);

        return (new SimpleDateFormat("yyyy-MM-dd").format(now.getTime()));
    }

    /**
     * �õ���ǰ����N��������
     */
    public static String getAddCurrentDate(String dateType,int amount){
        int dateWay=0;
        dateType=dateType.toUpperCase();
        if(dateType.equals("YEAR")) dateWay=Calendar.YEAR;
        if(dateType.equals("MONTH")) dateWay=Calendar.MONTH;
        if(dateType.equals("DATE")) dateWay=Calendar.DATE;
        Calendar now=Calendar.getInstance();
        now.add(dateWay,amount);

        return (new SimpleDateFormat("yyyy-MM-dd").format(now.getTime()));
    }

    /**
     * ����2005-05-18�ַ�
     */
    public static String toDateString(Date ts){
        return toDateTimeString(ts,"yyyy-MM-dd");
    }
    /**
     * ����08:59:58�ַ�
     */
    public static String toTimeString(Date ts){
        return toDateTimeString(ts,"HH:mm:ss");
    }
    public static String toDateTimeString(Date ts){
        return toDateTimeString(ts,"yyyy-MM-dd HH:mm:ss");
    }

    public static String toDateTimeString(Date  ts,String dateFormat){
        if(ts==null) return "";
        if(dateFormat==null) dateFormat="yyyy-MM-dd HH:mm:ss";
        java.util.Date date=new java.util.Date(ts.getTime());
        return (new SimpleDateFormat(dateFormat).format(date));
    }

    /**
     * �Ƚ�}��ʱ���ǰ��,���ǰ�����ں����򷵻�true������false����ͬʱ��Ļ�Ҳ��false��
     */
    public static boolean judgeDate1(String date1,String date2) throws ParseException {
        if(null==date1||"".equals(date1))return false;
        if(null==date2||"".equals(date2))return true;
        Calendar calendarDate1=Calendar.getInstance();
        calendarDate1.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date1));
        Calendar calendarDate2=Calendar.getInstance();
        calendarDate2.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date2));
        return calendarDate1.before(calendarDate2);
    }
    public static boolean judgeDate2(String date1,String date2) throws ParseException {
        if(null==date1||"".equals(date1))return true;
        if(null==date2||"".equals(date2))return false;
        Calendar calendarDate1=Calendar.getInstance();
        calendarDate1.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date1));
        Calendar calendarDate2=Calendar.getInstance();
        calendarDate2.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date2));
        return calendarDate1.before(calendarDate2);
    }

    /**
     * ����}��ʱ��������
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static long dateDiff(String date1,String date2) throws ParseException {
        if(null==date1||"".equals(date1))return 0;
        if(null==date2||"".equals(date2))return 0;
        Calendar calendarDate1=Calendar.getInstance();
        Calendar calendarDate2=Calendar.getInstance();
        calendarDate1.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date1));
        calendarDate2.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date2));
        long diffDate=calendarDate1.getTimeInMillis()-calendarDate2.getTimeInMillis();
        diffDate=diffDate/24/60/60/1000;
        if(diffDate>=0)diffDate=diffDate+1;
        else diffDate = diffDate - 1;
        return diffDate;
    }
    	/**
	 * ʱ��ȽϺ�����棭ǰ��
	 * @param type ���ͣ����գ�Ϊdd
	 * 		dd: ����
	 * 		hh: Сʱ
	 * 		mi: ����
	 * 		ss: �� 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 * @Author: zhangyw 20081120
	 */
		public static long dateDiff(String type, String date1, String date2) throws ParseException{
        if(null==type||"".equals(type))type="dd";
        if(null==date1||"".equals(date1))return 0;
        if(null==date2||"".equals(date2))return 0;
        long diff = 0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long param = 1; 
				if("dd".equals(type)){ 
					df = new SimpleDateFormat("yyyy-MM-dd");
					param =  1000 * 60 * 60 * 24;
				}else if("hh".equals(type)){
					df = new SimpleDateFormat("yyyy-MM-dd HH"); 
					param = 1000 * 60 * 60;
				}else if("mi".equals(type)){
					df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					param = 1000 * 60 ;
				}else if("ss".equals(type)){
					df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					param = 1000 ;
				}  
				Date d2 = df.parse(date2);
				Date d1 = df.parse(date1);
				diff = d2.getTime() - d1.getTime();
				diff = diff /param;
				return diff;
		} 
		
    public static String queryDate(int type,String queryDate){
        if(null==queryDate||"".equals(queryDate))return queryDate;
        if(queryDate.length()<10)return queryDate;
        String newDate = queryDate.substring(0,10);
        if(type==0) newDate=newDate+" 00:00:00";
        else newDate=newDate+" 23:59:59";
        return newDate;
    }
    //�������ʱ��,����һ��������
    public static int  workingDayGMT(String dayStar,String dayEnd)  throws ParseException{
        int workDays;
        long works,remain,workStar,workEnd;    //�������ڣ��ڼ��죬��ʼ���ڼ����������ڼ���
        long days;     //����ʱ�������]
        days =DateUtil.dateDiff(dayEnd,dayStar);
        if(days==0)return 0;

        workStar =DateUtil.getCurrentWeek1(dayStar);
        workEnd =DateUtil.getCurrentWeek1(dayEnd);
        works=days/7;
        remain=days%7;
        if((workStar==0&&workEnd==0)||(workStar==6&&workEnd==6)||(workStar==0&&workEnd==6)||(workStar==6&&workEnd==0)){
            remain=0;
        }else{
            if(workStar==0||workStar==6)workStar=1;
            if(workEnd==0||workEnd==6)workEnd=5;
            if(workStar>workEnd&&remain!=0)workEnd=workEnd+5;
            remain=workEnd-workStar+1;
        }
        workDays=Integer.parseInt(""+(works*5+remain));
        return workDays;
    }
    
    
    public static String[] getDateOfThisWeek(Calendar cal) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//Calendar cal = Calendar.getInstance();
		int index = cal.get(Calendar.DAY_OF_WEEK); // �����Ǳ��ܵĵڼ���
		// ת���й��ϰ��,����ǵ�һ��,��ת��Ϊ������(���������Ϊһ�ܵĵ�һ��,���й�Ϊÿ�ܵ����һ��)
		if (index == 1)
			index = 8;  
		cal.add(Calendar.DATE, -(index - 2));

		String start = (sdf.format(cal.getTime()));

		cal.add(Calendar.DATE, 6);
		String end = (sdf.format(cal.getTime()));
		String[] result = new String[] { start, end };
		return result;
	}    
  /**
   * 
   * ˵�� ��õ�ǰ���������ܵĵ�һ�������
   *
   * marui
   */ 
    public static String getCurrFirstDayOfWeek(){
    	Calendar cal = Calendar.getInstance();
    	String [] s = getDateOfThisWeek(cal);
    	return s[0];
    }
    
    
    /**
     * ��ȡָ�����������ܵĵ�һ������
     * @param date
     * @return
     */
    public static String getFirstDayOfWeek(Calendar calendar){
//    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		//Calendar calendar = Calendar.getInstance();
//		//calendar.setTime(date);
//		calendar.setFirstDayOfWeek(Calendar.MONDAY);
//		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//		return format.format(calendar.getTime()); 
    	String [] s = getDateOfThisWeek(calendar);
    	return s[0];
    }
    
    /**
     * ��ȡָ�����������ܵ����һ������
     * @param date
     * @return
     */
    public static String getLastDayOfWeek(Calendar calendar){
//    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		//Calendar calendar = Calendar.getInstance();
//		//calendar.setTime(date);
//		calendar.setFirstDayOfWeek(Calendar.MONDAY);
//		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

    	String [] s = getDateOfThisWeek(calendar);
    	return s[1];
    }
    
    /**
     * 
     * ˵�� ��ȡ��ǰ���������ܵ����һ������ڡ�
     *
     * Author: marui
     * @return
     */
    public static String getCurrLastDayOfWeek(){
//    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar calendar = Calendar.getInstance();
//		calendar.setFirstDayOfWeek(Calendar.MONDAY);
//		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//		return format.format(calendar.getTime());  
    	Calendar calendar = Calendar.getInstance();
    	String [] s = getDateOfThisWeek(calendar);
    	return s[1];
    }
    
    /**
     * 
     * ˵�� ��ȡ��ǰ���������µĵ�һ��
     *
     * Author: marui
     * @return
     */
    public static String getCurrFirstDayOfMonth(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());  
    }
    
    /**
     * 
     * ˵�� get Lastest day of current Month;
     *
     * Author: marui
     * @return
     */
    public static String getCurrLastDayOfMonth(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());
    }
    
    /**
     * 
     * ˵�� ��ȡ��ǰ�����ϸ��µĵ�һ��
     *
     * Author: marui
     * @return
     */
    public static String getLastFirstDayOfMonth(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());  
    }
    
    /**
     * 
     * ˵�� get Lastest day of last Month;
     *
     * Author: marui
     * @return
     */
    public static String getLastLastDayOfMonth(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());
    }
    
    /**
     * 
     * ˵�� ��ȡ��ǰ�������ϸ��µĵ�һ��
     *
     * Author: marui
     * @return
     */
    public static String getLastedFirstDayOfMonth(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-2);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());  
    }
    
    /**
     * 
     * ˵�� get Lastest day of lasted Month;
     *
     * Author: marui
     * @return
     */
    public static String getLastedLastDayOfMonth(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-2);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());
    }
    
    /**
     * 
     * ˵�� get first day of the current year;
     *
     * Author: marui
     * @return
     */
    public static String getCurrFirstDayOfYear(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		return format.format(calendar.getTime());
    }
    
    /**
     * 
     * ˵�� get lastest day of the current year
     *
     * Author: marui
     * @return
     */
    public static String getCurrLastDayOfYear(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		return format.format(calendar.getTime());
    }
    
    /**
     * 
     * ˵�� get first day of the last year;
     *
     * Author: marui
     * @return
     */
    public static String getLastFirstDayOfYear(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year-1);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		return format.format(calendar.getTime());
    }
    
    /**
     * 
     * ˵�� get lastest day of the last year
     *
     * Author: marui
     * @return
     */
    public static String getLastLastDayOfYear(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year-1);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		return format.format(calendar.getTime());
    }
    
    /**
     * 
     * ˵�� get first day of the lasted year;
     *
     * Author: marui
     * @return
     */
    public static String getLastedFirstDayOfYear(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year-2);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		return format.format(calendar.getTime());
    }
    
    /**
     * 
     * ˵�� get lastest day of the lasted year
     *
     * Author: marui
     * @return
     */
    public static String getLastedLastDayOfYear(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year-2);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		return format.format(calendar.getTime());
    }
}
