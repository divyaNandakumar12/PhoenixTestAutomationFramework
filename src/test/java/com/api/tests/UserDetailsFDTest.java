package com.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.utils.AuthTokenProvider;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;

public class UserDetailsFDTest {
	
	@Test
	public void userDetailsAPI() throws IOException {
		
		Header header =new Header("Authorization", AuthTokenProvider.getAuthToken(FD));
		given().baseUri(getProperty("BASE_URI"))
		.and()
		.header(header)
		.log().uri()
		.log().method()
		.log().headers()
		.accept(ContentType.JSON)
		.when()
		.get("userdetails")
		.then()
		.statusCode(200)
		.time(lessThan(1500L))
		.log().all()
		.body("message", equalTo("Success"))
		.body("data.id", equalTo(4))
		.body(matchesJsonSchemaInClasspath("responseSchema/userDetailsResponseSchema.json"));
				
	}

}
