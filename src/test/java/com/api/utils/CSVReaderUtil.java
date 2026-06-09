package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {
	
	
	private CSVReaderUtil() {
		
	}

	public static <T> Iterator<T> loadCSV(String pathOfCSVFile,Class<T> beanClass) {
		InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
		
		CSVReader csvReader=new CSVReader(inputStreamReader); 
		CsvToBean<UserBean> csvToBean=new CsvToBeanBuilder(csvReader)
				.withType(beanClass)
				.withIgnoreEmptyLine(true)
				.build();
		
		List<T> list=(List<T>) csvToBean.parse();
//		System.out.println(list);
//		System.out.println(list.get(0).getUsername());
		return list.iterator();
	}
}
