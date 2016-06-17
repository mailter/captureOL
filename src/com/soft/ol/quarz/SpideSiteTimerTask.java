package com.soft.ol.quarz;

import java.util.TimerTask;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.soft.jxl.service.JlxServiceImp;
import com.soft.ol.service.AuthenticateService;

public class SpideSiteTimerTask extends TimerTask { 

  private static Logger log = (Logger) LogManager.getLogger(SpideSiteTimerTask.class);
  private AuthenticateService authenticService;

  public void setAuthenticService(AuthenticateService authenticService) {
	this.authenticService = authenticService;
  }

@Override 
  public void run() { 
	  try { 
		  log.info("-----------executeAuthenticateService-----------------------"); 
		  System.out.println("====job===start==");
		  authenticService.executeAuthenticateResult("","","01","","","","","");
		  System.out.println("====job===end==");
		  log.info("---------2-------executeAuthenticateService-------------------"); 
	  } catch (Exception e) { 
		  e.printStackTrace(); 
	  } 
  } 

} 

  
