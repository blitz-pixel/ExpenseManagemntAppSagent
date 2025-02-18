package com.example.ExpenseManagementApp.Repositories;

import com.example.ExpenseManagementApp.Model.Recurringtransaction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<Recurringtransaction, Long> {
    @Query(
            value = "SELECT * FROM exampleconnectiondb.Recurringtransaction rt " +
                    "JOIN exampleconnectiondb.transaction t ON rt.recurring_id = t.transaction_id " +
                    "WHERE t.account_id = ?1",
            nativeQuery = true
    )
    List<Recurringtransaction> findRecurringTransactionById(Long accountId);

    @Query("SELECT rt FROM Recurringtransaction rt WHERE rt.transaction.account.id = ?1 AND NOT rt.transaction.isDeleted" )
    List<Recurringtransaction> findRTransactionsByTransactionId(Long accountId);


    Optional<Recurringtransaction> findByTransaction_Uuid(@Size(max = 40) @NotNull String uuid);
}
