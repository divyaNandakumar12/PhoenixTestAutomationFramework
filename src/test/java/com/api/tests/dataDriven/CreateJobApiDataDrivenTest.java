package com.api.tests.dataDriven;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;
import com.api.services.JobService;
@Listeners(com.listener.ApiTestListener.class)
public class CreateJobApiDataDrivenTest {

	private CreateJobPayload createJobPayload;
	
	private JobService jobService;
	@BeforeMethod(description = "Initializing the job service")
	public void setup() {
		jobService = new JobService();
	}

	@Test(description = "Verify if create job api ia able to create inwarrenty job", groups = { "api", "regression",
			"datadriven","csv" },
			dataProviderClass  = com.dataproviders.DataProviderUtil.class,
			dataProvider = "CreateJobAPIDataProvider")
	public void createJobApiTest(CreateJobPayload createJobPayload) throws IOException {
		         jobService.master(Role.FD, createJobPayload).then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("responseSchema/createJobApiResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));

	}

}
