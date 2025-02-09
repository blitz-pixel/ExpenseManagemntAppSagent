package com.example.ExpenseManagementApp.Controllers;

import com.example.ExpenseManagementApp.DTO.RevenueRequest;
import com.example.ExpenseManagementApp.DTO.RevenueResponseDTO;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/revenue")
public class RevenueController {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final TransactionService transactionService;

    @Autowired
    public RevenueController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<RevenueResponseDTO>> getAllRevenues(@RequestParam Long accountId) {
        try {
            List<RevenueResponseDTO> revenues = transactionService.getRevenueTransactions(accountId);
            return ResponseEntity.ok(revenues);
        } catch (Exception e) {
            logger.warning("Error fetching revenues: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<RevenueResponseDTO> addRevenue(@RequestBody RevenueRequest request) {
        try {
            if (request == null || request.getAccountId() == null || request.getAmount() == null || request.getCategoryId() == null) {
                logger.warning("Invalid request body");
                return ResponseEntity.badRequest().body(null);
            }

            logger.info("Received Request: Account ID = " + request.getAccountId() +
                    ", Description = " + request.getDescription() +
                    ", Amount = " + request.getAmount() +
                    ", Category ID = " + request.getCategoryId());

            Transaction transaction = transactionService.addRevenue(
                    request.getAccountId(),
                    request.getDescription(),
                    request.getAmount(),
                    request.getCategoryId()
            );

            RevenueResponseDTO response = new RevenueResponseDTO(transaction);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe("Error adding revenue: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
