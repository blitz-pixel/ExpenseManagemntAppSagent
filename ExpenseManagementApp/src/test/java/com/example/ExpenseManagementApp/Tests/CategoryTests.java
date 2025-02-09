package com.example.ExpenseManagementApp.Tests;

import com.example.ExpenseManagementApp.DTO.CategoryDTO;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Repositories.CategoryRepository;
import com.example.ExpenseManagementApp.Repositories.UserRepository;
import com.example.ExpenseManagementApp.Services.CategoryService;
import com.example.ExpenseManagementApp.Services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CategoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserRepository userRepository;


    @Test
    public  void  testThatReturnsCategoryByName(){
        Category c = categoryRepository.findByName("Food").orElse(null);

        assertNotNull(c, "Category should not be null when it exists.");
    }

    @Test
    public void testThatCreatesCategory(){
        Category category = new Category();
        CategoryDTO categoryDTO = new CategoryDTO(22L, null, "Rent", "House", Category.CatType.income);
        Category savedCategory = categoryService.createCategory(categoryDTO);

        assertNotNull(savedCategory, "Category should be saved");
    }

    @Test
    public void testThtFindsSubCategory(){
        Category c = categoryRepository.findByParent("Burger",5L).orElse(null);

        assertNotNull(c, "Sub Category should not be null when it exists.");
    }

    @Test
    public void testThtFindsAllCategories(){
        List<Category> Categories = categoryRepository.findByAccountIdOrUserID(42L);

        assertNotNull(Categories, "Category should not be null when it exists.");
    }

    @Test
    void  testThtFindsSpecificCategory(){
        User user = userRepository.findById(42L).orElse(null);
        Category c = categoryRepository.findByNameAndId("Food",user.getUser_id()).orElse(null);


        assertNotNull(c, "Category should not be null when it exists.");
    }


}
