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
import com.database.model.CustomerDBModel;
import com.dataproviders.api.bean.CreateJobBean;

import io.qameta.allure.Step;

public class CustomerDAO {

	private static final Logger logger=LogManager.getLogger(CustomerDAO.class);
	private static final String SQL_QUERY = """
			select * from tr_customer tc where tc.id =?
			""";
	
	private CustomerDAO() {
		
	}

	@Step("Retrieving the Customer information from database for the specific customer id")
	public static CustomerDBModel getCustomerInfo(int customerId){
		CustomerDBModel customerDBModel=null;
		try {
	    logger.info("Geting connection from the Database manager");
		Connection	connection = DatabaseManager.getConnection();
		PreparedStatement preparedStatement=connection.prepareStatement(SQL_QUERY);
		preparedStatement.setInt(1, customerId);
		logger.info("Executing the Sql query {}",SQL_QUERY);
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()) {
			System.out.println(resultSet.getString("first_name"));
			customerDBModel=new CustomerDBModel(resultSet.getInt("id"),resultSet.getString("first_name"), resultSet.getString("last_name"), 
					resultSet.getString("mobile_number"), resultSet.getString("mobile_number_alt"), resultSet.getString("email_id"),
					resultSet.getString("email_id_alt"),resultSet.getInt("tr_customer_address_id"));
		}
		} catch (SQLException e) {
			logger.error("Cannot convert the result set to the bean",e);
			e.printStackTrace();
		};
		return customerDBModel;
	}
	
}
