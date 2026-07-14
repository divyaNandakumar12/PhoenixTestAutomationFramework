package com.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;
import com.api.utils.SpecUtil;
import com.dataproviders.api.bean.UserBean;

import static com.api.utils.SpecUtil.*;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;

@Listeners(com.listener.ApiTestListener.class)
public class LoginAPITest {
	
	private UserBean userBean;
	private AuthService authService;
	
	@BeforeMethod(description = "create the request payload for login API")
	public void setup() {
		userBean=new UserBean("iamfd", "password");
		authService=new AuthService();
	}
	
	@Test(description = "verifying if login api is working for FD user",groups = {"api","regression","smoke"})
	public void loginAPITest() throws IOException {
		
		authService.login(userBean)
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message", equalTo("Success"))
		.and()
		.body("data.token", notNullValue())
		.and()
		.body(matchesJsonSchemaInClasspath("responseSchema/loginResponseSchema.json"));
	}

}
