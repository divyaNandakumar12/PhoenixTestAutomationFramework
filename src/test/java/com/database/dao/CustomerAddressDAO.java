package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.database.DatabaseManager;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.dataproviders.api.bean.CreateJobBean;

public class CustomerAddressDAO {

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

	public static CustomerAddressDBModel getCustomerAddress(int customerAddressId) {
		CustomerAddressDBModel customerAddressDBModel = null;
		try {
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
			preparedStatement.setInt(1, customerAddressId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customerAddressDBModel=new CustomerAddressDBModel(resultSet.getInt("id"),resultSet.getString("flat_number"), resultSet.getString("apartment_name"), resultSet.getString("street_name"),
						resultSet.getString("landmark"), resultSet.getString("area"), resultSet.getString("pincode"), resultSet.getString("country"), 
						resultSet.getString("state"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return customerAddressDBModel;
	}

}
