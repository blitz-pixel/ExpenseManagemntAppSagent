package com.example.ExpenseManagementApp.Services;

import com.example.ExpenseManagementApp.Model.Budget;
import com.example.ExpenseManagementApp.Repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    /**
     * Save a new budget and calculate its end date based on frequency.
     */
    public Budget createBudget(Budget budget) {
        // Calculate the end date based on the frequency
        budget.calculateEndDate();

        // Save the budget to the database
        return budgetRepository.save(budget);
    }

    /**
     * Scheduled task to remove expired budgets.
     */
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void removeExpiredBudgets() {
        LocalDate today = LocalDate.now();

        // Find all budgets where the end date is before today
        List<Budget> expiredBudgets = budgetRepository.findByBudgetEndDateBefore(today);

        // Delete expired budgets
        budgetRepository.deleteAll(expiredBudgets);

        System.out.println("Removed " + expiredBudgets.size() + " expired budgets.");
    }
}