package com.example.ExpenseManagementApp.Services;


import com.example.ExpenseManagementApp.DTO.ExpenseRequestDTO;
import com.example.ExpenseManagementApp.DTO.ExpenseResponseDTO;
import com.example.ExpenseManagementApp.DTO.RevenueResponseDTO;
import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Repositories.AccountRepository;
import com.example.ExpenseManagementApp.Repositories.CategoryRepository;
import com.example.ExpenseManagementApp.Repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionService {
    Logger logger = Logger.getLogger(TransactionService.class.getName());

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;  // Inject AccountRepository
    private final CategoryRepository categoryRepository;  // Inject CategoryRepository

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;  // Assigning to the instance variable
        this.categoryRepository = categoryRepository;  // Assigning to the instance variable
    }

    public List<ExpenseResponseDTO> getExpenseTransactions(Long accountId) {
        List<Transaction> ExpenseList = transactionRepository.findAllByTypeAndAccount_Id(Category.CatType.expense, accountId);
//        ExpenseList.forEach(transaction -> Hibernate.initialize(transaction.getCategory()));
//        ExpenseList.forEach(transaction -> Hibernate.initialize(transaction.getAccount()));
        List<ExpenseResponseDTO> ExpenseResponseDTOs = ExpenseList.stream()
                .map(ExpenseResponseDTO::new)
                .toList();
        logger.info(ExpenseList.toString());
        return ExpenseResponseDTOs;
    }


    // For testing return type is Transaction
    @Transactional
    public Transaction addExpenseTransaction(ExpenseRequestDTO expenseDTO) {
        if (expenseDTO.getAccount_id() == null) throw new RuntimeException("Account id is required");
        Account account = accountRepository.findById(expenseDTO.getAccount_id()).orElseThrow(
                () -> new IllegalArgumentException("Account not found"));
        Category category = categoryRepository.findByName(expenseDTO.getCategoryName()).orElseThrow(
                () -> new IllegalArgumentException("Category not found"));
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType(Category.CatType.expense);
        transaction.setCategory(category);
        transaction.setAmount(expenseDTO.getAmount());
        transaction.setDate(Instant.now());
        if ( expenseDTO.getSubCategoryName() != null && !(expenseDTO.getSubCategoryName().isEmpty()) ) {
            Category subCategory = categoryRepository.findByParent(expenseDTO.getSubCategoryName(),category.getId()).orElseThrow(
                    () -> new IllegalArgumentException("SubCategory not found"));
            transaction.setCategory(subCategory);
        }
        if (expenseDTO.getDescription() != null && !(expenseDTO.getDescription().isEmpty())) transaction.setDescription(expenseDTO.getDescription());
        return  transactionRepository.save(transaction);
    }

    // Method to add revenue
    public Transaction addRevenue(Long accountId, String description, BigDecimal amount, Long categoryId) {
        Transaction transaction = new Transaction();
        transaction.setAccount(accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found")));
        transaction.setDescription(description);
        transaction.setAmount(amount);  // Directly using BigDecimal
        transaction.setType(Category.CatType.income);
        transaction.setCategory(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found")));
        transaction.setDate(Instant.now());

        return transactionRepository.save(transaction);
    }


    public List<RevenueResponseDTO> getRevenueTransactions(Long accountId) {
        List<Transaction> ExpenseList = transactionRepository.findAllByTypeAndAccount_Id(Category.CatType.income, accountId);
//        ExpenseList.forEach(transaction -> Hibernate.initialize(transaction.getCategory()));
//        ExpenseList.forEach(transaction -> Hibernate.initialize(transaction.getAccount()));
        List<RevenueResponseDTO> RevenueResponseDTOs = ExpenseList.stream()
                .map(RevenueResponseDTO::new)
                .toList();
        logger.info(ExpenseList.toString());
        return RevenueResponseDTOs;
    }

}

