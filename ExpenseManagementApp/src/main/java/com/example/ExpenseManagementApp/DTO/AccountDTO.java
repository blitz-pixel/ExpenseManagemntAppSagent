package com.example.ExpenseManagementApp.DTO;

import com.example.ExpenseManagementApp.Model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountDTO {
    private Long accountId;
    @JsonProperty("accountName")
    private String accountName;
    @JsonProperty("accountType")
    private Account.AccountType accountType;
//    private String userName;

    public AccountDTO(Account account) {
        this.accountId = account.getAccountId();
        this.accountName = account.getAccountName();
        this.accountType = account.getType();
//        this.userName = account.getUser_Foriegn_id().getUserName();
    }

    public  AccountDTO(){
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Account.AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(Account.AccountType accountType) {
        this.accountType = accountType;
    }
}
