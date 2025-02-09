package com.example.ExpenseManagementApp.DTO;

import java.math.BigDecimal;
import java.time.Instant;

public class ExpenseRequestDTO {
    private Long accountId;
    private String description;
    private BigDecimal amount;
    private String categoryName;
    private String subCategoryName;
    private Instant date;
    private Long categoryId;

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }


    // For testing
    public ExpenseRequestDTO(Long account_id, String description, Instant date, String categoryName, String subCategoryName, BigDecimal amount) {
        this.accountId = account_id;
        this.description = description;
        this.date = date;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.amount = amount;
    }

    public ExpenseRequestDTO(){
    }

    // Getters and Setters
    public Long getAccount_id() {
        return accountId;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getCategoryId() {
        return categoryId;

    }
}
