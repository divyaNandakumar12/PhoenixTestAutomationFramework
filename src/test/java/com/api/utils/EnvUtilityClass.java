package com.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.services.JobService;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtilityClass {
	
	public static Dotenv enDotenv;
	private static final Logger logger=LogManager.getLogger(EnvUtilityClass.class);
	
	private EnvUtilityClass() {
		
	}
	
	static {
		logger.info("Loading the .env file...");
		enDotenv=Dotenv.load();
	}
	
	public static String getValue(String key) {
		logger.info("Reading the value of {} from .env",key);
		return enDotenv.get(key);
	}

}
