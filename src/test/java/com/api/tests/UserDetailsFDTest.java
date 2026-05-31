package com.api.tests;

import static com.api.constants.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

public class UserDetailsFDTest {
	
	@Test(description = "Verify if the user details API response is shown correctly",groups = {"api","smoke","regression"})
	public void userDetailsAPI() throws IOException {
	
		given()
		.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
		.get("userdetails")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message", equalTo("Success"))
		.body("data.id", equalTo(4))
		.body(matchesJsonSchemaInClasspath("responseSchema/userDetailsResponseSchema.json"));
				
	}

}
