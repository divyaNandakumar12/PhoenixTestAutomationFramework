package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {
	private static Properties prop=new Properties();
	private static String path="config/config.properties";
	private static String env;
	private static final Logger logger=LogManager.getLogger(ConfigManager.class);
	
	private ConfigManager() {
		
	}
	
	static {
		logger.info("Reading env value passed from terminal");
		if(System.getProperty("env")==null) {
			logger.warn("Env variable is not set....using qa as the env");
		}
		env=System.getProperty("env","qa");
		logger.info("Running the tests in the env {}",env);
		env=env.toLowerCase().trim();
		
		switch(env) {
		case "dev"->path="config/config.dev.properties";
			
		case "qa" ->path="config/config.qa.properties";
			
		case "uat" ->path="config/config.uat.properties";
			
		default -> path="config/config.qa.properties";
		}
		
		logger.info("Using the properties file from the path {}",path);
		InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		
		if(inputStream==null) {
			logger.error("Cannot find config file in the path",path);
			throw new RuntimeException("Cannot find config file in the path "+path);
		}
		try {
			
			prop.load(inputStream);
		} catch (IOException e) {
			logger.error("Something went wrong ....please check the file {}",path);
			e.printStackTrace();
		}
		
	}
	
	
	public static String getProperty(String key){
		return prop.getProperty(key);
		
	}

}
