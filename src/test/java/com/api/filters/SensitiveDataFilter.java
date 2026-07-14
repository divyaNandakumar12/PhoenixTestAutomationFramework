package com.api.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter{
    private static final Logger LOGGER=LogManager.getLogger(SensitiveDataFilter.class);
	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		
		System.out.println("--------------HELLO FROM THE FILTER----------------");
		reductPayload(requestSpec);
		Response response= ctx.next(requestSpec, responseSpec);
		reductResponseBody(response);
		System.out.println("--------------I got the response in filter--------------");
		return response;
	}

	public void reductPayload(FilterableRequestSpecification requestSpec) {
		String requestPayload= requestSpec.getBody().toString();
		requestPayload=requestPayload.replaceAll(
			    "\"password\"\\s*:\\s*\"[^\"]*\"",
			    "\"password\":\"[REDACTED]\""
			);
		LOGGER.info("Request payload {}",requestPayload);
	}
	
	
	public void reductResponseBody(Response response) {
		String responseString= response.asPrettyString();
		responseString=responseString.replaceAll(
			    "\"token\"\\s*:\\s*\"[^\"]*\"",
			    "\"token\":\"[REDACTED]\""
			);
		LOGGER.info("Response body {}",responseString);
	}
}
