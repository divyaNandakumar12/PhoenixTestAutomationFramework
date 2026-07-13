package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtilityClass;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

	private DatabaseManager() {

	}

	private static final String DB_URL = getDBValues("DB_URL");
	private static final String DB_USERNAME = getDBValues("DB_USERNAME");
	private static final String DB_PASSWORD = getDBValues("DB_PASSWORD");
	private static boolean isVaultUp = true;
	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE"));
	private static final int CONNECTION_TIMEOUT = Integer.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT"));
	private static final int IDLE_TIMEOUT = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT"));
	private static final int MAX_LIFETIME = Integer.parseInt(ConfigManager.getProperty("MAX_LIFETIME"));
	private static final String POOL_NAME = ConfigManager.getProperty("POOL_NAME");

	private static HikariConfig hikariConfig;
	private static volatile HikariDataSource hikariDataSource;

	private static String getDBValues(String key) {
		String valueString = null;

		if (isVaultUp)
			valueString = VaultDBConfig.getSecretValue(key);
		if (valueString == null) {
			System.err.println("Vault is down or some issue with vault");
			isVaultUp=false;
		} else {
			System.out.println("Reading value from vault");
			return valueString;
		}

		System.out.println("READING VALUE FROM ENV...");
		valueString = EnvUtilityClass.getValue(key);
		return valueString;

	}

	private synchronized static void initializePool() {

		if (hikariDataSource == null) {
			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) {
					hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USERNAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT);
					hikariConfig.setIdleTimeout(IDLE_TIMEOUT);
					hikariConfig.setMaxLifetime(MAX_LIFETIME);
					hikariConfig.setPoolName(POOL_NAME);
					hikariDataSource = new HikariDataSource(hikariConfig);

				}
			}
		}

	}

	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		if (hikariDataSource == null) {
			initializePool();
		} else if (hikariDataSource.isClosed()) {
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}

		conn = hikariDataSource.getConnection();

		return conn;
	}

}
