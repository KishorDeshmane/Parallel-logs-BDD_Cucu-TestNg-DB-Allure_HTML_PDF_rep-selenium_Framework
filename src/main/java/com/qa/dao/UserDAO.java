package com.qa.dao;

import com.qa.model.UserModel;
import com.qa.utility.DbConnectionManager;
import com.qa.utility.ConfigManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDAO {
	private static final Properties sqlQueries = ConfigManager.getSqlQueries(); // Load SQL queries once

	/**
	 * Fetch all users from the database
	 * 
	 * @return List of UserModel objects
	 */
	public List<UserModel> getAllUsers() {
		List<UserModel> users = new ArrayList<>();
		String query = sqlQueries.getProperty("query.getAllUsers");

		try (Connection conn = DbConnectionManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				users.add(new UserModel(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
			}
		} catch (SQLException e) {
			System.err.println("Error fetching users: " + e.getMessage());
		}
		return users;
	}

	/**
	 * Fetch a user by ID
	 * 
	 * @param userId The user ID to search for
	 * @return UserModel object if found, else null
	 */
	public UserModel getUserById(int userId) {
		String query = sqlQueries.getProperty("query.getUserById");
		try (Connection conn = DbConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new UserModel(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
			}
		} catch (SQLException e) {
			System.err.println("Error fetching user by ID: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Insert a new user into the database
	 * 
	 * @param name  User's name
	 * @param email User's email
	 * @return true if insertion is successful, else false
	 */
	public boolean insertUser(String name, String email) {
		String query = sqlQueries.getProperty("query.insertUser");
		try (Connection conn = DbConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, name);
			pstmt.setString(2, email);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error inserting user: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Update user details by ID
	 * 
	 * @param userId   ID of the user to update
	 * @param newName  New name
	 * @param newEmail New email
	 * @return true if update is successful, else false
	 */
	public boolean updateUser(int userId, String newName, String newEmail) {
		String query = sqlQueries.getProperty("query.updateUser");
		try (Connection conn = DbConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, newName);
			pstmt.setString(2, newEmail);
			pstmt.setInt(3, userId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error updating user: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Delete user by ID
	 * 
	 * @param userId ID of the user to delete
	 * @return true if deletion is successful, else false
	 */
	public boolean deleteUser(int userId) {
		String query = sqlQueries.getProperty("query.deleteUser");
		try (Connection conn = DbConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, userId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error deleting user: " + e.getMessage());
		}
		return false;
	}
}
