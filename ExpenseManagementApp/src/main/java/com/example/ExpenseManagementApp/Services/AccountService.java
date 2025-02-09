package com.example.ExpenseManagementApp.Services;

import com.example.ExpenseManagementApp.DTO.AccountDTO;
import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Repositories.AccountRepository;
import com.example.ExpenseManagementApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public List<AccountDTO> getAccountsByUsername(Long user_id) {
//        User user = userRepository.findByUserName(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
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


}