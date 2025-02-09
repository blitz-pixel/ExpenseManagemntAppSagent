package com.example.ExpenseManagementApp.Controllers;

import com.example.ExpenseManagementApp.DTO.CategoryDTO;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Services.AccountService;
import com.example.ExpenseManagementApp.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final AccountService accountService;
    Logger logger = Logger.getLogger(CategoryController.class.getName());

    @Autowired
    public CategoryController(CategoryService categoryService, AccountService accountService) {
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    // Sending account Id always before implementing shared accounts
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@RequestParam Long Id) {
        // Before implementing the concept of shared accounts
        Long userId = accountService.getUserId(Id);
        return ResponseEntity.ok(categoryService.getCategories(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            Category createdCategory = categoryService.createCategory(categoryDTO);
            return ResponseEntity.ok("Category added successfully");
        } catch (Exception e) {
            if (e.getMessage().equals("Parent Category does not exist")) {
                return ResponseEntity.badRequest().body("Parent Category does not exist");
            }

            if (e.getMessage().equals("Sub Category already exists")) {
                return ResponseEntity.badRequest().body("Sub Category already exists");
            }
            if (e.getMessage().equals("Parent Category already exists")) {
                return ResponseEntity.badRequest().body("Parent Category already exists");
            }
            logger.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.severe("Error deleting category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the category");
        }
    }


//    @GetMapping("/{id}")
//    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
//        Category category = categoryService.getCategoryById(id);
//       logger.info(category.toString());
//        if (category != null && category.getParent() != null) {
//            System.out.println("Parent Category: " + category.getName());
//        }
//
//
//        return ResponseEntity.ok().body(category);
//    }
//
//
//    @GetMapping("/type/{type}")
//    public ResponseEntity<List<Category>> getCategoriesByType(@PathVariable Category.CatType type) {
//        return ResponseEntity.ok(categoryService.getCategoriesByType(type));
//    }
//




//    @GetMapping("/{accountId}/categories")
//    public ResponseEntity<List<Category>> getCategories(@PathVariable Long accountId) {
//        try {
//            Long account_scope_id = categoryService.isSharedAccount(accountId);
//            List<Category> categories = categoryService.getCategories(account_scope_id);
//            return new ResponseEntity<>(categories, HttpStatus.OK);
//        } catch (SQLException e) {
//            // Log the exception and return an error response
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}