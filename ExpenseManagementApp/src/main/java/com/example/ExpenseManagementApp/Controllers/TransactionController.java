package com.example.ExpenseManagementApp.Controllers;

import com.example.ExpenseManagementApp.DTO.TransactionDTO;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Services.RecurringTransactionService;
import com.example.ExpenseManagementApp.Services.TransactionService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final RecurringTransactionService recurringTransactionService;
    Logger logger = Logger.getLogger(TransactionController.class.getName());

    @Autowired
    public TransactionController(TransactionService transactionService, RecurringTransactionService recurringTransactionService) {
        this.transactionService = transactionService;
        this.recurringTransactionService = recurringTransactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getTransactions(@RequestParam Long accountId, @RequestParam Category.CatType type) {
        try {
            List<TransactionDTO> transactions = transactionService.getTransactions(accountId, type);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.severe("Error fetching transactions: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(@RequestParam Long accountId) {
        try {
            List<TransactionDTO> transactions = transactionService.getRecentTransactions(accountId);
            logger.info("Transactions: " + transactions);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.severe("Error fetching transactions: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/recurring/toggle")
    public ResponseEntity<String> toggleTransaction(@RequestParam String uuid) {
        try {
            recurringTransactionService.ActivateDeactivateRecurringTransaction(uuid);

            return ResponseEntity.ok("Transaction toggled successfully");
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
            logger.severe("Error toggling transaction: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }



}

