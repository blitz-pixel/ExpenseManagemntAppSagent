package com.example.ExpenseManagementApp.Services;

import com.example.ExpenseManagementApp.DTO.AccountDTO;
import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Repositories.AccountRepository;
import com.example.ExpenseManagementApp.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }


    public List<AccountDTO> getAccountsPersonal(Long user_id) {
        List<Account>  accounts= accountRepository.findAccountByUser_id(user_id);
        return accounts.stream().map(AccountDTO::new).toList();
    }


    // Before implementing the concept of shared accounts
    public Long getUserId(Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getUser_Foriegn_id().getUser_id();
    }

    public  User getUser(Account account){
        return account.getUser_Foriegn_id();
    }

    public  User getUser(Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getUser_Foriegn_id();
    }


    @Transactional
    public void addAccount(Long accountId,AccountDTO accountDTO){
        User user =  getUser(accountId);
        List<Account> accounts = accountRepository.findAccountByUser_id(user.getUser_id());
        if (accounts.size() >= 5){
            throw new IllegalArgumentException("You can only have 5 accounts");
        }
        for (Account account : accounts){
            if (account.getType().equals(accountDTO.getAccountType()) && account.getAccountName().equals(accountDTO.getAccountName())){
                throw new IllegalArgumentException("Account already exists");
            }
        }
        Account account = new Account();
        account.setAccountName(accountDTO.getAccountName());
        account.setType(accountDTO.getAccountType());
        account.setUser_Foriegn_id(user);
        account.setCreatedAt(Instant.now());
        accountRepository.save(account);

    }


}