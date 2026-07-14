package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
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

import static com.api.utils.SpecUtil.*;
@Listeners(com.listener.ApiTestListener.class)
public class CreateJobApiTest {

	private CreateJobPayload createJobPayload;
	private JobService jobService;

	@BeforeMethod(description = "Creating createjob api request payload and instantiating the job service")
	public void setup() {
		Customer customer = new Customer("Rowland", "Wunsch", "758-252-7805", "", "Libbie_Frami@yahoo.com", "");
		CustomerAddress customerAddress = new CustomerAddress("1229", "park villa", "15th street", "axis bank kolathur",
				"poompuhar nagar", "600099", "India", "Tamil Nadu");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "92238575534460",
				"92238575534460", "92238575534460", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(),
				Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "phone is running slow");
		List<Problems> pr = new ArrayList<Problems>();
		pr.add(problems);
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warrenty.IN_WARRENTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, pr);
		jobService = new JobService();
	}

	@Test(description = "Verify if create job api ia able to create inwarrenty job", groups = { "api", "regression",
			"smoke" })
	public void createJobApiTest() throws IOException {
		         jobService.master(Role.FD, createJobPayload).then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("responseSchema/createJobApiResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));

	}

}
