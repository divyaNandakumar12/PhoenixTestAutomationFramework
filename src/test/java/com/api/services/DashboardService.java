package com.api.services;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;
import com.api.utils.SpecUtil;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class DashboardService {
	
	private static final String COUNT_ENDPOINT = "/dashboard/count";
	private static final String DETAILS_ENDPOINT = "/dashboard/details";
	private static final Logger logger=LogManager.getLogger(DashboardService.class);

	@Step("Making Count API request for the role")
	public Response count(Role role) {
		logger.info("Making request to the {} for the role {}",COUNT_ENDPOINT,role);
		Response response=null;
		try {
			response=given().spec(requestSpecWithAuth(role)).when().get(COUNT_ENDPOINT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Step("Making Count API request without auth token")
	public Response count() {
		logger.info("Making request to the {} with no auth",COUNT_ENDPOINT);
		Response response=null;
		try {
			response=given().spec(requestSpec()).when().get(COUNT_ENDPOINT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Step("Making Details API request")
	public Response details(Role role,Object payload) {
		logger.info("Making request to the {} with role {} and the payload {}",DETAILS_ENDPOINT,role,payload);
		Response response=null;
		try {
			response=given().spec(requestSpecWithAuth(role)).body(payload)
					.when().post(DETAILS_ENDPOINT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}
