package com.example.ExpenseManagementApp.Repositories;

import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    @Query("SELECT t FROM Transaction t WHERE t.type = ?1 AND t.account.account_id = ?2")
    List<Transaction> findByAccountAndAmountGreaterThan(Account account, BigDecimal amount);

    List<Transaction> findAllByTypeAndAccount_Id(Category.CatType type, Long accountId);

}

