package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import io.restassured.response.Response;

public class JobService {
	
	private static final String CREATEJOB_ENDPOINT = "/job/create";
	private static final String SEARCH_ENDPOINT="/job/search";
	private static final Logger logger=LogManager.getLogger(JobService.class);

	public Response master(Role role,CreateJobPayload createJobPayload) {
		logger.info("making request to {} with the role {} and the payload ()", CREATEJOB_ENDPOINT,role,createJobPayload);
		Response response=null;
		try {
			response=given().spec(requestSpecWithAuth(role, createJobPayload)).when().post(CREATEJOB_ENDPOINT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public Response search(Role role,Object payload) {
		logger.info("making request to {} with the role {} and the payload ()", SEARCH_ENDPOINT,role,payload);
		Response response=null;
		try {
			response=given().spec(requestSpecWithAuth(role)).body(payload).
					when().post(SEARCH_ENDPOINT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	

}
