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
import com.api.utils.DateTimeUtil;
import com.github.javafaker.Faker;

import static com.api.utils.SpecUtil.*;

public class CreateJobApiTest2 {

	private CreateJobPayload createJobPayload;
	private static final String COUNTRY="India";
	@BeforeMethod(description = "Creating createjob api request payload")
	public void setup() {
		Faker faker=new Faker(new Locale("en-IND"));
		
		String fname=faker.name().firstName();
		String lname=faker.name().lastName();
		String phoneNumber=faker.numerify("##########");
		String altPhoneNumber=faker.numerify("##########");
		String emailAddress=faker.internet().emailAddress();
		String altEmailAddress=faker.internet().emailAddress();
		
		
		Customer customer=new Customer(fname, lname, phoneNumber, altPhoneNumber, emailAddress, altEmailAddress);
		System.out.println(customer);
		
		String flatNumber=faker.numerify("###");
		String apartmentName=faker.address().streetName();
		String streetName=faker.address().streetName();
		String landmark=faker.address().streetName();
		String area=faker.address().streetName();
		String pincode=faker.numerify("#####");
		String state=faker.address().state();
		CustomerAddress customerAddress=new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area, pincode, COUNTRY, apartmentName);
		
		System.out.println(customerAddress);
		
		//customer product details
		String dop=DateTimeUtil.getTimeWithDaysAgo(10);
		String imeiSerialNumber=faker.numerify("###############");
		String popurl=faker.internet().url();
		
		CustomerProduct customerProduct =new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber, popurl, 1, 1);
		System.out.println(customerProduct);
		
		Random random=new Random();
		int id=random.nextInt(27)+1;
		String remark=faker.lorem().sentence(5);
		Problems problems=new Problems(id, remark);
		
		List<Problems> problemList=new ArrayList<>();
		problemList.add(problems);
		
		createJobPayload=new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
	}

	@Test(description = "Verify if create job api ia able to create inwarrenty job", groups = { "api", "regression",
			"smoke" })
	public void createJobApiTest() throws IOException {
		         given().spec(requestSpecWithAuth(Role.FD, createJobPayload)).when().post("/job/create").then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("responseSchema/createJobApiResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));

	}

}
