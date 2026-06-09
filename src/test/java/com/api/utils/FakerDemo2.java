package com.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;

public class FakerDemo2 {

	private static final String COUNTRY="India";
	public static void main(String[] args) {
		
			Faker faker=new Faker(new Locale("en-IND"));
			
			String fname=faker.name().firstName();
			String lname=faker.name().lastName();
			String phoneNumber=faker.numerify("##########");
			String altPhoneNumber=faker.numerify("##########");
			String emailAddress=faker.internet().emailAddress();
			String altEmailAddress=faker.internet().emailAddress();
			
			
			Customer customer=new Customer(fname, lname, phoneNumber, altPhoneNumber, emailAddress, altEmailAddress);
			System.out.println(customer);
			
			String flatNumber=faker.numerify("###");
			String apartmentName=faker.address().streetName();
			String streetName=faker.address().streetName();
			String landmark=faker.address().streetName();
			String area=faker.address().streetName();
			String pincode=faker.numerify("#####");
			String state=faker.address().state();
			CustomerAddress customerAddress=new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area, pincode, COUNTRY, apartmentName);
			
			System.out.println(customerAddress);
			
			//customer product details
			String dop=DateTimeUtil.getTimeWithDaysAgo(10);
			String imeiSerialNumber=faker.numerify("###############");
			String popurl=faker.internet().url();
			
			CustomerProduct customerProduct =new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber, popurl, 1, 1);
			System.out.println(customerProduct);
			
			Random random=new Random();
			int id=random.nextInt(27)+1;
			String remark=faker.lorem().sentence(5);
			Problems problems=new Problems(id, remark);
			
			List<Problems> problemList=new ArrayList<>();
			problemList.add(problems);
			
			CreateJobPayload createJobPayload=new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
			System.out.println(createJobPayload);
	}

}
