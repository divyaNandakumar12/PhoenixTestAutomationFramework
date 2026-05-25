package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import groovyjarjarantlr4.v4.codegen.model.ThrowEarlyExitException;

public class ConfigManager {
	private static Properties prop=new Properties();
	private static String path="config/config.properties";
	private static String env;
	private ConfigManager() {
		
	}
	
	static {
		
		env=System.getProperty("env","qa");
		env=env.toLowerCase().trim();
		
		switch(env) {
		case "dev"->path="config/config.dev.properties";
			
		case "qa" ->path="config/config.qa.properties";
			
		case "uat" ->path="config/config.uat.properties";
			
		default -> path="config/config.qa.properties";
		}
		
		
		InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		
		if(inputStream==null) {
			throw new RuntimeException("Cannot find config file in the path "+path);
		}
		try {
			
			prop.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static String getProperty(String key) throws IOException {
		return prop.getProperty("BASE_URI");
		
	}

}
