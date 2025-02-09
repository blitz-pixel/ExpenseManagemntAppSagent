package com.example.ExpenseManagementApp.Controllers;

import com.example.ExpenseManagementApp.Model.Budget;
import com.example.ExpenseManagementApp.Services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    /**
     * Create a new budget.
     */
    @PostMapping("/create")
    public Budget createBudget(@RequestBody Budget budget) {
        return budgetService.createBudget(budget);
    }
}