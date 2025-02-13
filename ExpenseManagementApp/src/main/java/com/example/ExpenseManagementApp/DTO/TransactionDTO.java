package com.example.ExpenseManagementApp.DTO;

import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Recurringtransaction;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public class TransactionDTO {
    @JsonProperty("accountId")
    private Long accountId;
    @Nullable
    private String uuid;
    private String description;
    private Instant date;
    @JsonProperty("ParentCategoryName")
    protected String parentCategoryName;
    @JsonProperty("SubCategoryName")
    protected String subCategoryName;
    protected BigDecimal amount;
//    private Boolean isDeleted;
    @Nullable
    private Boolean isRecurring;
    @Nullable
    private Recurringtransaction.RFrequency frequency;



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
//        this.isDeleted = transaction.getDeleted();
        Category category = transaction.getCategory();
        if (category != null) {
            this.parentCategoryName = Optional.ofNullable(category.getParent())
                    .map(Category::getName)
                    .orElse(category.getName());

            this.subCategoryName = (category.getParent() != null) ? category.getName() : "";
        } else {
            this.parentCategoryName = "";
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

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

//    public Boolean getDeleted() {
//        return isDeleted;
//    }
//
//    public void setDeleted(Boolean deleted) {
//        isDeleted = deleted;
//    }

    @Nullable
    public String getUuid() {
        return uuid;
    }

    public Boolean getRecurring() {
        return isRecurring;
    }

    public void setRecurring(Boolean recurring) {
        isRecurring = recurring;
    }

    public void setUuid(@Nullable String uuid) {
        this.uuid = uuid;
    }

    public Recurringtransaction.RFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Recurringtransaction.RFrequency frequency) {
        this.frequency = frequency;
    }
}
