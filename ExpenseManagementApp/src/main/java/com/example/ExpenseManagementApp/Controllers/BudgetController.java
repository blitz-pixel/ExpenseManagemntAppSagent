package com.example.ExpenseManagementApp.Controllers;

import com.example.ExpenseManagementApp.Model.Budget;
import com.example.ExpenseManagementApp.Services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    /**
     * Create a new budget.
     */
    @PostMapping("/create")
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        return ResponseEntity.ok(budgetService.createBudget(budget));
    }

    /**
     * Get all budgets.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Budget>> getAllBudgets() {
        return ResponseEntity.ok(budgetService.getAllBudgets());
    }

    /**
     * Get a budget by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        Optional<Budget> budget = budgetService.getBudgetById(id);
        return budget.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update an existing budget.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget budgetDetails) {
        return budgetService.updateBudget(id, budgetDetails)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Delete a budget.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBudget(@PathVariable Long id) {
        boolean deleted = budgetService.deleteBudget(id);
        if (deleted) {
            return ResponseEntity.ok("Budget deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
