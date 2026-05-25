package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class MasterAPIRequestTest {
	
	
	@Test
	public void masterApiRequest() throws IOException {
		
		Header header=new Header("Authorization", AuthTokenProvider.getAuthToken(FD));
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.header(header)
		.contentType(ContentType.JSON)
		.log().all()
		.when()
		.post("/master")
		.then()
		.statusCode(200)
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.time(lessThan(1000L))
		.body("data", hasKey("mst_oem"))
		.body("data", hasKey("mst_model"))
		.body("$", hasKey("message"))
		.body("$", hasKey("data"))
		.body("data.mst_oem.size()", equalTo(2))
		.body("data.mst_model.size()", greaterThan(0))
		.body("data.mst_oem.id", everyItem(notNullValue(null)))
		.body("data.mst_oem.name", everyItem(notNullValue(null)))
		.body(matchesJsonSchemaInClasspath("responseSchema/masterApiResponseSchema.json"));
		
	}
	
	
	public void invalidTokenMasterApi() throws IOException {
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.header(new Header("Authorization", ""))
		.contentType(ContentType.JSON)
		.log().all()
		.when()
		.post("/master")
		.then()
		.statusCode(401);
	}

}
