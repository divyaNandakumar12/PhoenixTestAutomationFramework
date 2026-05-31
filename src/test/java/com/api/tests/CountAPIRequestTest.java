package com.api.tests;

import static com.api.constants.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

public class CountAPIRequestTest {

	@Test(description = "Verify if the count API is giving correct response", groups = { "api", "smoke", "regression" })
	public void countAPITest() throws IOException {

		given().spec(requestSpecWithAuth(FD)).when().get("/dashboard/count").then().spec(responseSpec_OK())
				.body("message", equalTo("Success")).body("data", notNullValue()).body("data.size()", equalTo(3))
				.body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", everyItem(not(blankOrNullString())))
				.body(matchesJsonSchemaInClasspath("responseSchema/countApiResponseSchema.json"));

	}

	@Test(description = "Verify if the count API is giving correct status code for invalid token", groups = { "api",
			"negative", "smoke", "regression" })
	public void invalidHeaderTest() throws IOException {

		given().spec(requestSpec()).when().get("/dashboard/count").then().spec(responseSpecWithStatusCode_TEXT(401));

	}

}
