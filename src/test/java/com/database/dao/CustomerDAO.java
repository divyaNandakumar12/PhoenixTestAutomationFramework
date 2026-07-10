package com.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;
import com.dataproviders.api.bean.CreateJobBean;

public class CustomerDAO {
	
	private static final String SQL_QUERY="""
			select * from tr_customer tc where tc.id =349790
			""";

	public static CustomerDBModel getCustomerInfo() throws SQLException {
		Connection connection=DatabaseManager.getConnection();;
		Statement statement=connection.createStatement();
		ResultSet resultSet=statement.executeQuery(SQL_QUERY);
		CustomerDBModel customerDBModel=null;
		while(resultSet.next()) {
			System.out.println(resultSet.getString("first_name"));
			customerDBModel=new CustomerDBModel(resultSet.getString("first_name"), resultSet.getString("last_name"), 
					resultSet.getString("mobile_number"), resultSet.getString("mobile_number_alt"), resultSet.getString("email_id"),
					resultSet.getString("email_id_alt"));
		}
		
		return customerDBModel;
	}
	
}
