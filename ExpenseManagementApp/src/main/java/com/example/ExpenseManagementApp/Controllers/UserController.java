package com.example.ExpenseManagementApp.Controllers;

//import com.example.ExpenseManagementApp.Configuration.JwtUtil;
import com.example.ExpenseManagementApp.DTO.LoginDTO;
import com.example.ExpenseManagementApp.DTO.RegisterDTO;
import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Services.UserService;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    Logger logger = Logger.getLogger(UserController.class.getName()); // Create a logger instance

    @PostMapping("/Login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {


        try {
            Long accountID = userService.getAccountID(loginDTO.getEmail());
            logger.info("Token" + accountID);
            return  ResponseEntity.ok().header("X-Account-ID" ,String.valueOf(accountID)).body("Login successful");
//
        } catch (Exception e) {
            // Log the exception
            logger.severe("Login failed due to an error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/Registration")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        try {
            userService.addUserPersonal(registerDTO);
//            userService.AddToAccount(registerDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}