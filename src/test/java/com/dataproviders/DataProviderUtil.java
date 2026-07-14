package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.database.DatabaseManager;
import com.database.dao.CreateJobPayloadDataDAO;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtil {

	private static final Logger logger=LogManager.getLogger(DataProviderUtil.class);
	@DataProvider(name = "LoginAPIDataProvider", parallel = true)
	public static Iterator<UserBean> loginAPIDataProvider() {
		logger.info("loading data from the CSV file testData/LoginCreds.csv");
		return CSVReaderUtil.loadCSV("testData/LoginCreds.csv", UserBean.class);
	}

	@DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobDataProvider() {
		logger.info("loading data from the CSV file testData/CreateJobData.csv");
		Iterator<CreateJobBean> iterator = CSVReaderUtil.loadCSV("testData/CreateJobData.csv", CreateJobBean.class);
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (iterator.hasNext()) {
			tempBean = iterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			payloadList.add(tempPayload);
		}

		return payloadList.iterator();
	}

	@DataProvider(name = "createJobAPIFakerDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobAPIFakerDataProvider() {
		String countString = System.getProperty("fakerCount", "5");
		int fakerCountInt = Integer.parseInt(countString);
		logger.info("Generating fake create job data with the faker count {}",fakerCountInt);
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(fakerCountInt);
		return payloadIterator;
	}
	
	@DataProvider(name = "LoginAPIJsonDataProvider", parallel = true)
	public static Iterator<UserBean> LoginAPIJsonDataProvider() {
		logger.info("loading data from the JSON file testData/loginAPITestData.json");
		return JsonReaderUtil.loadJson("testData/loginAPITestData.json", UserBean[].class);
	}
	
	@DataProvider(name = "CreateJobAPIJsonDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIJsonDataProvider() {
		logger.info("loading data from the JSON file testData/CreateJobAPIData.json");
		return JsonReaderUtil.loadJson("testData/CreateJobAPIData.json", CreateJobPayload[].class);
	}
	
	@DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
	public static Iterator<UserBean> LoginAPIExcelDataProvider() {
		logger.info("loading data from the excel file testData/PhoenixTestData.xlsx and sheet is LoginTestData");
		return ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx","LoginTestData",UserBean.class);
	}
	
	@DataProvider(name = "CreateJobAPIExcelDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIExcelDataProvider() {
		logger.info("loading data from the excel file testData/PhoenixTestData.xlsx and sheet is CreateJobTestData");
		Iterator<CreateJobBean> iterator = ExcelReaderUtil.loadTestData("testData/PhoenixTestData.xlsx","CreateJobTestData",CreateJobBean.class );
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (iterator.hasNext()) {
			tempBean = iterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			payloadList.add(tempPayload);
		}

		return payloadList.iterator();
	}

	
	@DataProvider(name = "CreateJobAPIDBDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> CreateJobAPIDBDataProvider() {
		logger.info("loading data from the database for CreateJobPayload");
		List<CreateJobBean> beanList=CreateJobPayloadDataDAO.getCreateJobPayloadData();
		 List<CreateJobPayload> payloadList=new ArrayList<CreateJobPayload>();
		 
		 for(CreateJobBean createJobBean:beanList) {
			 CreateJobPayload createJobPayload=CreateJobBeanMapper.mapper(createJobBean);
			 payloadList.add(createJobPayload);
		 }

		return payloadList.iterator();
	}

}
