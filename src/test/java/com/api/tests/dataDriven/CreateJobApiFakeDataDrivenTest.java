package com.api.tests.dataDriven;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;

public class CreateJobApiFakeDataDrivenTest {

	private CreateJobPayload createJobPayload;

	@Test(description = "Verify if create job api ia able to create inwarrenty job", groups = { "api", "regression",
			"datadriven","faker" },
			dataProviderClass  = com.dataproviders.DataProviderUtil.class,
			dataProvider = "createJobAPIFakerDataProvider")
	public void createJobApiTest(CreateJobPayload createJobPayload) throws IOException {
		         given().spec(requestSpecWithAuth(Role.FD, createJobPayload)).when().post("/job/create").then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("responseSchema/createJobApiResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));

	}

}
