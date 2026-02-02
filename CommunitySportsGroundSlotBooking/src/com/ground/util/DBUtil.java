package com.ground.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	public static java.sql.Connection getDBConnection(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			}catch(ClassNotFoundException e) {
		e.printStackTrace();
	}
		String url="jdbc:oracle:thin:@localhost:1521:XE";
		String user="SYSTEM";
		String pass="dharshu";
		Connection con=null;
		try {
			con=DriverManager.getConnection(url,user,pass);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
		
	}
	

}
