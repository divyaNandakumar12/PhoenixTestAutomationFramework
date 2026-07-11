package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;
import com.dataproviders.api.bean.CreateJobBean;

public class CustomerDAO {

	private static final String SQL_QUERY = """
			select * from tr_customer tc where tc.id =?
			""";
	
	private CustomerDAO() {
		
	}

	public static CustomerDBModel getCustomerInfo(int customerId){
		CustomerDBModel customerDBModel=null;
		try {
		Connection	connection = DatabaseManager.getConnection();
		PreparedStatement preparedStatement=connection.prepareStatement(SQL_QUERY);
		preparedStatement.setInt(1, customerId);
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()) {
			System.out.println(resultSet.getString("first_name"));
			customerDBModel=new CustomerDBModel(resultSet.getInt("id"),resultSet.getString("first_name"), resultSet.getString("last_name"), 
					resultSet.getString("mobile_number"), resultSet.getString("mobile_number_alt"), resultSet.getString("email_id"),
					resultSet.getString("email_id_alt"),resultSet.getInt("tr_customer_address_id"));
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return customerDBModel;
	}
	
}
