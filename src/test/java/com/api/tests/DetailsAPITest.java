package com.api.tests;


import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.request.model.Detail;
import com.api.services.AuthService;
import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static com.api.utils.SpecUtil.*;
@Listeners(com.listener.ApiTestListener.class)
@Epic("Job Management")
@Feature("Job Details")
public class DetailsAPITest {
	
	private DashboardService dashboardService;
	
	private Detail detail;
	
	@BeforeMethod(description = "Instantiating Dashboard Service and creating Detail payload")
	public void setup() {
		dashboardService=new DashboardService();
		detail=new Detail("created_today");
	}

	@Story("Job details should be shown correctly")
	@Description("Verify if the details API is working properly")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verify if the details API is working properly")
	public void detailAPITest() {
		dashboardService.details(FD, detail)
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Success"));
	}
	
}
