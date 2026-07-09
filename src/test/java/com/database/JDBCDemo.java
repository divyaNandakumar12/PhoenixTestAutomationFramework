package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo {

	public static void main(String[] args) throws SQLException {

		Connection connection = DriverManager.getConnection("jdbc:mysql://64.227.160.186 :3306/SR_DEV",
				"srdev_ro_automation", "Srdev@123");

		Statement statement = connection.createStatement();
		ResultSet ResultSet = statement
				.executeQuery("select tc.first_name ,tc.last_name ,tc.mobile_number  from tr_customer tc");

		while (ResultSet.next()) {
			String firstName = ResultSet.getString("first_name");
			String lastName = ResultSet.getString("last_name");
			String mobileNumber = ResultSet.getString("mobile_number");
			
			System.out.println(firstName+"|"+lastName+"|"+mobileNumber);
		}

	}

}
