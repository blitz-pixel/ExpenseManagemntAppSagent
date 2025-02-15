package com.example.ExpenseManagementApp.Tests;

import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Repositories.UserRepository;
import com.example.ExpenseManagementApp.Services.UserService;
import com.example.ExpenseManagementApp.TestUtils.UserTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class RegisterEndPointTests {

    @Container
     static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("examplemanagementappdb")
            .withUsername("root")
            .withPassword("1234");

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    private  final UserTest userTest = new UserTest();

    @BeforeAll
    static void setUp() {
        mySQLContainer.start();
        System.setProperty("spring.datasource.url", mySQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", mySQLContainer.getUsername());
        System.setProperty("spring.datasource.password", mySQLContainer.getPassword());
    }

    @Test
    public void testThatUserIsRegistered() {
        User user = userTest.testUserDetails();
        System.out.println("User before save: " + user);

        User savedUser = userRepository.save(user);
        System.out.println("User after save: " + savedUser);

        userRepository.flush();

        assertAll(
                () -> assertNotNull(savedUser, "User should be saved"),
                () -> assertEquals("John Doe", savedUser.getUserName(), "Usernames should match"),
                () -> assertEquals("John123@gmail.com", savedUser.getEmail(), "Emails should match"),
                () -> assertEquals("123456", savedUser.getPassword(), "Passwords should match"),
                () -> assertNotNull(savedUser.getCreatedAt(), "Created at should not be null"),
                () -> assertNotNull(savedUser.getUser_id(), "User ID should not be null")
        );

        User savedDBUser = userRepository.findByEmail(savedUser.getEmail()).orElse(null);
        assertAll(
                () -> assertNotNull(savedDBUser, "User in db should be saved"),
                () -> assertNotNull(savedDBUser.getCreatedAt(), "Created at should not be null in db"),
                () -> assertNotNull(savedDBUser.getUser_id(), "User ID should not be null in db")
        );
    }
}