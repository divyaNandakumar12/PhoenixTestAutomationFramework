package com.api.services;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.constants.Role;
import com.api.utils.SpecUtil;

import io.restassured.response.Response;

public class MasterService {
	
	private static final String MASTER_ENDPOINT = "/master";

	public Response master(Role role) {
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
	
	
	public Response master() {
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
