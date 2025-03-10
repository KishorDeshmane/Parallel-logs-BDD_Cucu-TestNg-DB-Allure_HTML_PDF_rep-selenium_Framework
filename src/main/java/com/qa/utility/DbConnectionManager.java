package com.qa.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionManager {
	 private static final Properties configReader = ConfigManager.getConfigProperties();
	
    private static final String URL = configReader.getProperty("db.url");
    private static final String USER = configReader.getProperty("db.username");
    private static final String PASSWORD = configReader.getProperty("");
    private static final String driver = configReader.getProperty("db.driver");

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }
}
