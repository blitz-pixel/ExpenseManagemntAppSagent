package com.example.ExpenseManagementApp.Controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
//import com.example.ExpenseManagementApp.Configuration.JwtUtil;
import com.example.ExpenseManagementApp.DTO.AccountDTO;
import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Services.AccountService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {


    private AccountService accountService;
//    private JwtUtil jwtUtil;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    Logger logger = Logger.getLogger(AccountController.class.getName());


    @GetMapping
    public ResponseEntity<String> getTokenDecode() {
        try {
//            logger.info("Token got at Account: " + token);
            return ResponseEntity.ok("Account");
        } catch (Exception e) {
            logger.info(e.getMessage());
//            logger.info("Token got at Account: " + token);
            return ResponseEntity.badRequest().build();
        }


    }
    @GetMapping("/profile")
    public ResponseEntity<List<AccountDTO>> getUserAccounts(@RequestParam Long userID) {
//        String username = authentication.getName();
        try {
            List<AccountDTO> accounts = accountService.getAccountsByUsername(userID);
            logger.info(accounts.get(0).toString());
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}