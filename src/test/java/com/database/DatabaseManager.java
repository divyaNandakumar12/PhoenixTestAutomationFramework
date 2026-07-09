package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.api.utils.ConfigManager;

public class DatabaseManager {
	
	private DatabaseManager() {
		
	}
	
	private static final String DB_URL=ConfigManager.getProperty("DB_URL");
	private static final String DB_USERNAME=ConfigManager.getProperty("DB_USERNAME");
	private static final String DB_PASSWORD=ConfigManager.getProperty("DB_PASSWORD");
	private static volatile Connection connection;
	

	public synchronized static Connection createConnection() throws SQLException {
		
			if(connection==null) {
				synchronized (DatabaseManager.class) {
					if(connection==null) {
						connection = DriverManager.getConnection(DB_URL,
								DB_USERNAME, DB_PASSWORD);
					}
				}
			}
		
		
		return connection;
	}

}
