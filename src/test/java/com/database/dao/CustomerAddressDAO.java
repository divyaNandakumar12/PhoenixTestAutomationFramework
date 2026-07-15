package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.dataproviders.api.bean.CreateJobBean;

import io.qameta.allure.Step;

public class CustomerAddressDAO {

	private static final Logger logger=LogManager.getLogger(CustomerAddressDAO.class);
	private static final String SQL_QUERY = """
						select
			tca.id,
			tca.flat_number,
			tca.apartment_name,
			tca.street_name,
			tca.landmark,
			tca.area,
			tca.pincode,
			tca.country,
			tca.state from tr_customer_address tca where tca.id =?
						""";
	
	private CustomerAddressDAO() {
		
	}

	@Step("Retrieving the Customer address data from database for the specific customer address id")
	public static CustomerAddressDBModel getCustomerAddress(int customerAddressId) {
		CustomerAddressDBModel customerAddressDBModel = null;
		try {
			logger.info("Geting connection from the Database manager");
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
			preparedStatement.setInt(1, customerAddressId);
			logger.info("Executing the Sql query {}",SQL_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customerAddressDBModel=new CustomerAddressDBModel(resultSet.getInt("id"),resultSet.getString("flat_number"), resultSet.getString("apartment_name"), resultSet.getString("street_name"),
						resultSet.getString("landmark"), resultSet.getString("area"), resultSet.getString("pincode"), resultSet.getString("country"), 
						resultSet.getString("state"));
			}
		} catch (SQLException e) {
			logger.error("Cannot convert the result set to the bean",e);
			e.printStackTrace();
		}
		;
		return customerAddressDBModel;
	}

}
