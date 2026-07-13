package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.OEM;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.Role;
import com.api.constants.ServiceLocation;
import com.api.constants.Warrenty;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.services.JobService;
import com.api.utils.DateTimeUtil;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDAO;
import com.database.dao.CustomerDAO;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.github.javafaker.Faker;

import static com.api.utils.SpecUtil.*;

public class CreateJobApiTestWithFakeData {

	private CreateJobPayload createJobPayload;
	private static final String COUNTRY="India";
	private JobService jobService;
	@BeforeMethod(description = "Creating createjob api request payload and instantiating the job service")
	public void setup() {
		
		createJobPayload=FakerDataGenerator.generateFakeCreateJobData();
		jobService = new JobService();
	}

	@Test(description = "Verify if create job api ia able to create inwarrenty job", groups = { "api", "regression",
			"smoke" })
	public void createJobApiTest() throws IOException {
		    int customerId= jobService.master(Role.FD, createJobPayload).then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("responseSchema/createJobApiResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_")).extract().body().jsonPath().getInt("data.tr_customer_id");
		    
		    CustomerDBModel customerDBModel=CustomerDAO.getCustomerInfo(customerId);

			System.out.println(customerDBModel);
			Assert.assertEquals(customerDBModel.getFirst_name(), createJobPayload.customer().first_name());
			Assert.assertEquals(customerDBModel.getLast_name(), createJobPayload.customer().last_name());
			Assert.assertEquals(customerDBModel.getMobile_number(), createJobPayload.customer().mobile_number());
			Assert.assertEquals(customerDBModel.getMobile_number_alt(), createJobPayload.customer().mobile_number_alt());
			Assert.assertEquals(customerDBModel.getEmail_id(), createJobPayload.customer().email_id());
			Assert.assertEquals(customerDBModel.getEmail_id_alt(), createJobPayload.customer().email_id_alt());
			
			int customerAddressId=customerDBModel.getTr_customer_address_id();
			System.out.println(customerAddressId);
			
			
			CustomerAddressDBModel customerAddressDBModel=CustomerAddressDAO.getCustomerAddress(customerAddressId);
			System.out.println(customerAddressDBModel);
			
			Assert.assertEquals(customerAddressDBModel.getFlat_number(), createJobPayload.customer_address().flat_number());
			Assert.assertEquals(customerAddressDBModel.getApartment_name(), createJobPayload.customer_address().apartment_name());
			Assert.assertEquals(customerAddressDBModel.getStreet_name(), createJobPayload.customer_address().street_name());
			Assert.assertEquals(customerAddressDBModel.getLandmark(), createJobPayload.customer_address().landmark());
			Assert.assertEquals(customerAddressDBModel.getArea(), createJobPayload.customer_address().area());
			Assert.assertEquals(customerAddressDBModel.getPincode(), createJobPayload.customer_address().pincode());
			Assert.assertEquals(customerAddressDBModel.getState(), createJobPayload.customer_address().state());
			Assert.assertEquals(customerAddressDBModel.getCountry(), createJobPayload.customer_address().country());

	}

}
