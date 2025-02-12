package com.example.ExpenseManagementApp.Controllers;

import com.example.ExpenseManagementApp.DTO.RecurringTransactionDTO;
import com.example.ExpenseManagementApp.Services.RecurringTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZoneId;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/v1/recurring")
public class RecurringTransactionController {
    private final RecurringTransactionService recurringTransactionService;
    private final Logger logger = Logger.getLogger(RecurringTransactionController.class.getName());
    @Autowired
    public RecurringTransactionController(RecurringTransactionService recurringTransactionService){
        this.recurringTransactionService = recurringTransactionService;
    }

    @GetMapping
    public ResponseEntity<List<RecurringTransactionDTO>> getRecurringTransactions(@RequestParam Long accountId){
        try {
            return ResponseEntity.ok(recurringTransactionService.getRecurringTransactions(accountId, ZoneId.of("Asia/Kolkata")));
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }




}
