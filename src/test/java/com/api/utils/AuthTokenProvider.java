package com.api.utils;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import static com.api.constants.Role.*;

import com.api.constants.Role;
import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {

	public static String getAuthToken(Role role) throws IOException {

		UserCredentials userCredentials = null;
		if (role==FD) {
			userCredentials = new UserCredentials("iamfd", "password");
		} else if (role==SUP) {
			userCredentials =new UserCredentials("iamsup", "password");
		} else if (role==ENG) {
			userCredentials =new UserCredentials("iameng", "password");
		} else if (role==QC) {
			userCredentials =new UserCredentials("iamqc", "password");
		}
		String token = given().baseUri(ConfigManager.getProperty("BASE_URI")).contentType(ContentType.JSON).body(userCredentials)
				.when().post("login").then().statusCode(200).log().ifValidationFails().extract().jsonPath()
				.getString("data.token");

		return token;
	}

}
