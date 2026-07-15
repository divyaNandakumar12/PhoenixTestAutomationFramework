package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.UserCredentials;
import com.api.services.JobService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.qameta.allure.Step;

public class JsonReaderUtil {

	private static final Logger logger=LogManager.getLogger(JsonReaderUtil.class);
	@Step("Loading test data from json")
	public static <T> Iterator<T> loadJson(String filePath,Class<T[]> clazz){
        logger.info("Reading the JSON file from the file {}",filePath);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		ObjectMapper objectMapper=new ObjectMapper();
		T[] classArray;
		List<T> list=null;
		try {
			logger.info("Converting the JSON data to the bean class",clazz);
			classArray = objectMapper.readValue(is, clazz);
			list=Arrays.asList(classArray);
		} catch (IOException e) {
			logger.error("Cannot read the json from the file {}",filePath,e);
			e.printStackTrace();
		}
		
		return list.iterator();
	}

}
