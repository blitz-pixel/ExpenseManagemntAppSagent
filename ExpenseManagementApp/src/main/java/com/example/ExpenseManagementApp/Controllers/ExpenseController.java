package com.example.ExpenseManagementApp.Controllers;

import com.example.ExpenseManagementApp.DTO.TransactionDTO;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Services.TransactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    Logger logger = Logger.getLogger(ExpenseController.class.getName()); // Create a logger instance
    private final TransactionService expenseService;


    public ExpenseController(TransactionService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getExpensesByAccount(@RequestParam Long accountId){
        try {
//            List<Transaction> TransactionList = expenseService.getExpenseTransactions(accountId);
//            logger.info(TransactionList.toString());
//            for (Transaction transaction : TransactionList) {
//                logger.info(transaction.toString());
//            }
            return ResponseEntity.ok(expenseService.getTransactions(accountId, Category.CatType.expense));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addExpenseTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {
            System.out.print(transactionDTO);
            Transaction t = expenseService.addTransaction(transactionDTO, Category.CatType.expense);
            logger.info(t.getId().toString());
            return ResponseEntity.ok("Expense added successfully");
        } catch (Exception e) {
            System.out.print(e);
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}