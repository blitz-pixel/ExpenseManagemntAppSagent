package com.example.ExpenseManagementApp.Controllers;

//import com.example.ExpenseManagementApp.Configuration.JwtUtil;
import com.example.ExpenseManagementApp.DTO.AccountDTO;
        import com.example.ExpenseManagementApp.Services.AccountService;
        import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/profiles")
    public ResponseEntity<List<AccountDTO>> getUserAccounts(@RequestParam Long accountId) {
//        String username = authentication.getName();
        try {
            Long userID = accountService.getUserId(accountId);
            List<AccountDTO> accounts = accountService.getAccountsPersonal(userID);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUserPersonalAccount(@RequestParam Long accountId, @RequestBody AccountDTO accountDTO) {
        try {
            accountService.addAccount(accountId, accountDTO);
            return ResponseEntity.ok("Account added successfully");
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}