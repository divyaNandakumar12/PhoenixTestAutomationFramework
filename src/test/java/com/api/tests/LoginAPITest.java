package com.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;

public class LoginAPITest {
	
	
	@Test
	public void loginAPITest() throws IOException {
		UserCredentials userCredentials=new UserCredentials("iamfd", "password");
		given().baseUri(getProperty("BASE_URI"))
		.contentType(ContentType.JSON)
		.and()
		.accept(ContentType.JSON)
		.and()
		.body(userCredentials)
		.log().uri()
		.log().method()
		.log().body()
		.log().headers()
		.when()
		.post("login")
		.then()
		.statusCode(200)
		.log().all()
		.time(lessThan(1500L))
		.and()
		.body("message", equalTo("Success"))
		.and()
		.body("data.token", notNullValue())
		.and()
		.body(matchesJsonSchemaInClasspath("responseSchema/loginResponseSchema.json"));
	}

}
