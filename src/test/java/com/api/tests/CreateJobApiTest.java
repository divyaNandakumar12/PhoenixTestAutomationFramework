package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class CreateJobApiTest {

	@Test
	public void createJobApiTest() throws IOException {
		
		Customer customer=new Customer("Rowland", "Wunsch", "758-252-7805", "", "Libbie_Frami@yahoo.com", "");
		CustomerAddress customerAddress=new CustomerAddress("1229", "park villa", "15th street", "axis bank kolathur", "poompuhar nagar", "600099", "India", "Tamil Nadu");
		CustomerProduct customerProduct=new CustomerProduct("2026-04-08T12:00:00.000Z", "65238575534467", "65238575534467", "65238575534467", "2026-04-08T12:00:00.000Z", 1, 1);
		Problems problems=new Problems(1, "phone is running slow");
		Problems[] problemArray=new Problems[1];
		problemArray[0]=problems;
		CreateJobPayload createJobPayload=new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemArray);
		
		given().spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK());
		
	}

}
