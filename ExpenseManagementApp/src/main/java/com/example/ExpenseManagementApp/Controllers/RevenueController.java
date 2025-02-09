package com.example.ExpenseManagementApp.Controllers;

import com.example.ExpenseManagementApp.DTO.TransactionDTO;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import sun.util.logging.PlatformLogger;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/revenue")
public class RevenueController {
    Logger logger = Logger.getLogger(getClass().getName());


    public TransactionService transactionService;

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllRevenues(@RequestParam Long accountId) {
        try {
            return ResponseEntity.ok(transactionService.getTransactions(accountId, Category.CatType.income));
        } catch (Exception e) {
            logger.info("Error Revenue : " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRevenue(@RequestBody TransactionDTO transactionDTO) {

        try {
            if (transactionDTO == null) {
                logger.warning("Request body is null");
                return ResponseEntity.badRequest().body("Request body is missing");
            }

            logger.info("Received Request: Account ID = " + transactionDTO.getAccountId() +
                    ", Description = " + transactionDTO.getDescription() +
                    ", Amount = " + transactionDTO.getAmount() +
                    ", Category Names = " + transactionDTO.getSubCategoryName() + transactionDTO.getParentCategoryName()
            );

            if (transactionDTO.getAccountId() == null || transactionDTO.getAmount() == null) {
                return ResponseEntity.badRequest().body("Missing required fields in the transactionDTO body");
            }
            Transaction t = transactionService.addTransaction(transactionDTO, Category.CatType.income);
            return ResponseEntity.ok("Revenue added successfully");
        } catch (Exception e) {
            logger.warning("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
