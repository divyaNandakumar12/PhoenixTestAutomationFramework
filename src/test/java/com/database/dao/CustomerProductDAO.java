package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDAO {

	private static final String SQL_QUERY = """
						select tcp.tr_customer_id,tcp.dop,
			tcp.serial_number,
			tcp.imei1,
			tcp.imei2,
			tcp.popurl,
			tcp.mst_model_id from tr_customer_product tcp where tcp.id =?
						""";

	private CustomerProductDAO() {

	}

	public static CustomerProductDBModel getCustomerProductInfo(int customerProductId) {
		CustomerProductDBModel customerProductDBModel = null;
		try {
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
			preparedStatement.setInt(1, customerProductId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customerProductDBModel = new CustomerProductDBModel(resultSet.getInt("tr_customer_id"),
						resultSet.getString("dop"), resultSet.getString("serial_number"), resultSet.getString("imei1"),
						resultSet.getString("imei2"), resultSet.getString("popurl"), resultSet.getInt("mst_model_id"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		;
		return customerProductDBModel;
	}

}
