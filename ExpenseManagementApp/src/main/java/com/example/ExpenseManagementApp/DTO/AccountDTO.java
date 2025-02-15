package com.example.ExpenseManagementApp.DTO;

import com.example.ExpenseManagementApp.Model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountDTO {
    @JsonProperty("accountName")
    private String accountName;
    @JsonProperty("accountType")
    private Account.AccountType accountType;
//    private String userName;

    public AccountDTO(Account account) {
        this.accountName = account.getAccountName();
        this.accountType = account.getType();
//        this.userName = account.getUser_Foriegn_id().getUserName();
    }
}
