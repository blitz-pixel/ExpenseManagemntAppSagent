package com.example.ExpenseManagementApp.Services;

import com.example.ExpenseManagementApp.Model.Budget;
import com.example.ExpenseManagementApp.Repositories.BudgetRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private static final Logger logger = Logger.getLogger(BudgetService.class.getName());

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    /**
     * Create a new budget.
     */
    public Budget createBudget(Budget budget) {
        budget.calculateEndDate();
        return budgetRepository.save(budget);
    }

    /**
     * Get all budgets.
     */
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    /**
     * Get a budget by ID.
     */
    public Optional<Budget> getBudgetById(Long id) {
        return budgetRepository.findById(id);
    }

    /**
     * Update a budget.
     */
    public Optional<Budget> updateBudget(Long id, Budget budgetDetails) {
        return budgetRepository.findById(id).map(existingBudget -> {
            // Account should not be changed here unless absolutely necessary
            existingBudget.setBudgetName(budgetDetails.getBudgetName());
            existingBudget.setAmount(budgetDetails.getAmount());
            existingBudget.setBudgetStartDate(budgetDetails.getBudgetStartDate());
            existingBudget.setBudgetFrequency(budgetDetails.getBudgetFrequency());
            existingBudget.calculateEndDate();

            return budgetRepository.save(existingBudget);
        });
    }

    /**
     * Delete a budget.
     */
    public boolean deleteBudget(Long id) {
        if (budgetRepository.existsById(id)) {
            budgetRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Remove expired budgets.
     */
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void removeExpiredBudgets() {
        LocalDate today = LocalDate.now();
        List<Budget> expiredBudgets = budgetRepository.findByBudgetEndDateBefore(today);
        budgetRepository.deleteAll(expiredBudgets);
        logger.info("Removed " + expiredBudgets.size() + " expired budgets.");
    }
}
