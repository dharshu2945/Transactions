package com.wipro.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
public static Connection getDBConnection() {
	try {
		Class.forName("oracle.jdbc.OracleDriver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user="SYSTEM";
	String pass="dharshu";
	Connection connection = null;
	try {
		connection = DriverManager.getConnection(url,user,pass);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return connection;
}
}