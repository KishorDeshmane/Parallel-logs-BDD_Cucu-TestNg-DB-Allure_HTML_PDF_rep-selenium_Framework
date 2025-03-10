package stepDefinitions;

import java.util.List;
import org.testng.annotations.Test;
import com.qa.dao.UserDAO;
import com.qa.model.UserModel;

public class test {
	 UserDAO dao;
	 List<UserModel> users;
	
	@Test
    public void testDB() {
        dao = new UserDAO();
        users = dao.getAllUsers();
		 /* Print all emails */
		 System.out.println("All Emails:");
		 for (int i = 0; i < users.size(); i++) {
		 System.out.println("Email " + (i + 1) + ": " + users.get(i).getEmail());
		 }
        /* Print specific emails by index */
        System.out.println("\nPrinting Specific Indexes:");
        if (users.size() > 0) System.out.println("First Email: " + users.get(0));  // First email
        if (users.size() > 1) System.out.println("Second Email: " + users.get(1)); // Second email
        if (users.size() > 2) System.out.println("Third Email: " + users.get(2));  // Third email
    }
	
	@Test
	public void getUserById() {
		dao = new UserDAO();
        int userId = 2; // Change this ID based on your database
        UserModel user = dao.getUserById(userId);
        if (user != null) {
            System.out.println("User found: " + user);
        } else {
            System.out.println("No user found with ID " + userId);
        }
    }
	
	@Test
    public void testInsertUser() {
		dao = new UserDAO();
        String name = "Alice Smith";
        String email = "alice1@example.com";
       boolean isInserted = dao.insertUser(name, email);
        if (isInserted) {
            System.out.println("User successfully inserted: " + name + " (" + email + ")");
        } else {
            System.out.println("Failed to insert user.");
        }
    }
	
	@Test
    public void testUpdateUserEmail() {
        dao = new UserDAO();
        int userId = 6; // Change this ID based on your database
        String newName = "Updated Name";
        String newEmail = "updated.email@example.com";
        boolean isUpdated = dao.updateUser(userId, newName, newEmail);
        if (isUpdated) {
            System.out.println("User successfully updated: ID " + userId + " -> " + newName + " (" + newEmail + ")");
        } else {
            System.out.println("Failed to update user with ID " + userId);
        }
    }
	
	@Test
	public void testDeleteUser() {
        dao = new UserDAO();
        int userId = 6; // Change this ID based on your database
        boolean isDeleted = dao.deleteUser(userId);
        if (isDeleted) {
            System.out.println("User successfully deleted: ID " + userId);
        } else {
            System.out.println("Failed to delete user with ID " + userId);
        }
    }
	
}
