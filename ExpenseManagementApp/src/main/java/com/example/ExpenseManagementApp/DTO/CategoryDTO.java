package com.example.ExpenseManagementApp.DTO;

import com.example.ExpenseManagementApp.Model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;

public class CategoryDTO {

    @Nullable
    private Long accountId;
    @Nullable
    private Long userId;
    @Nullable
    @JsonProperty("SubCategoryName")
    private String subCategoryName;
    @JsonProperty("ParentCategoryName")
    private String parentCategoryName;
    private Category.CatType type;

    public CategoryDTO(@Nullable Long accountId, @Nullable Long userId, @Nullable String SubCategoryName, @Nullable String ParentCategoryName, Category.CatType type) {
        this.accountId = accountId;
        this.userId = userId;
        this.subCategoryName = SubCategoryName;
        this.parentCategoryName = ParentCategoryName;
        this.type = type;
    }

    public CategoryDTO(){
    }

    public CategoryDTO(Category category){
        if (category.getAccount() != null)this.accountId = category.getAccount().getAccountId();
        if (category.getUser() != null)this.userId = category.getUser().getUser_id();
        if(category.getParent() != null){
            this.subCategoryName = category.getName();
            this.parentCategoryName = category.getParent().getName();
        }else{
            this.parentCategoryName = category.getName();
            this.subCategoryName = "";
        }
        this.type = category.getType();
    }

    @Nullable
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(@Nullable Long accountId) {
        this.accountId = accountId;
    }

    @Nullable
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@Nullable Long userId) {
        this.userId = userId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    @Nullable
    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(@Nullable String parentId) {
        this.parentCategoryName = parentId;
    }

    public Category.CatType getType() {
        return type;
    }

    public void setType(Category.CatType type) {
        this.type = type;
    }
}