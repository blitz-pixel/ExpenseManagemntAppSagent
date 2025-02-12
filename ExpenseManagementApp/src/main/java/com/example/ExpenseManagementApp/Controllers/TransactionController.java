//package com.example.ExpenseManagementApp.Controllers;
//
//import com.example.ExpenseManagementApp.DTO.TransactionDTO;
//import com.example.ExpenseManagementApp.Model.Category;
//import com.example.ExpenseManagementApp.Model.Transaction;
//import com.example.ExpenseManagementApp.Services.TransactionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.logging.Logger;
//
//@RestController
//@RequestMapping("/api/v1/transactions")
//public class TransactionController {
//
//    private final TransactionService transactionService;
//    Logger logger = Logger.getLogger(TransactionController.class.getName());
//
//    @Autowired
//    public TransactionController(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<TransactionDTO>> getTransactions(@RequestParam Long accountId, @RequestParam Category.CatType type) {
//        try {
//            List<TransactionDTO> transactions = transactionService.getTransactions(accountId, type);
//            return ResponseEntity.ok(transactions);
//        } catch (Exception e) {
//            logger.severe("Error fetching transactions: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionDTO transactionDTO, @RequestParam Category.CatType type) {
//        try {
//            Transaction createdTransaction = transactionService.addTransaction(transactionDTO, type);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
//        } catch (RuntimeException e) {
//            logger.severe("Error adding transaction: " + e.getMessage());
//            return ResponseEntity.badRequest().build();
//        } catch (Exception e) {
//            logger.severe("Unexpected error adding transaction: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//
//    @PutMapping("/{transactionId}")
//    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId, @RequestBody TransactionDTO transactionDTO, @RequestParam Category.CatType type) {
//        try {
//            Transaction updatedTransaction = transactionService.updateTransaction(transactionId, transactionDTO, type);
//            return ResponseEntity.ok(updatedTransaction);
//        } catch (IllegalArgumentException e) {
//            logger.warning("Invalid transaction update request: " + e.getMessage());
//            return ResponseEntity.badRequest().build();
//        } catch (Exception e) {
//            logger.severe("Error updating transaction: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @GetMapping("/{transactionId}")
//    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long transactionId) {
//        try {
//            TransactionDTO transaction = transactionService.getTransactionById(transactionId);
//            return ResponseEntity.ok(transaction);
//        } catch (IllegalArgumentException e) {
//            logger.warning("Transaction not found: " + e.getMessage());
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            logger.severe("Error fetching transaction: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}
//
