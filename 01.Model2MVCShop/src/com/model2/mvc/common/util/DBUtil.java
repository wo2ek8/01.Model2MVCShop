package com.model2.mvc.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import sun.reflect.Reflection;


public class DBUtil {
	
	private final static String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final static String JDBC_URL = "jdbc:oracle:thin:scott/tiger@localhost:1521:xe";

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(JDBC_URL);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}