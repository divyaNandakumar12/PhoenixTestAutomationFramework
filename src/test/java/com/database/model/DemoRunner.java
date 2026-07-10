package com.database.model;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.dao.CustomerDAO;

public class DemoRunner {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		CustomerDBModel customerDBModel=CustomerDAO.getCustomerInfo();

		System.out.println(customerDBModel);
		
		Customer customer=new Customer("Nannie", "Okuneva", "670-465-8488", "", "Reyna_Kassulke18@gmail.com", "");
		Assert.assertEquals(customerDBModel.getFirst_name(), customer.first_name());
		
		
	}

}
