package com.api.utils;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.api.constants.Role.*;

import com.api.constants.Role;
import com.api.request.model.UserCredentials;
import com.api.services.JobService;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	private static Map<Role, String> tokenCache=new ConcurrentHashMap<Role, String>();
	private static final Logger logger=LogManager.getLogger(AuthTokenProvider.class);

	public static String getAuthToken(Role role) throws IOException {
		logger.info("checking if the token for the {} is present in the cache",role);
		if(tokenCache.containsKey(role)) {
			return tokenCache.get(role);
		}
		logger.info("token no found making the login request for the role {}",role);
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

		logger.info("token cached for the future request");
		tokenCache.put(role, token);
		return token;
	}

}
