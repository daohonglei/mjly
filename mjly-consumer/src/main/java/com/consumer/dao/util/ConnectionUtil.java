package com.consumer.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private Connection connection;
	private boolean isUse;
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public boolean isUse() {
		return isUse;
	}
	public void setUse(boolean isUse) {
		this.isUse = isUse;
	}
	@SuppressWarnings("unused")
	public ConnectionUtil(String url,String username,String password,String driver) {
		try {
			String driverName =driver;
			connection = DriverManager.getConnection(url, username, password);
			isUse=false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
