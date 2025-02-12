package com.example.ExpenseManagementApp.Services;


import com.example.ExpenseManagementApp.DTO.CategoryDTO;
import com.example.ExpenseManagementApp.DTO.TransactionDTO;
import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Repositories.AccountRepository;
import com.example.ExpenseManagementApp.Repositories.CategoryRepository;
import com.example.ExpenseManagementApp.Repositories.TransactionRepository;
import com.example.ExpenseManagementApp.Repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;


    @PersistenceContext
    private EntityManager entityManager;

    Logger logger = Logger.getLogger(CategoryService.class.getName());
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, AccountRepository accountRepository, UserRepository userRepository, AccountService accountService, TransactionService transactionService, TransactionRepository transactionRepository) {
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
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


        if (parentCategory != null) {
            if (categoryDTO.getSubCategoryName().isEmpty()) {
                throw new IllegalArgumentException("Parent Category already exists");
            }
            if (subCategory == null && categoryDTO.getType() != parentCategory.getType()) {
                throw new IllegalArgumentException("Category type must be the same as Parent Category");
            }
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


    public List<CategoryDTO> getCategories(Long id) {
        List<Category> categories = categoryRepository.findByAccountIdOrUserID(id);
        return categories.stream()
                .map(CategoryDTO::new)
                .toList();
    }

    public List<CategoryDTO> getCategories(Long id, Category.CatType type) {
        List<Category> categories = categoryRepository.findByAccountIdOrUserIDAndType(id, type);
        return categories.stream()
                .map(CategoryDTO::new)
                .toList();
    }


//    @Transactional
//    public void deleteCategory(Long accountId, String name) {
//        User user = accountService.getUser(accountId);
//        Category category = categoryRepository.findByNameAndId(name, user.getUser_id())
//                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
//
//
//        List<Category> subCategories = categoryRepository.findByParent(category).orElse(Collections.emptyList());
//        for (Category sub : subCategories) {
//            transactionRepository.markTransactionsAsDeletedByCategory(sub.getId());
//            transactionRepository.updateCategoryToNullByCategory(sub.getId());
//        }
//        categoryRepository.deleteAll(subCategories);
//        transactionRepository.markTransactionsAsDeletedByCategory(category.getId());
//        transactionRepository.updateCategoryToNullByCategory(category.getId());
//        categoryRepository.delete(category);
//    }

    @Transactional
    public void deleteCategory(Long accountId, String name) {
        User user = accountService.getUser(accountId);
        Category category = categoryRepository.findByNameAndId(name, user.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        categoryRepository.delete(category);
        entityManager.flush();
        transactionRepository.SoftDeleteTransactions();
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





}
