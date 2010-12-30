package com.yuchting.yuchberry.server.frame;

import java.util.Date;

import com.yuchting.yuchberry.server.Logger;
import com.yuchting.yuchberry.server.fetchMgr;

public class fetchThread extends Thread{
	
	fetchMgr 	m_fetchMgr = new fetchMgr();
	Logger 		m_logger = null;
	
	boolean	m_pauseState = false;
	boolean	m_close		= false;
	
	boolean	m_sendTimeupMail = false;
		
	long		m_expiredTime	= 0;
	long		m_formerTimer	= 0;
		
	public fetchThread(String _prefix,String _configFile,long _expiredTime,long _formerTimer,boolean _testConnect)throws Exception{
		m_expiredTime = _expiredTime * 1000 * 3600;
		
		m_logger = new Logger(_prefix);
		m_fetchMgr.InitConnect(_prefix, _configFile, m_logger);
		
		if(_testConnect){
			m_fetchMgr.ResetSession(true);
		}
		
		m_formerTimer = _formerTimer;
		
		start();
	}
	
	public void run(){
				
		while(!m_close){
			
			try{
				while(m_pauseState){
					sleep(2000);
				}
				
				m_fetchMgr.StartListening();
				
			}catch(Exception e){
				m_logger.PrinterException(e);
			}
			
			try{
				sleep(60000);
			}catch(Exception ex){}
		}
	}
	
	
	public long GetLastTime(){
		if(m_expiredTime > 0){
			return m_expiredTime - ((new Date()).getTime() - m_formerTimer);
		}
		
		return 0;
	}
	
	public void Pause(){
		if(m_close == true){
			return;
		}
		
		if(m_pauseState == false){
			m_fetchMgr.EndListening();
			m_pauseState = true;
			
			try{
				sleep(100);
			}catch(Exception e){}
			
			interrupt();
		}
	}
	
	public void Reuse(){
		
		if(m_close == true){
			return;
		}
		
		if(m_pauseState = true){
			
			m_sendTimeupMail = false;
			m_pauseState = false;
			
			try{
				sleep(100);
			}catch(Exception e){}
			
			if(GetLastTime() < 0){
				m_formerTimer = (new Date()).getTime();
			}			
			
			interrupt();			
		}
	}
	
	public synchronized void Destroy(){
		
		m_fetchMgr.EndListening();
		m_logger.StopLogging();
		
		try{
			sleep(100);
		}catch(Exception e){}
		
		m_close = true;
		interrupt();
	}
}