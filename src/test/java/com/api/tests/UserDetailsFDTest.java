package com.api.tests;

import static com.api.constants.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.api.services.UserDetailsService;
import com.api.utils.SpecUtil;

public class UserDetailsFDTest {
	
	private UserDetailsService userDetailsService;
	@BeforeMethod(description = "Initializing the userdetails service")
	public void setup() {
		userDetailsService=new UserDetailsService();
	}
	
	@Test(description = "Verify if the user details API response is shown correctly",groups = {"api","smoke","regression"})
	public void userDetailsAPI() throws IOException {
	
		userDetailsService.userDetails(FD)
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message", equalTo("Success"))
		.body("data.id", equalTo(4))
		.body(matchesJsonSchemaInClasspath("responseSchema/userDetailsResponseSchema.json"));
				
	}

}
