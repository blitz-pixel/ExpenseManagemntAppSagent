package com.example.ExpenseManagementApp.Repositories;

import com.example.ExpenseManagementApp.Model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    // Custom query to find budgets with an end date before the given date
    List<Budget> findByBudgetEndDateBefore(LocalDate date);
}