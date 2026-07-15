package com.api.utils;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.api.utils.ConfigManager.*;

import java.io.IOException;

import org.hamcrest.Matchers;

import com.api.constants.Role;
import com.api.filters.SensitiveDataFilter;
import com.api.request.model.UserCredentials;

public class SpecUtil {

	@Step("Setting up the BASE_URI,Content type as Aplication/JSON and attaching the SensitiveData filter")
	public static RequestSpecification requestSpec() throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addFilter(new SensitiveDataFilter()).build();
		return requestSpecification;
	}

	@Step("Setting up the BASE_URI,Content type as Aplication/JSON and attaching the SensitiveData filter")
	public static RequestSpecification requestSpec(Object object) {

		RequestSpecification requestSpecification=null;
		try {
			requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
					.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(object)
					.addFilter(new SensitiveDataFilter())
					.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestSpecification;
	}
	
	@Step("Setting up the BASE_URI,Content type as Aplication/JSON and attaching the SensitiveData filter for a role")
	public static RequestSpecification requestSpecWithAuth(Role role) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getAuthToken(role)).addFilter(new SensitiveDataFilter())
				.build();
		return requestSpecification;
	}
	
	
	@Step("Setting up the BASE_URI,Content type as Aplication/JSON and attaching the SensitiveData filter for a role and attaching payload")
	public static RequestSpecification requestSpecWithAuth(Role role,Object Payload) throws IOException {

		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getAuthToken(role))
				.setBody(Payload).addFilter(new SensitiveDataFilter())
				.build();
		return requestSpecification;
	}

	@Step("Expecting the response to have content type as Application/JSON, status 200 and response time less than 1000 ms")
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).expectResponseTime(Matchers.lessThan(1500L))
				.build();
		return responseSpecification;
	}
	
	@Step("Expecting the response to have content type as Application/JSON and response time less than 1000 ms and status code")
	public static ResponseSpecification responseSpecWithStatusCode_JSON(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectContentType(ContentType.JSON).expectResponseTime(Matchers.lessThan(1500L))
				.build();
		return responseSpecification;
	}
	
	
	@Step("Expecting the response to have content type as Application/JSON and response time less than 1000 ms and status code")
	public static ResponseSpecification responseSpecWithStatusCode_TEXT(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(Matchers.lessThan(1500L))
				.build();
		return responseSpecification;
	}
}
