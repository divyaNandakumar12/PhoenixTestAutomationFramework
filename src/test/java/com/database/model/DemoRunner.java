package com.database.model;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.dao.CustomerAddressDAO;
import com.database.dao.CustomerDAO;

public class DemoRunner {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		CustomerAddressDBModel customerAddressDBModel=CustomerAddressDAO.getCustomerAddress(349665);

		System.out.println(customerAddressDBModel);
		
		
	}

}
