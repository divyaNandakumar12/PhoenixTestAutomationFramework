package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;

import io.restassured.response.Response;

public class JobService {
	
	private static final String CREATEJOB_ENDPOINT = "/job/create";

	public Response master(Role role,CreateJobPayload createJobPayload) {
		Response response=null;
		try {
			response=given().spec(requestSpecWithAuth(role, createJobPayload)).when().post(CREATEJOB_ENDPOINT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}
