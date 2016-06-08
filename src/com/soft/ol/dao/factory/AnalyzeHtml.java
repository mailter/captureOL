package com.soft.ol.dao.factory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.soft.ol.dto.Court;

public class AnalyzeHtml {
	
	Document doc =null;
	String flag ="";
	
	public AnalyzeHtml (String html,String strflag){
		doc=Jsoup.parse(html);
		this.flag =strflag;
	}
	
	/**
	 *解析文书列表
	 * 
	 *@return int
	 *@author lis
	 **/
	public int getRecordCountByjsoup(){
		int maxCount =0;
		
		String count=doc.getElementsByTag("strong").text();
		if (!"".equals(count) && count!=null){
			maxCount=Integer.parseInt(count);
		}
		
		return maxCount;
	}
	
	/**
	 *取得开庭信息
	 * 
	 *@return page
	 *@author lis
	 **/
	
	public ArrayList<Court> getContentByjsoup(){
		ArrayList<Court> list = new ArrayList<Court>();
		
		Element eles=doc.getElementById("report");
		Elements ElemmentTr =eles.getElementsByTag("tr");
		for (int i=1; i < ElemmentTr.size(); i++){
			Court court = new Court();
			court.setStrCourt(toTrim(ElemmentTr.get(i).select("td.10dpi").get(0).text()));
			court.setStrTribunal(toTrim(ElemmentTr.get(i).select("td.10dpi").get(1).text()));
			court.setOpenDate(toTrim(translationData(ElemmentTr.get(i).select("td.10dpi").get(2).text())));
			court.setCaseId(toTrim(ElemmentTr.get(i).select("td.10dpi").get(3).text()));
			court.setCaseContent(toTrim(toTrim(ElemmentTr.get(i).select("td.10dpi").get(4).text())));
			court.setCaseDept(toTrim(ElemmentTr.get(i).select("td.10dpi").get(5).text()));
			court.setJudge(toTrim(ElemmentTr.get(i).select("td.10dpi").get(6).text()));
			court.setPlaintiff(toTrim(ElemmentTr.get(i).select("td.10dpi").get(7).text()));
			court.setDefendant(toTrim(ElemmentTr.get(i).select("td.10dpi").get(8).text()));
			court.setDataFrom("上海");
			list.add(court);
			 }
		return list;
	}
	
//	
//	/**
//	 *取得开庭信息
//	 * 
//	 *@return page
//	 *@author lis
//	 **/
//	
//	public ArrayList<Brand> getBrandByjsoup(){
//		ArrayList<Brand> list = new ArrayList<Brand>();
//		
//		Elements eles=doc.select("table.import_hpb_list");
//		Elements ElemmentTr =eles.get(0).getElementsByTag("tr");
//		for (int i=1; i < ElemmentTr.size(); i++){
//			Brand brand = new Brand();
//			brand.setRegisterNumber(ElemmentTr.get(i).getElementsByTag("a").get(0).text());
//			brand.setTypeNumber(ElemmentTr.get(i).getElementsByTag("a").get(1).text());
//			brand.setBrandName(ElemmentTr.get(i).getElementsByTag("a").get(2).text());
//			brand.setApplicationName(ElemmentTr.get(i).getElementsByTag("a").get(3).text());
//			list.add(brand);
//		}
//		return list;
//	}
	
	private String translationData(String strDate){
		Date date = null;                    
		String dateString="";
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");    
		
		try {
			date = format1.parse(strDate);
			dateString = format1.format(date);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return dateString; 
	}
	
	
	private String toTrim(String str){
		String tmpCh ="";
		if ("".equals(str) || str ==null){
			return tmpCh;
		}else{
			tmpCh =str.trim().replace(" ", "");
		}
		return tmpCh;
	}
	
}
