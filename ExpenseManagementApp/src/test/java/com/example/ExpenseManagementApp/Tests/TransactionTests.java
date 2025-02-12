package com.example.ExpenseManagementApp.Tests;

import com.example.ExpenseManagementApp.DTO.TransactionDTO;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Repositories.TransactionRepository;
import com.example.ExpenseManagementApp.Services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;



    @Test
    public void testThatCreatesExpense(){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount_id(25L);
        transactionDTO.setAmount(BigDecimal.valueOf(1000L));
        transactionDTO.setParentCategoryName("Food");
        transactionDTO.setDescription("Lunch");
        Transaction transaction = transactionService.addTransaction(transactionDTO, Category.CatType.expense);

        assertNotNull(transaction, "Category should be saved");
    }

    @Test
    public void testThatCreatesSubCategory(){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount_id(22L);
        transactionDTO.setParentCategoryName("Food");
        transactionDTO.setSubCategoryName("Burger");
        transactionDTO.setAmount(BigDecimal.valueOf(1000L));
        transactionDTO.setDate(Instant.now());
        transactionService.addTransaction(transactionDTO, Category.CatType.expense);
    }

    @Test
    public void testThatCreatesIncome(){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccount_id(22L);
        transactionDTO.setAmount(BigDecimal.valueOf(1000L));
        transactionDTO.setParentCategoryName("abcabc");
//        transactionDTO.setDescription("Salary");
        Transaction transaction = transactionService.addTransaction(transactionDTO, Category.CatType.income);

        assertNotNull(transaction, "Category should be saved");
    }

    @Test
    public  void testThatGetsRevenueTransactions(){
        List<TransactionDTO> RevenueTransactions = transactionService.getTransactions(22L, Category.CatType.income);

        assertNotNull(RevenueTransactions, "Revenue Transactions should not be null when they exist.");
//        assertEquals("2025-02-04 06:29:30 ", RevenueTransactions.get(1).getDate().toString());
    }

    @Test
    public void testThatAllRepositoryFunctionsWork(){
        List<Transaction>  transactionDTOS= transactionRepository.findAllByTypeAndAccountId(Category.CatType.income, 22L);
        assertNotNull(transactionDTOS, "Transactions should not be null when they exist.");
    }

    @Test
    public void testThatDeletesTransaction(){
        transactionService.deleteTransaction( " 1ecf132f-2d88-4f27-8bb1-760ae23f9013");

        assertTrue(transactionRepository.findByUuid(" 1ecf132f-2d88-4f27-8bb1-760ae23f9013").isEmpty());
    }
}
