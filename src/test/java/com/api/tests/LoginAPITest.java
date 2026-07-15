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

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;

@Listeners(com.listener.ApiTestListener.class)
@Epic("User Management")
@Feature("Authentication")
public class LoginAPITest {
	
	private UserBean userBean;
	private AuthService authService;
	
	@BeforeMethod(description = "create the request payload for login API")
	public void setup() {
		userBean=new UserBean("iamfd", "password");
		authService=new AuthService();
	}
	
	@Story("Valid user should be able to login into the system")
	@Description("Verify if FD user is able to login via api")
	@Severity(SeverityLevel.BLOCKER)
	@Test(description = "verifying if login api is working for FD user",groups = {"api","regression","smoke"},
	retryAnalyzer = com.api.retry.RetryAnalyzer.class)
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
