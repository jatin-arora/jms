package com.jeet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
	private Connection con;

	public ConnectionManager() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	private void init() throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jeet",
				"jatin", "6432");
	}


	Connection getConnection(){
		return con;
	}
	
	Statement getStatement(){
		try {
			return con.createStatement();
		} catch (SQLException e) {
		}
		return null;
	}

}
