package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobApiTest {

	@Test
	public void createJobApiTest() throws IOException {
		
		Customer customer=new Customer("Rowland", "Wunsch", "758-252-7805", "", "Libbie_Frami@yahoo.com", "");
		CustomerAddress customerAddress=new CustomerAddress("1229", "park villa", "15th street", "axis bank kolathur", "poompuhar nagar", "600099", "India", "Tamil Nadu");
		CustomerProduct customerProduct=new CustomerProduct("2026-04-08T12:00:00.000Z", "99238575534467", "99238575534467", "99238575534467", "2026-04-08T12:00:00.000Z", 1, 1);
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
