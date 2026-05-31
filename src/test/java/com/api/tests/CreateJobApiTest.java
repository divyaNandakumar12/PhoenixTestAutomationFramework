package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.utils.DateTimeUtil.*;
import com.api.utils.SpecUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobApiTest {

	@Test
	public void createJobApiTest() throws IOException {
		
		Customer customer=new Customer("Rowland", "Wunsch", "758-252-7805", "", "Libbie_Frami@yahoo.com", "");
		CustomerAddress customerAddress=new CustomerAddress("1229", "park villa", "15th street", "axis bank kolathur", "poompuhar nagar", "600099", "India", "Tamil Nadu");
		CustomerProduct customerProduct=new CustomerProduct(getTimeWithDaysAgo(10), "90238575534467", "90238575534467", "90238575534467", getTimeWithDaysAgo(10), 1, 1);
		Problems problems=new Problems(1, "phone is running slow");
		List<Problems> pr=new ArrayList<Problems>();
		pr.add(problems);
		CreateJobPayload createJobPayload=new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, pr);
		
		given().spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("responseSchema/createJobApiResponseSchema.json"))
		.body("message", equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", equalTo(1))
		.body("data.job_number", startsWith("JOB_"));
		
	}

}
