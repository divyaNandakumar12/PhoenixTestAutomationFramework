package com.api.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AllureEnvironmentWriterUtility {
	private static final Logger logger=LogManager.getLogger(AllureEnvironmentWriterUtility.class);
	public static void createEnvironmentPropertiesFile()  {
		// TODO Auto-generated method stub

		String folderPath="target/allure-results";
		File file=new File(folderPath);
		file.mkdirs();
		Properties properties=new Properties();
		properties.setProperty("Name", "Divya");
		properties.setProperty("Project_Name", "Phoenix test automation framework");
		properties.setProperty("Env", ConfigManager.env);
		properties.setProperty("BASE_URI", ConfigManager.getProperty("BASE_URI"));
		properties.setProperty("Operating system", System.getProperty("os.name"));
		properties.setProperty("Operating system Version", System.getProperty("os.version"));
		properties.setProperty("Java version", System.getProperty("java.version"));
	
		FileWriter fWriter;
		try {
			fWriter = new FileWriter(folderPath+"/environment.properties");
			properties.store(fWriter, "my properties file");
			logger.info("Created enviroment.properties file at {}",folderPath);
		} catch (IOException e) {
			logger.error("Unable to create the environment.properties file",e);
			e.printStackTrace();
		}
	}

}
