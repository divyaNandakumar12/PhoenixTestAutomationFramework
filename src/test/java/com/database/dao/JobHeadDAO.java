package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;
import com.database.model.JobHeadModel;

public class JobHeadDAO {
	private static final Logger logger=LogManager.getLogger(JobHeadDAO.class);
	private static final String SQL_QUERY = """
			select * from tr_job_head tjh where tjh.tr_customer_id =?
			""";
	
	private JobHeadDAO() {
		
	}

	public static JobHeadModel getJobInfo(int customerId){
		JobHeadModel jobHeadModel=null;
		try {
		logger.info("Geting connection from the Database manager");
		Connection	connection = DatabaseManager.getConnection();
		PreparedStatement preparedStatement=connection.prepareStatement(SQL_QUERY);
		preparedStatement.setInt(1, customerId);
		logger.info("Executing the Sql query {}",SQL_QUERY);
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()) {
			jobHeadModel=new JobHeadModel(resultSet.getInt("id"),resultSet.getString("job_number"),resultSet.getInt("tr_customer_id"),resultSet.getInt("tr_customer_product_id"),
					resultSet.getInt("mst_service_location_id"),
					resultSet.getInt("mst_platform_id"),resultSet.getInt("mst_warrenty_status_id"),resultSet.getInt("mst_oem_id"));
		}
		} catch (SQLException e) {
			logger.error("Cannot convert the result set to the bean",e);
			e.printStackTrace();
		};
		return jobHeadModel;
	}

}
