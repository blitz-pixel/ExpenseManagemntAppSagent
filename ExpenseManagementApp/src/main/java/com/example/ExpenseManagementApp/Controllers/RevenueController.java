package com.example.ExpenseManagementApp.Controllers;

import com.example.ExpenseManagementApp.DTO.TransactionDTO;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Services.RecurringTransactionService;
import com.example.ExpenseManagementApp.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import sun.util.logging.PlatformLogger;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/revenue")
public class RevenueController {
    Logger logger = Logger.getLogger(getClass().getName());


    private TransactionService revenueService;
    private RecurringTransactionService recurringTransactionService;


    @Autowired
    public void setTransactionService(TransactionService transactionService, RecurringTransactionService recurringTransactionService) {
        this.revenueService = transactionService;
        this.recurringTransactionService = recurringTransactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllRevenues(@RequestParam Long accountId) {
        try {
            return ResponseEntity.ok(revenueService.getTransactions(accountId, Category.CatType.income));
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
            Transaction t = revenueService.addTransaction(transactionDTO, Category.CatType.income);
            if (Boolean.TRUE.equals(transactionDTO.getRecurring())) {
                recurringTransactionService.createRecurringTransaction(t, transactionDTO);
            }
            return ResponseEntity.ok("Revenue added successfully");
        } catch (Exception e) {
            logger.warning("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error adding revenue: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTransaction(@RequestParam String uuid) {
        try {
            revenueService.deleteTransaction(uuid);
            return ResponseEntity.ok("Transaction deleted successfully");
        } catch (Exception e) {
            logger.severe("Error deleting transaction: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error deleting transaction: " + e.getMessage());
        }
    }


}
