package com.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo2 {

	private static  Logger logger=LogManager.getLogger(Demo2.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		logger.info("Inside the main method");
		int a=10;
		logger.info("value of a {}",a);
		int b=0;
		if(b==0) {
			logger.warn("value of b {}",b);
		}else {
			logger.info("value of b {}",b);
		}
		
		try {
			int result=a/b;
			logger.info("value of result {}",result);
		}catch (ArithmeticException e) {
			logger.error("Operation cannot happen",e);
		}
		
	}

}
