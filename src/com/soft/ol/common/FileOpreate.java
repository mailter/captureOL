package com.soft.ol.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileOpreate {
	
	/**  
	 * 根据路径读取文件  *   
	 * @param readPath  
	 * 读取文件的路径  	
	 * @return  
	 * @throws Exception  */ 
	public String readFile(String readPath) throws Exception {  
		return readFile(new File(readPath)); 
	}

	/**  
	 * * 读取文件  *   
	 * @param file  
	 * @return  
	 * @throws Exception  
	 * */ 
	public String readFile(File file) throws Exception {  
		BufferedReader br = new BufferedReader(new FileReader(file));  
		String line = null; 
		String count ="0";
		while ((line = br.readLine()) != null) {   
			count=line;
		}  
	 	br.close(); 
		return count;
	}
	
	 /**写入文件  
	  * @param str  
	  * 要保存的内容  
	  * @param savePath           
	  * 保存的文件路径  
	  * @throws Exception          
	  *  找不到路径 
	  */ 
	  public void writeFile(String str, String savePath) throws Exception {  
		  BufferedWriter bw = new BufferedWriter(new FileWriter(savePath));  
		  bw.write(str);  
		  bw.close(); 
	  }
	  
//	  public static void main(String[] args) {
//		  String filePath = "../gio/src/num.txt";
//		  FileOpreate fop = new FileOpreate();  
//		  String str = "22222";  
//		  try {   
//			  str = fop.readFile(filePath);
//			  System.out.println(str);  
//		  } catch (Exception e) {
//			  System.out.println("文件不存在");  
//		}
//		  
//		  try {   
//			  fop.writeFile(str, filePath);  
//		  } catch (Exception e) {   
//			  System.out.println("保存文件失败（路径错误）");  
//		  }
//	  }

}
