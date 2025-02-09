package com.example.ExpenseManagementApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableFeignClients
@SpringBootApplication
public class
ExpenseManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseManagementAppApplication.class, args);

	}
}