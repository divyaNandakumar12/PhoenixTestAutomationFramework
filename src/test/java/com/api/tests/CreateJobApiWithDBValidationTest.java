package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.OEM;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.Role;
import com.api.constants.ServiceLocation;
import com.api.constants.Warrenty;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.services.JobService;
import com.database.dao.CustomerAddressDAO;
import com.database.dao.CustomerDAO;
import com.database.dao.CustomerProductDAO;
import com.database.dao.JobHeadDAO;
import com.database.dao.MapJobProblemDAO;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;
import com.database.model.MapJobProblemDBModel;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static com.api.utils.SpecUtil.*;

public class CreateJobApiWithDBValidationTest {

	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;
	private JobService jobService;

	@BeforeMethod(description = "Creating createjob api request payload and instantiating the job service")
	public void setup() {
		customer = new Customer("Rowland", "Wunsch", "758-252-7805", "", "Libbie_Frami@yahoo.com", "");
		customerAddress = new CustomerAddress("1229", "park villa", "15th street", "axis bank kolathur",
				"poompuhar nagar", "600099", "India", "Tamil Nadu");
		customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "11238575534409",
				"11238575534409", "11238575534409", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(),
				Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "phone is running slow");
		List<Problems> pr = new ArrayList<Problems>();
		pr.add(problems);
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warrenty.IN_WARRENTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, pr);
		jobService = new JobService();
	}

	@Test(description = "Verify if create job api ia able to create inwarrenty job", groups = { "api", "regression",
			"smoke" })
	public void createJobApiTest() throws IOException {
		        Response response= jobService.master(Role.FD, createJobPayload).then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("responseSchema/createJobApiResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_")).extract().response();

		        JsonPath jsonPath=response.jsonPath();
		        int customerId=jsonPath.getInt("data.tr_customer_id");
		        int customerProductId=jsonPath.getInt("data.tr_customer_product_id");
		        int tr_job_head_id=jsonPath.getInt("data.id");
		        System.out.println(customerId);
		        
		        CustomerDBModel customerDBModel=CustomerDAO.getCustomerInfo(customerId);

				System.out.println(customerDBModel);
				Assert.assertEquals(customerDBModel.getFirst_name(), customer.first_name());
				Assert.assertEquals(customerDBModel.getLast_name(), customer.last_name());
				Assert.assertEquals(customerDBModel.getMobile_number(), customer.mobile_number());
				Assert.assertEquals(customerDBModel.getMobile_number_alt(), customer.mobile_number_alt());
				Assert.assertEquals(customerDBModel.getEmail_id(), customer.email_id());
				Assert.assertEquals(customerDBModel.getEmail_id_alt(), customer.email_id_alt());
				
				int customerAddressId=customerDBModel.getTr_customer_address_id();
				System.out.println(customerAddressId);
				
				
				CustomerAddressDBModel customerAddressDBModel=CustomerAddressDAO.getCustomerAddress(customerAddressId);
				System.out.println(customerAddressDBModel);
				
				Assert.assertEquals(customerAddressDBModel.getFlat_number(), customerAddress.flat_number());
				Assert.assertEquals(customerAddressDBModel.getApartment_name(), customerAddress.apartment_name());
				Assert.assertEquals(customerAddressDBModel.getStreet_name(), customerAddress.street_name());
				Assert.assertEquals(customerAddressDBModel.getLandmark(), customerAddress.landmark());
				Assert.assertEquals(customerAddressDBModel.getArea(), customerAddress.area());
				Assert.assertEquals(customerAddressDBModel.getPincode(), customerAddress.pincode());
				Assert.assertEquals(customerAddressDBModel.getState(), customerAddress.state());
				Assert.assertEquals(customerAddressDBModel.getCountry(), customerAddress.country());
				
				
				
				CustomerProductDBModel customerProductDBModel=CustomerProductDAO.getCustomerProductInfo(customerProductId);
				System.out.println(customerProductDBModel);
				
				
				Assert.assertEquals(customerProductDBModel.getDop(), customerProduct.dop());
				Assert.assertEquals(customerProductDBModel.getSerial_number(), customerProduct.serial_number());
				Assert.assertEquals(customerProductDBModel.getImei1(), customerProduct.imei1());
				Assert.assertEquals(customerProductDBModel.getImei2(), customerProduct.imei2());
				Assert.assertEquals(customerProductDBModel.getPopurl(), customerProduct.popurl());
				Assert.assertEquals(customerProductDBModel.getMst_model_id(), customerProduct.mst_model_id());
				
				MapJobProblemDBModel mapJobProblemDBModel=MapJobProblemDAO.getProblemInfo(tr_job_head_id);
				System.out.println(mapJobProblemDBModel);
				
				Assert.assertEquals(mapJobProblemDBModel.getMst_problem_id(), createJobPayload.problems().get(0).id());
				Assert.assertEquals(mapJobProblemDBModel.getRemark(), createJobPayload.problems().get(0).remark());
				
				JobHeadModel jobHeadModel=JobHeadDAO.getJobInfo(customerId);
				System.out.println(jobHeadModel);
				
				Assert.assertEquals(jobHeadModel.getMst_service_location_id(), createJobPayload.mst_service_location_id());
				Assert.assertEquals(jobHeadModel.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
				Assert.assertEquals(jobHeadModel.getMst_platform_id(), createJobPayload.mst_platform_id());
				Assert.assertEquals(jobHeadModel.getMst_oem_id(), createJobPayload.mst_oem_id());
				
				
				
	}

}
