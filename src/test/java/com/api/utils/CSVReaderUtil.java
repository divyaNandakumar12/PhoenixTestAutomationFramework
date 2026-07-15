package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.services.JobService;
import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import io.qameta.allure.Step;

public class CSVReaderUtil {
	
	private static final Logger logger=LogManager.getLogger(CSVReaderUtil.class);
	private CSVReaderUtil() {
		
	}

	@Step("Loading test data from CSV")
	public static <T> Iterator<T> loadCSV(String pathOfCSVFile,Class<T> beanClass) {
		logger.info("loading the CSV file from the path {}", pathOfCSVFile);
		InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
		
		CSVReader csvReader=new CSVReader(inputStreamReader);
		logger.info("converting the CSV to the Bean class", beanClass);
		CsvToBean<UserBean> csvToBean=new CsvToBeanBuilder(csvReader)
				.withType(beanClass)
				.withIgnoreEmptyLine(true)
				.build();
		
		List<T> list=(List<T>) csvToBean.parse();
		return list.iterator();
	}
}
