package com.soft.jxl.task;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soft.jxl.bean.CustomInfo;
import com.soft.jxl.bean.TaskCache;
import com.soft.jxl.service.JlxServiceImp;
/**
 * 获取分析数据
* @ClassName: AccessReportDataTask 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Lujf
* @date 2016年6月14日 下午4:01:01
 */
public class AccessReportDataTask{
	 protected final Logger log = LoggerFactory.getLogger(this.getClass()
				.getName());
	 private static final int MAX_AVAILABLE = 15;  
	 private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
	 private ExecutorService exec = Executors.newCachedThreadPool();
	 
	 private JlxServiceImp jlxService;

	 public void setJlxService(JlxServiceImp jlxService) {
		this.jlxService = jlxService;
	}
	 
	/**
	 * 运营分析数据获取，任务。
	* @Title: runTask 
	* @Description:
	* @param
	* @return void
	* @author Lujf
	* @2016年6月14日@下午4:33:58
	* @throws
	 */
	public void runTask(){
		 while(true){
			 try {
				final CustomInfo customInfo = TaskCache.customerLst.poll();
				if(customInfo != null){	
					if(customInfo.isExpire() && customInfo.getCount() < 3){
						if(available.tryAcquire()){
							exec.submit(new Runnable() {
								@Override
								public void run() {
									try{
										String res = jlxService.accessReportData(customInfo.getName(), customInfo.getIdcard(), customInfo.getPhone());
										if(res == null){
											customInfo.setNextTime();
											TaskCache.customerLst.offer(customInfo);
										}
									}finally{
										available.release();
									}
								}
							});
						}else{
							TaskCache.customerLst.offer(customInfo);
						}
					}else if(customInfo.getCount() < 3){
						TaskCache.customerLst.offer(customInfo);
					}
				}
				Thread.sleep(3000);
			} catch (Exception e) {
				
			}
		 }
		 
	 }
}
