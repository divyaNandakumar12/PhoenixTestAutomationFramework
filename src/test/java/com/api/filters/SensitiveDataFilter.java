package com.api.filters;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter{
    private static final Logger LOGGER=LogManager.getLogger(SensitiveDataFilter.class);
	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		LOGGER.info("****************REQUEST DETAILS*******************************");
		LOGGER.info("BASE URI: {}",requestSpec.getURI());
		LOGGER.info("HTTP METHOD: {}",requestSpec.getMethod());
		LOGGER.info("REQUEST HEADERS: \n {}",requestSpec.getHeaders());
		reductHeader(requestSpec);
		reductPayload(requestSpec);
		Response response= ctx.next(requestSpec, responseSpec);
		LOGGER.info("****************RESPONSE DETAILS*******************************");
		LOGGER.info("STATUS : {}",response.getStatusLine());
		LOGGER.info("RESPONSE TIME: {}",response.timeIn(TimeUnit.MILLISECONDS));
		LOGGER.info("RESPONSE HEADERS: \n {}",response.getHeaders());
		reductResponseBody(response);
		return response;
	}

	private void reductHeader(FilterableRequestSpecification requestSpec) {
	     List<Header> headerList=requestSpec.getHeaders().asList();
	     for(Header h:headerList) {
	    	 if(h.getName().equalsIgnoreCase("Authorization")) {
	    		 LOGGER.info("HEADER  {} :  {}",h.getName(),"\"[REDACTED]\"");
	    	 }else {
	    		 LOGGER.info("HEADER  {} :  {}",h.getName(),h.getValue());
	    	 }
	     }
		
	}

	private void reductPayload(FilterableRequestSpecification requestSpec) {
		if(requestSpec.getBody()!=null) {
			String requestPayload= requestSpec.getBody().toString();
			requestPayload=requestPayload.replaceAll(
				    "\"password\"\\s*:\\s*\"[^\"]*\"",
				    "\"password\":\"[REDACTED]\""
				);
			LOGGER.info("Request payload \n {}",requestPayload);
		}
	}
	
	
	private void reductResponseBody(Response response) {
		String responseString= response.asPrettyString();
		responseString=responseString.replaceAll(
			    "\"token\"\\s*:\\s*\"[^\"]*\"",
			    "\"token\":\"[REDACTED]\""
			);
		LOGGER.info("Response body \n {}",responseString);
	}
}
