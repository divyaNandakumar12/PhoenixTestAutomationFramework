package com.api.tests;

import static com.api.constants.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.utils.AuthTokenProvider;
import com.api.utils.SpecUtil;

import io.restassured.http.Header;

public class MasterAPIRequestTest {
	
	
	@Test(description = "Verify if the master API is giving correct response",groups = {"api","smoke","regression"})
	public void masterApiRequest() throws IOException {
		
		Header header=new Header("Authorization", AuthTokenProvider.getAuthToken(FD));
		given()
		.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
		.post("/master")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.time(lessThan(1000L))
		.body("data", hasKey("mst_oem"))
		.body("data", hasKey("mst_model"))
		.body("$", hasKey("message"))
		.body("$", hasKey("data"))
		.body("data.mst_oem.size()", equalTo(2))
		.body("data.mst_model.size()", greaterThan(0))
		.body("data.mst_oem.id", everyItem(notNullValue(null)))
		.body("data.mst_oem.name", everyItem(notNullValue(null)))
		.body(matchesJsonSchemaInClasspath("responseSchema/masterApiResponseSchema.json"));
		
	}
	
	
	@Test(description = "Verify if the master API is giving correct status code for invalid token",groups = {"api","negative","smoke","regression"})
	public void invalidTokenMasterApi() throws IOException {
		given()
		.spec(SpecUtil.requestSpec())
		.when()
		.post("/master")
		.then()
		.spec(SpecUtil.responseSpecWithStatusCode_TEXT(401));
	}

}
