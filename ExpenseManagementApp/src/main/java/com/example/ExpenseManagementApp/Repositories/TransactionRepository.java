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
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAndAmountGreaterThan(Account account, BigDecimal amount);


    @Query("SELECT t FROM Transaction t WHERE t.date BETWEEN :startDate AND :endDate")
    List<Transaction> findTransactionsBetweenDates(Instant startDate, Instant endDate);

    @Query(value = "SELECT EXTRACT(YEAR FROM t.date) AS year, " +
            "       EXTRACT(WEEK FROM t.date) AS week, " +
            "       COALESCE(SUM(t.amount), 0) AS totalAmount," + " t.type as Type " +
            "FROM Transaction t " +
            " WHERE t.account_id = :accountId " + // Correct placeholder for type
            "GROUP BY EXTRACT(YEAR FROM t.date), EXTRACT(WEEK FROM t.date), t.type " +
            "ORDER BY year, week", nativeQuery = true)
    List<Object[]> getTotalExpensesGroupedByWeekForAccount(@Param("accountId") Long accountId);


    @Query(value = "SELECT EXTRACT(YEAR FROM t.date) AS year, " +
            "       EXTRACT(MONTH FROM t.date) AS month, " +
            "       COALESCE(SUM(t.amount), 0) AS totalAmount, " +
            "       t.type as Type " +
            "FROM Transaction t " +
            "WHERE t.account_id = :accountId AND NOT t.is_deleted " +
            "GROUP BY EXTRACT(YEAR FROM t.date), EXTRACT(MONTH FROM t.date), t.type " +
            "ORDER BY year, month", nativeQuery = true)
    List<Object[]> getTotalExpensesGroupedByMonthForAccount(@Param("accountId") Long accountId);



    @Query(value = "SELECT EXTRACT(YEAR FROM t.date) AS year, " +
            "       COALESCE(SUM(t.amount), 0) AS totalAmount ," + " t.type as Type " +
            "FROM Transaction t " +
            " WHERE t.account_id = :accountId " +
            "GROUP BY EXTRACT(YEAR FROM t.date), t.type " +
            "ORDER BY year", nativeQuery = true)
    List<Object[]> getTotalExpensesGroupedByYearForAccount(@Param("accountId") Long accountId);

    @Query(value = "SELECT " +
            "    c_parent.name AS parent_category_name, " +
            "    c_sub.name AS subcategory_name, " +
             "c_sub.type as Type, " +
            "    EXTRACT(YEAR FROM t.date) AS year, " +
            "    EXTRACT(MONTH FROM t.date) AS month, " +
            "    COALESCE(SUM(t.amount), 0) AS total_spent " +
            "FROM category c_parent " +
            "JOIN category c_sub ON c_parent.category_id = c_sub.parent_id " +
            "LEFT JOIN transaction t ON c_sub.category_id = t.category_id " +
            "AND (t.account_id = :accountId AND NOT t.is_deleted) " +
            "GROUP BY c_parent.category_id, c_parent.name, " +
            "         c_sub.category_id, c_sub.name, " +
            "         EXTRACT(YEAR FROM t.date), EXTRACT(MONTH FROM t.date) " +
            "ORDER BY c_parent.name, c_sub.name, year, month,c_sub.type",
            nativeQuery = true)
    List<Object[]> getCategoryWiseTransactionMonthly(@Param("accountId") Long accountId);

    @Query(value = "SELECT " +
            "    c_parent.name AS parent_category_name, " +
            "    c_sub.name AS subcategory_name, " +
            "    c_sub.type AS Type, " +
            "    EXTRACT(YEAR FROM t.date) AS year, " +
            "    COALESCE(SUM(t.amount), 0) AS total_spent " +
            "FROM category c_parent " +
            "JOIN category c_sub ON c_parent.category_id = c_sub.parent_id " +
            "LEFT JOIN transaction t ON c_sub.category_id = t.category_id " +
            "AND (t.account_id = :accountId AND NOT t.is_deleted) " +
            "GROUP BY c_parent.category_id, c_parent.name, " +
            "         c_sub.category_id, c_sub.name, c_sub.type, " +
            "         EXTRACT(YEAR FROM t.date) " +
            "ORDER BY c_parent.name, c_sub.name, year, c_sub.type",
            nativeQuery = true)
    List<Object[]> getCategoryWiseTransactionYearly(@Param("accountId") Long accountId);

    @Query(value = "SELECT " +
            "    c_parent.name AS parent_category_name, " +
            "    c_sub.name AS subcategory_name, " +
            "    c_sub.type AS Type, " +
            "    EXTRACT(YEAR FROM t.date) AS year, " +
            "    EXTRACT(WEEK FROM t.date) AS week, " +
            "    COALESCE(SUM(t.amount), 0) AS total_spent " +
            "FROM category c_parent " +
            "JOIN category c_sub ON c_parent.category_id = c_sub.parent_id " +
            "LEFT JOIN transaction t ON c_sub.category_id = t.category_id " +
            "AND (t.account_id = :accountId AND NOT t.is_deleted) " +
            "GROUP BY c_parent.category_id, c_parent.name, " +
            "         c_sub.category_id, c_sub.name, c_sub.type, " +
            "         EXTRACT(YEAR FROM t.date), EXTRACT(WEEK FROM t.date) " +
            "ORDER BY c_parent.name, c_sub.name, year, week, c_sub.type",
            nativeQuery = true)
    List<Object[]> getCategoryWiseTransactionWeekly(@Param("accountId") Long accountId);









    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = 'REVENUE' AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal getTotalRevenuesBetweenDates(Instant startDate, Instant endDate);

    @Query("SELECT t.category.name, COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = 'EXPENSE' AND t.date BETWEEN :startDate AND :endDate GROUP BY t.category.name ORDER BY SUM(t.amount) DESC")
    List<Object[]> getCategoryWiseExpenses(Instant startDate, Instant endDate);
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