package com.api.tests.dataDriven;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.utils.SpecUtil;
import com.dataproviders.api.bean.UserBean;

import static com.api.utils.SpecUtil.*;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;

public class LoginAPIJsonDataDrivenTest {
	
	
	@Test(description = "verifying if login api is working for FD user",groups = {"api","regression","datadriven"},
	dataProviderClass  = com.dataproviders.DataProviderUtil.class,
	dataProvider = "LoginAPIJsonDataProvider")
	public void loginAPITest(UserCredentials userCredentials ){	
		given()
		.spec(requestSpec(userCredentials))
		.when()
		.post("login")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message", equalTo("Success"))
		.and()
		.body("data.token", notNullValue())
		.and()
		.body(matchesJsonSchemaInClasspath("responseSchema/loginResponseSchema.json"));
	}

}
