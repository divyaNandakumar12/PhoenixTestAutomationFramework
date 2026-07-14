package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;
import com.database.model.MapJobProblemDBModel;

public class MapJobProblemDAO {

	private static final Logger logger=LogManager.getLogger(MapJobProblemDAO.class);
	private static final String SQL_QUERY = """
						select * from map_job_problem mjp where mjp.tr_job_head_id =?
						""";

	private MapJobProblemDAO() {

	}

	public static MapJobProblemDBModel getProblemInfo(int tr_job_head_id) {
		MapJobProblemDBModel mapJobProblemDBModel = null;
		try {
			logger.info("Geting connection from the Database manager");
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
			preparedStatement.setInt(1, tr_job_head_id);
			logger.info("Executing the Sql query {}",SQL_QUERY);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				mapJobProblemDBModel = new MapJobProblemDBModel(resultSet.getInt("id"),resultSet.getInt("tr_job_head_id"),resultSet.getInt("mst_problem_id"),
						resultSet.getString("remark"));
			}
		} catch (SQLException e) {
			logger.error("Cannot convert the result set to the bean",e);
			e.printStackTrace();
		}
		;
		return mapJobProblemDBModel;
	}

}
