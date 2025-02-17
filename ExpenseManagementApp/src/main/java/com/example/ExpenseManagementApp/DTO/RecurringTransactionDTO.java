package com.example.ExpenseManagementApp.DTO;

import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Recurringtransaction;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.parameters.P;

import javax.management.Descriptor;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

public class RecurringTransactionDTO{
    private BigDecimal amount;
    private LocalDate currentDate;
    private Category.CatType type;

    @JsonProperty("Description")
    private String description;
    @JsonProperty("ParentCategoryName")
    private String parentCategoryName;
    @JsonProperty("SubCategoryName")
    private String subCategoryName;
    private LocalDate nextDate;
    private Boolean is_active;

    public RecurringTransactionDTO(Recurringtransaction recurringTransaction) {
        Transaction transaction = recurringTransaction.getTransaction();

        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.is_active = recurringTransaction.getActive();

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


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public Category.CatType getType() {
        return type;
    }

    public void setType(Category.CatType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public LocalDate getNextDate() {
        return nextDate;
    }

    public void setNextDate(LocalDate nextDate) {
        this.nextDate = nextDate;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    //    private
}
