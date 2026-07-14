package com.listener;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.database.DatabaseManager;

public class ApiTestListener implements ITestListener{

	private static final Logger logger=LogManager.getLogger(DatabaseManager.class);
	public void onTestStart(ITestResult result) {
		logger.info("*****************************************************************");
		logger.info("=======Staring the test {}=====",result.getName());
		logger.info("=======Test class name {}======= ",result.getMethod().getTestClass());
		logger.info("=======Description {} =======",result.getMethod().getDescription());
		logger.info("=======Groups {}======= ",Arrays.toString(result.getMethod().getGroups()));
		logger.info("*****************************************************************");
	}
	
	
	public void onTestSuccess(ITestResult result) {
		long startTime=result.getStartMillis();
		long endTime=result.getEndMillis();
		
		logger.info("Total Duration of the test {}",(endTime-startTime));
		logger.info("{}- Test passed",result.getName());
	}
	
	
	public void onTestFailure(ITestResult result) {
		logger.error("{}-- Test failed",result.getName());
		logger.error("Error message",result.getThrowable().getMessage());
		logger.error(result.getThrowable());
	}


	
	public void onTestSkipped(ITestResult result) {
		logger.info("{}-- Test skipped",result.getName());
		logger.error(result.getThrowable());
	}


	
	public void onStart(ITestContext context) {
		logger.info("**************Starting the Phoenix framework*************");
	}


	 
	public void onFinish(ITestContext context) {
		logger.info("**************Finished the Phoenix framework*************");
	}
	
	
	
	

}
