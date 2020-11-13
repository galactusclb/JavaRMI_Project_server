package com.lx.DbConnection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionProvider {
	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn != null) {
			return conn;
		} else {
			try {
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://localhost:3306/universityfeedbackdb";
				String user = "root";
				String password = "";

				Class.forName(driver);

				conn = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch (SQLException e) {
				System.out.println(e);
			}
			return conn;
		}
	}
}
