package com.example.ExpenseManagementApp.Tests;

import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Repositories.UserRepository;
import com.example.ExpenseManagementApp.Services.UserService;
import com.example.ExpenseManagementApp.TestUtils.UserTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RegisterTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // Initialize UserTest manually if it's not autowired
    private final UserTest userTest = new UserTest();

//    @Test
//    public void testRegister() throws InterruptedException {
//        // Get a test user from userTest
//        User user = userTest.testUserDetails();
//
//        // Call the service method to add the user
//        User savedUser = userService.addUser(user);
//        System.out.println("User: " + savedUser.getUserName());
//        System.out.println("User ID: " + savedUser.getUser_id());
//        Thread.sleep(1000);
//
//        // Verify that the user is saved correctly
//        assertNotNull(savedUser, "User should be saved");
//        assertEquals("John Doe", savedUser.getUserName(), "Usernames should match");
//        assertEquals("John123@gmail.com", savedUser.getEmail(), "Emails should match");
//        assertEquals("123456", savedUser.getPassword(), "Passwords should match");
//        assertNotNull(savedUser.getCreatedAt(), "Created at should not be null");
//        assertNotNull(savedUser.getUser_id(), "User ID should not be null");
//
//        // Fetch the user from the repository
//        Optional<User> savedUserDB = userRepository.findById(savedUser.getUser_id());
//        assertTrue(savedUserDB.isPresent(), "User should be found in the database");
//
//        // Verify the details of the fetched user
//        User fetchedUser = savedUserDB.get();
//        System.out.println("UserDB Created At: " + fetchedUser.getCreatedAt());
//        Thread.sleep(1000);
//
//        assertEquals("John Doe", fetchedUser.getUserName(), "Usernames should match");
//        assertEquals("John123@gmail.com", fetchedUser.getEmail(), "Emails should match");
//        assertEquals("123456", fetchedUser.getPassword(), "Passwords should match");
//        assertNotNull(fetchedUser.getCreatedAt(), "Created at should not be null");
//    }
}