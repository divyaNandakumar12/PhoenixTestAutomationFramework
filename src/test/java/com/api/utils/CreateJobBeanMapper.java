package com.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.dataproviders.api.bean.CreateJobBean;

import io.qameta.allure.Step;

public class CreateJobBeanMapper {
	
	private static final Logger logger=LogManager.getLogger(CreateJobBeanMapper.class);
	private CreateJobBeanMapper() {
		
	}
	
	@Step("Converting the CreateJobBean to the CreateJob Payload for CreateJobAPI Test")
	public static CreateJobPayload mapper(CreateJobBean createJobBean) {
		logger.info("Converting the create job bean {} to CreateJobPayload",createJobBean);
		int mstServiceLocationId=Integer.parseInt(createJobBean.getMst_service_location_id());
		int mstPlatformId=Integer.parseInt(createJobBean.getMst_platform_id());
		int mstWarrentyStatusId=Integer.parseInt(createJobBean.getMst_warrenty_status_id());
		int mstOemId=Integer.parseInt(createJobBean.getMst_oem_id());
		String firstName=createJobBean.getCustomer__first_name();
		String lastName=createJobBean.getCustomer__last_name();
		String mobileNumber=createJobBean.getCustomer__mobile_number();
		String mobileNumberAlt=createJobBean.getCustomer__mobile_number_alt();
		String email=createJobBean.getCustomer__email_id();
		String emailAlt=createJobBean.getCustomer__email_id_alt();
		String flatNumber=createJobBean.getCustomer_address__flat_number();
		String apartment_name=createJobBean.getCustomer_address__apartment_name();
		String street_name=createJobBean.getCustomer_address__street_name();
		String landmark=createJobBean.getCustomer_address__landmark();
		String area=createJobBean.getCustomer_address__area();
		String pincode=createJobBean.getCustomer_address__pincode();
		String country=createJobBean.getCustomer_address__country();
		String state=createJobBean.getCustomer_address__state();
		String dop=createJobBean.getCustomer_product__dop();
		String serial_number=createJobBean.getCustomer_product__serial_number();
		String imei1=createJobBean.getCustomer_product__imei1();
		String imei2=createJobBean.getCustomer_product__imei2();
		String popurl=createJobBean.getCustomer_product__popurl();
		int product_id=Integer.parseInt(createJobBean.getCustomer_product__product_id());
		int mst_model_id=Integer.parseInt(createJobBean.getCustomer_product__mst_model_id());
		
		List<Problems> problems=new ArrayList<Problems>();
		Problems problems2=new Problems(Integer.parseInt(createJobBean.getProblems__id()), createJobBean.getProblems__remark());
		problems.add(problems2);
		
		
		Customer customer=new Customer(firstName, lastName, mobileNumber, mobileNumberAlt, email, emailAlt);
		
		CustomerAddress customerAddress=new CustomerAddress(flatNumber, apartment_name, street_name, landmark, area, pincode, country, state);
		
		CustomerProduct customerProduct=new CustomerProduct(dop, serial_number, imei1, imei2, popurl, product_id, mst_model_id);
		
		CreateJobPayload createJobPayload=new CreateJobPayload(mstServiceLocationId, mstPlatformId, mstWarrentyStatusId, mstOemId, customer, customerAddress, customerProduct, problems);
		logger.info("Converted the create job bean {} to CreateJobPayload",createJobPayload);
		return createJobPayload;
	}

}
