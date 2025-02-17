//package com.example.ExpenseManagementApp.Controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@Service
//public class DatabaseSource {
//
//    @Autowired
//    private DataSource dataSource;
//
//    public void printDatabaseUrl() {
//        try (Connection connection = dataSource.getConnection()) {
//            System.out.println("Database URL: " + connection.getMetaData().getURL());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
