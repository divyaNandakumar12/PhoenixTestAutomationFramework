package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikaricpDemo {

	public static void main(String[] args) throws SQLException {
		
		HikariConfig hikariConfig=new HikariConfig();
		hikariConfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
		hikariConfig.setUsername(ConfigManager.getProperty("DB_USERNAME"));
		hikariConfig.setPassword(ConfigManager.getProperty("DB_PASSWORD"));
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMinimumIdle(2);
		hikariConfig.setConnectionTimeout(10000);
		hikariConfig.setIdleTimeout(10000);
		hikariConfig.setMaxLifetime(1800000);
		hikariConfig.setPoolName("Phoenix Test Automation framework pool");
		HikariDataSource hikariDataSource=new HikariDataSource(hikariConfig);
		Connection connection= hikariDataSource.getConnection();
		System.out.println(connection);
		
		Statement statement= connection.createStatement();
		ResultSet resultSet= statement.executeQuery("select tc.first_name ,tc.last_name ,tc.mobile_number  from tr_customer tc");

		while (resultSet.next()) {
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String mobileNumber = resultSet.getString("mobile_number");
			
			System.out.println(firstName+"|"+lastName+"|"+mobileNumber);
		}
		
		hikariDataSource.close();
	}

}
