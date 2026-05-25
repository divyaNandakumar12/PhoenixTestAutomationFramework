package com.api.tests;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

import static com.api.constants.Role.*;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CountAPIRequestTest {
	
	@Test
	public void countAPITest() throws IOException {
		
		Header header=new Header("Authorization", AuthTokenProvider.getAuthToken(FD));
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.contentType(ContentType.JSON)
		.header(header)
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(200)
		.time(lessThan(1000L))
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.body("data.size()", equalTo(3))
		.body("data.count", everyItem(greaterThanOrEqualTo(0)))
		.body("data.label", everyItem(not(blankOrNullString())))
		.body(matchesJsonSchemaInClasspath("responseSchema/countApiResponseSchema.json"));
		
	}
	
	@Test
	public void invalidHeaderTest() throws IOException {
		
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.contentType(ContentType.JSON)
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
		
	}

}
