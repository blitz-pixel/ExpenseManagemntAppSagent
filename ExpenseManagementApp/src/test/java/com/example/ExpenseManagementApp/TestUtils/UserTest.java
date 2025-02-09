package com.example.ExpenseManagementApp.TestUtils;

import com.example.ExpenseManagementApp.Model.User;

import java.time.Instant;

public class UserTest {


    public User testUserDetails() {
        User user = new User();
        user.setUserName("John Doe");
        user.setEmail("John123@gmail.com");
        user.setPassword("123456");
//        user.setCreatedAt(Instant.now());
//        user.setUser_id(1L);
        return user;
    }
}
