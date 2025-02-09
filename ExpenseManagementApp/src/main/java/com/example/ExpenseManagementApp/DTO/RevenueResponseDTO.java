package com.example.ExpenseManagementApp.DTO;

import com.example.ExpenseManagementApp.Model.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class RevenueResponseDTO {
    private Long accountId;
    private String description;
    private Date date;
    private String parentCategoryName;
    private String subCategoryName;
    private BigDecimal amount; // Changed from Double to BigDecimal

    public RevenueResponseDTO(Transaction transaction) {
        this.accountId = transaction.getAccount().
                getAccount_id();
        this.description = transaction.getDescription();
        this.date = Date.from(transaction.getDate());
        this.amount = transaction.getAmount(); // Directly assign BigDecimal

        if (transaction.getCategory().getParent() != null) {
            this.subCategoryName = transaction.getCategory().getName();
            this.parentCategoryName = transaction.getCategory().getParent().getName();
        } else {
            this.parentCategoryName = transaction.getCategory().getName();
            this.subCategoryName = "";
        }
    }

    // Getters and Setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public void setParentCategoryName(String categoryName) {
        this.parentCategoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
