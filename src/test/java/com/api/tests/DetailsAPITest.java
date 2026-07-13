package com.api.tests;


import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.request.model.Detail;
import com.api.services.AuthService;
import com.api.services.DashboardService;
import static com.api.utils.SpecUtil.*;

public class DetailsAPITest {
	
	private DashboardService dashboardService;
	
	private Detail detail;
	
	@BeforeMethod(description = "Instantiating Dashboard Service and creating Detail payload")
	public void setup() {
		dashboardService=new DashboardService();
		detail=new Detail("created_today");
	}

	
	@Test(description = "Veify if the details API is working properly")
	public void detailAPITest() {
		dashboardService.details(FD, detail)
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Success"));
	}
	
}
