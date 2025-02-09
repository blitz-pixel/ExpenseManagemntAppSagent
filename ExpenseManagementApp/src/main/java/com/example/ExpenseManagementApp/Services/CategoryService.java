package com.example.ExpenseManagementApp.Services;


import com.example.ExpenseManagementApp.DTO.CategoryDTO;
import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Repositories.AccountRepository;
import com.example.ExpenseManagementApp.Repositories.CategoryRepository;
import com.example.ExpenseManagementApp.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountService accountService;


    Logger logger = Logger.getLogger(CategoryService.class.getName());
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, AccountRepository accountRepository, UserRepository userRepository, AccountService accountService) {
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Category createCategory(CategoryDTO categoryDTO) {
        Account account = null;

        if (categoryDTO.getAccountId() != null) {
            account = accountRepository.findById(categoryDTO.getAccountId()).orElse(null);

        }

        // Since no shared accounts userId will always be null
//        if (categoryDTO.getUserId() != null) {
//            user = userRepository.findById(categoryDTO.getUserId()).orElse(null);
//        }

        if (account == null) {
            throw new IllegalArgumentException("Account or User does not exist");
        }

        // For no shared accounts (Line 53 and 54)
        User user = accountService.getUser(account);
        Category parentCategory = categoryRepository.findByNameAndId(categoryDTO.getParentCategoryName(),user.getUser_id()).orElse(null);
        Category subCategory = categoryRepository.findByNameAndParent(categoryDTO.getSubCategoryName(),parentCategory).orElse(null);
//        System.out.println("PArent " + parentCategory.getId());
        if (categoryDTO.getSubCategoryName() != null && !(categoryDTO.getSubCategoryName().isEmpty()) && parentCategory == null) {
            throw new IllegalArgumentException("Parent Category does not exist");
        }

        if (parentCategory != null && categoryDTO.getSubCategoryName().isEmpty()) {
            throw new IllegalArgumentException("Parent Category already exists");
        }



        if (subCategory != null) {
            throw new IllegalArgumentException("Sub Category already exists");
        }


        Category category = new Category();


        // No shared accounts so account will always be null
        category.setAccount(null);
        category.setUser(user);
        category.setType(categoryDTO.getType());


        if (categoryDTO.getSubCategoryName() != null && !categoryDTO.getSubCategoryName().isEmpty()) {
            category.setName(categoryDTO.getSubCategoryName());
            category.setParent(parentCategory);
        } else {
            category.setName(categoryDTO.getParentCategoryName());
        }


        logger.info("Category created with name: " + category.getName());
        if (category.getParent() != null) {
            logger.info("Parent category: " + category.getParent().getName());
        }


        return categoryRepository.save(category);
    }
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // Check if the category has subcategories
        List<Category> subcategories = categoryRepository.findByParent(category);
        if (!subcategories.isEmpty()) {
            throw new IllegalStateException("Cannot delete category with subcategories");
        }

        // Check if the category is used in any transactions
        // You might need to inject TransactionRepository and add a method to check this
        // List<Transaction> transactions = transactionRepository.findByCategory(category);
        // if (!transactions.isEmpty()) {
        //     throw new IllegalStateException("Cannot delete category used in transactions");
        // }

        categoryRepository.delete(category);
    }


//    public Long isSharedAccount(Long accountId) throws SQLException {
//        Account account = accountRepository.findById(accountId).orElse(null);
//        if (account == null) {
//            throw new SQLException("Account not found");
//        }
//
//        if ("shared".equals(account.getType())){
//            return account.getAccount_id();
//        } else {
//            return  account.getUser_Foriegn_id().getUser_id();
//        }
//    }

    public List<Category> getCategoriesByType(Category.CatType type) {
        return categoryRepository.findByType(type);
    }

    public List<CategoryDTO> getCategories(Long Id) {
        List<Category> categories = categoryRepository.findByAccountIdOrUserID(Id);
        return categories.stream()
                .map(CategoryDTO::new)
                .toList();
    }

}
