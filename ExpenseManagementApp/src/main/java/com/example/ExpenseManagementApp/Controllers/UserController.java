package com.example.ExpenseManagementApp.Controllers;

//import com.example.ExpenseManagementApp.Configuration.JwtUtil;
import com.example.ExpenseManagementApp.DTO.LoginDTO;
import com.example.ExpenseManagementApp.DTO.UserDTO;
import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Services.AccountService;
import com.example.ExpenseManagementApp.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService userService;
    private AccountService accountService;

    @Autowired
    public void setAccountService(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    Logger logger = Logger.getLogger(UserController.class.getName());

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO login) {

        try {

            Long accountID = userService.ValidateUser(login);
            logger.info("Token" + accountID);
            return  ResponseEntity.ok().header("X-Account-ID" ,String.valueOf(accountID)).body("Login successful");
//
        } catch (Exception e) {
            // Log the exception
            logger.severe("Login failed due to an error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        try {
            userService.addUserPersonal(userDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody UserDTO userDTO, @RequestParam Long accountId) {
        try {
            User user = accountService.getUser(accountId);
            userService.UpdateUserDetails(userDTO, user.getEmail());
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}