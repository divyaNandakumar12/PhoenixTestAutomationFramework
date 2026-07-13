package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.Detail;
import com.api.request.model.Search;
import com.api.services.DashboardService;
import com.api.services.JobService;

public class SearchAPITest {

	private JobService jobService;

	private Search search;
	private static final String JOB_NUM="JOB_352478";

	@BeforeMethod(description = "Instantiating Dashboard Service and creating Detail payload")
	public void setup() {
		jobService = new JobService();
		search = new Search(JOB_NUM);
	}

	@Test(description = "Veify if the search API is working properly",groups = {"e2e","smoke"})
	public void detailAPITest() {
		jobService.search(FD, search).then().spec(responseSpec_OK()).body("message", equalTo("Success"));
	}

}
