package com.example.ExpenseManagementApp.Repositories;

import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.User_Foriegn_id.user_id= ?1")
    List<Account> findAccountByUser_id(Long id);

    @Query("SELECT a FROM Account a WHERE a.User_Foriegn_id.user_id= ?1")
    Optional<Account> findByUser_Foriegn_id(Long user_id);
}