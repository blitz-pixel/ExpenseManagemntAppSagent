package com.example.ExpenseManagementApp.Repositories;

import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAndAmountGreaterThan(Account account, BigDecimal amount);

    List<Transaction> findAllByTypeAndAccountId(Category.CatType type, Long accountId);


    Optional<Transaction> findById(Long id);
}