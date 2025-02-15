package com.example.ExpenseManagementApp.Repositories;

import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAndAmountGreaterThan(Account account, BigDecimal amount);


    @Query("SELECT t FROM Transaction t WHERE t.type = ?1 AND t.account.id = ?2 AND t.isDeleted = false")
    List<Transaction> findAllByTypeAndAccountId(Category.CatType type, Long accountId);

    Optional<Transaction> findByUuid(@Size(max = 40) @NotNull String uuid);

//    Optional<List<Transaction>> findByCategoryId(Long categoryId);


//    Optional<List<Transaction>> findByCategoryId(Long categoryId);

    @Query(value = "SELECT * FROM Transaction t WHERE t.category_id = ?1",nativeQuery = true)
    Optional<List<Transaction>> findByCategoryId(Long categoryId);


    List<Transaction> findAllByAccount_Id(Long accountId);


//        @Modifying
//        @Transactional
//        @Query("UPDATE Transaction t SET t.isDeleted = true WHERE t.id = :transactionId")
//        void batchDelete(@Param("transactionIds") List<Long> transactionIds);


    @Modifying
    @Transactional
    @Query("UPDATE Transaction t SET t.isDeleted = true WHERE t  = ?1")
    void OneDelete(Transaction transaction);

    @Modifying
    @Query("UPDATE Transaction t SET t.category = NULL WHERE t.category.id = :categoryId")
    void updateCategoryToNullByCategory(@Param("categoryId") Long categoryId);

    @Modifying
    @Query("UPDATE Transaction t SET t.isDeleted = TRUE WHERE t.category.id = :categoryId")
    void markTransactionsAsDeletedByCategory(@Param("categoryId") Long categoryId);


    @Modifying
    @Query("UPDATE Transaction t SET t.isDeleted = TRUE WHERE t.category.id IN :categoryIds")
    void markTransactionsAsDeletedBySubCategory(@Param("categoryIds")List<Long> subCategoryIds);

    Optional<List<Transaction>> findByCategory(Category category);

    @Modifying
    @Query("UPDATE Transaction t SET t.isDeleted = TRUE WHERE t.category IS NULL")
    void SoftDeleteTransactions();


//    @Query("SELECT t FROM Transaction t JOIN FETCH t.category WHERE t.transactionId = :id")
//    Transaction findTransactionWithCategory(@Param("id") Long id);







//    Optional<Transaction> findById( Long id);
}