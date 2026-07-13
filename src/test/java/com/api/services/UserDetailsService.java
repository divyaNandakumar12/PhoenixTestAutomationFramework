package com.api.services;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.constants.Role;
import com.api.utils.SpecUtil;

import io.restassured.response.Response;

public class UserDetailsService {
	
	private static final String USERDETAILS_ENDPOINT = "userdetails";

	public Response userDetails(Role role) {
		Response response=null;
		try {
			response = given()
			.spec(SpecUtil.requestSpecWithAuth(role))
			.when()
			.get(USERDETAILS_ENDPOINT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}
