package com.qa.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
	
	private static Properties configProperties;
	private static Properties testDataProperties;
	private static Properties sqlQueries;

	private static final String MAIN_CONFIG_PATH  = "./src/test/resources/org/config/config.properties";

	// Private constructor to prevent instantiation
	public ConfigManager() {
	}

	/**
	 * Loads configuration properties once
	 * 
	 * @return Properties object containing configuration settings
	 */
	public static synchronized Properties getConfigProperties() {
		if (configProperties == null) {
			configProperties = loadProperties(MAIN_CONFIG_PATH);
		}
		return configProperties;
	}

	/**
	 * Loads test data properties once
	 * 
	 * @return Properties object containing test data
	 */
	public static synchronized Properties getTestDataProperties() {
		if (testDataProperties == null) {
			String testDataPath = getConfigProperties().getProperty("test.data.path");
			testDataProperties = loadProperties(testDataPath);
		}
		return testDataProperties;
	}

	/**
	 * Loads SQL queries from sql_queries.properties file
	 * 
	 * @return Properties object containing SQL queries
	 */
	public static synchronized Properties getSqlQueries() {
		if (sqlQueries == null) {
			String sql_query_path = getConfigProperties().getProperty("sql.queries.path");
			sqlQueries = loadProperties(sql_query_path);
		}
		return sqlQueries;
	}

	/**
	 * Generic method to load properties from a given file path
	 * 
	 * @param filePath Path to the properties file
	 * @return Loaded Properties object
	 */
	private static Properties loadProperties(String filePath) {
		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream(filePath)) {
			properties.load(fis);
		} catch (IOException e) {
			System.err.println("Error loading properties file: " + filePath + " - " + e.getMessage());
		}
		return properties;
	}
	
	
	/**
     * Fetches property value from config, removes inline comments, and trims spaces
     * @param key Key of the property
     * @return Sanitized property value
     */
    public static String getProperty(String key) {
        String value = getConfigProperties().getProperty(key);
        if (value != null) {
            return value.split("#")[0].trim(); // Removes inline comments and trims whitespace
        }
        return null;
    }
	
}
