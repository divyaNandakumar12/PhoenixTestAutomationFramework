package com.api.retry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.api.services.AuthService;

public class RetryAnalyzer implements IRetryAnalyzer{

	private static final Logger logger=LogManager.getLogger(RetryAnalyzer.class);
	private static final int MAX_ATTEMPTS=2;
	private int count=1;
	@Override
	public boolean retry(ITestResult result) {
		logger.info("checking if the test {} can be re-executed",result.getName());
		if(count<=MAX_ATTEMPTS) {
			logger.warn("Executing the {} test, Current attempt number {}/ {}",result.getName(),count,MAX_ATTEMPTS);
			count++;
			return true;
		}
		
		return false;
	}
	
	

}
