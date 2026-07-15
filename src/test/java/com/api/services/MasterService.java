package com.api.services;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;
import com.api.utils.SpecUtil;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class MasterService {
	
	private static final String MASTER_ENDPOINT = "/master";
	private static final Logger logger=LogManager.getLogger(MasterService.class);

	@Step("Making master API request")
	public Response master(Role role) {
		logger.info("making request to {} with the role {}", MASTER_ENDPOINT,role);
		Response response=null;
		try {
			response=given()
					.spec(SpecUtil.requestSpecWithAuth(role))
					.when()
					.post(MASTER_ENDPOINT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Step("Making master API request without role parameter")
	public Response master() {
		logger.info("making request to {}", MASTER_ENDPOINT);
		Response response=null;
		try {
			response=given().spec(SpecUtil.requestSpec()).when()
					.post(MASTER_ENDPOINT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}
