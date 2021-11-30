package com.cg.banking.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	private static Connection conn;
	private static String url=null; 
	private static String user=null;
	private static String password=null;
	private static String driver=null;

	public static Connection getConnection() throws DBUtilException  {
		String errorMsg="noerror";
		try {
			if (conn == null || conn.isClosed()) {
				initializeParams();
				Class.forName(driver);
				conn = DriverManager.getConnection(url, user, PasswordHandler.decrypt(password));
			}
		} catch (ClassNotFoundException e) {
			errorMsg="Driver not loaded";
			e.printStackTrace();
		} catch (SQLException e) {
			errorMsg="Error in database";
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			errorMsg="Config file not found";
			e.printStackTrace();
		} catch (IOException e) {
			errorMsg="Error reading config file";
			e.printStackTrace();
		}
		if( !errorMsg.equals("noerror")) {
			throw new DBUtilException(errorMsg);
		}
		return conn;
	}

	private static String PasswordHandler(String password2) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void initializeParams() throws FileNotFoundException, IOException {
		String configFile = "db.config";
		Properties props = new Properties();
		props.load(new FileInputStream(configFile));
		driver = props.getProperty("DRIVER");
		url = props.getProperty("URL");
		user = props.getProperty("USER");
		password = props.getProperty("PASSWORD");
	}
	
	public static void closeConnection() {
		if(conn!=null) {
			try {conn.close();} catch (SQLException e) {}
		}
	}


}
