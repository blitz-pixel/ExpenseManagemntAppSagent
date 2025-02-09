package com.example.ExpenseManagementApp.DTO;

import com.example.ExpenseManagementApp.Model.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionDTO {
    @JsonProperty("accountId")
    private Long accountId;
    @Nullable
    private String uuid;
    private String description;
    private Instant date;

    @JsonProperty("ParentCategoryName")
    private String parentCategoryName;
    @JsonProperty("SubCategoryName")
    private String subCategoryName;
    private BigDecimal amount;

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }


    // For testing
//    public ExpenseRequestDTO(Long account_id, String description, Instant date, String ParentcategoryName, String subCategoryName, BigDecimal amount) {
//        this.accountId = account_id;
//        this.description = description;
//        this.date = date;
//        this.ParentcategoryName = ParentcategoryName;
//        this.subCategoryName = subCategoryName;
//        this.amount = amount;
//    }

    // For Response
    public TransactionDTO(Transaction transaction) {
        this.accountId = transaction.getAccount().getAccountId();
        this.uuid = transaction.getUuid();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        if (transaction.getCategory().getParent() != null) {
            this.subCategoryName = transaction.getCategory().getName();
            this.parentCategoryName = transaction.getCategory().getParent().getName();
        } else {
            this.parentCategoryName = transaction.getCategory().getName();
            this.subCategoryName = "";
        }
    }
    public TransactionDTO(){
    }

    // Getters and Setters
    public Long getAccount_id() {
        return accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAccount_id(Long account_id) {
        this.accountId = account_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Nullable
    public String getUuid() {
        return uuid;
    }
}
