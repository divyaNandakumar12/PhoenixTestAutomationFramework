package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtilityClass;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.qameta.allure.Step;

public class DatabaseManager {

	private DatabaseManager() {

	}
	private static final Logger logger=LogManager.getLogger(DatabaseManager.class);
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

	@Step("Loading Database secrets")
	private static String getDBValues(String key) {
		String valueString = null;

		if (isVaultUp)
			valueString = VaultDBConfig.getSecretValue(key);
		if (valueString == null) {
			logger.error("Vault is down or some issue with vault");
			isVaultUp=false;
		} else {
			logger.info("Reading value for key {} from vault",key);
			return valueString;
		}

		logger.info("READING VALUE FROM ENV...");
		valueString = EnvUtilityClass.getValue(key);
		return valueString;

	}

	@Step("Initializing database connection pool")
	private synchronized static void initializePool() {

		if (hikariDataSource == null) {
			logger.warn("Database connection is not available .....Creating hikariDataSource");
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
					logger.info("hikariDataSource created");
				}
			}
		}

	}

	@Step("Getting the database connection")
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		if (hikariDataSource == null) {
			logger.info("Initializing the Database connection using HikariCP");
			initializePool();
		} else if (hikariDataSource.isClosed()) {
			logger.error("HIKARI DATA SOURCE IS CLOSED");
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}

		conn = hikariDataSource.getConnection();

		return conn;
	}

}
