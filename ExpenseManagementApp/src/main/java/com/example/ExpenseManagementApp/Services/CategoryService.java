package com.example.ExpenseManagementApp.Services;


import com.example.ExpenseManagementApp.DTO.CategoryDTO;
import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Repositories.AccountRepository;
import com.example.ExpenseManagementApp.Repositories.CategoryRepository;
import com.example.ExpenseManagementApp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;


    Logger logger = Logger.getLogger(CategoryService.class.getName());
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, AccountRepository accountRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Account account = null;
        User user = null;
        if (categoryDTO.getAccountId() != null) account = accountRepository.findById(categoryDTO.getAccountId()).orElse(null);
//        if (categoryDTO.getUser_id() == null) throw new IllegalArgumentException("User id is required");
        if(categoryDTO.getUser_id() != null) user = userRepository.findById(categoryDTO.getUser_id()).orElse(null);
        Category ParentCategory = categoryRepository.findByName(categoryDTO.getParentCategoryName()).orElse(null);
        Category SubCategory = categoryRepository.findByName(categoryDTO.getSubCategoryName()).orElse(null);

        if (categoryDTO.getSubCategoryName()!= null && categoryDTO.getSubCategoryName().isEmpty() && ParentCategory != null  ) {
            throw new IllegalArgumentException("Parent Category already exists");
        }
        if (SubCategory != null) {
            throw new IllegalArgumentException("Sub Category already exists");
        }
//        Category Parent = categoryRepository.findBy
        Category category = new Category();
        category.setAccount(account);
        category.setUser(user);
        category.setType(categoryDTO.getType());
        if ( categoryDTO.getSubCategoryName() != null && categoryDTO.getSubCategoryName().isEmpty()) {
            category.setName(categoryDTO.getParentCategoryName());
        } else {
            category.setName(categoryDTO.getSubCategoryName());
            category.setParent(ParentCategory);
        }
        logger.info(category.getName());
        return  categoryRepository.save(category);
    }

    public Long isSharedAccount(Long accountId) throws SQLException {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            throw new SQLException("Account not found");
        }

        return account.getUser_Foriegn_id().getUser_id();
    }

   public List<Category> getCategoriesById(Long id){
        return categoryRepository.findCategoriesById(id);
   }





    public List<Category> getCategoriesByType(Category.CatType type) {
        return categoryRepository.findByType(type);
    }
}
