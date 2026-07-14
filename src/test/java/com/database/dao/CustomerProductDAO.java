package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDAO {

	private static final Logger logger=LogManager.getLogger(CustomerProductDAO.class);
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
			logger.info("Geting connection from the Database manager");
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
			preparedStatement.setInt(1, customerProductId);
			logger.info("Executing the Sql query {}",SQL_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customerProductDBModel = new CustomerProductDBModel(resultSet.getInt("tr_customer_id"),
						resultSet.getString("dop"), resultSet.getString("serial_number"), resultSet.getString("imei1"),
						resultSet.getString("imei2"), resultSet.getString("popurl"), resultSet.getInt("mst_model_id"));
			}
		} catch (SQLException e) {
			logger.error("Cannot convert the result set to the bean",e);
			e.printStackTrace();
		}
		;
		return customerProductDBModel;
	}

}
