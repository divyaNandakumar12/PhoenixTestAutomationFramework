package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;
import com.database.model.MapJobProblemDBModel;

public class MapJobProblemDAO {

	private static final String SQL_QUERY = """
						select * from map_job_problem mjp where mjp.tr_job_head_id =?
						""";

	private MapJobProblemDAO() {

	}

	public static MapJobProblemDBModel getProblemInfo(int tr_job_head_id) {
		MapJobProblemDBModel mapJobProblemDBModel = null;
		try {
			Connection connection = DatabaseManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY);
			preparedStatement.setInt(1, tr_job_head_id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				mapJobProblemDBModel = new MapJobProblemDBModel(resultSet.getInt("id"),resultSet.getInt("tr_job_head_id"),resultSet.getInt("mst_problem_id"),
						resultSet.getString("remark"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		;
		return mapJobProblemDBModel;
	}

}
