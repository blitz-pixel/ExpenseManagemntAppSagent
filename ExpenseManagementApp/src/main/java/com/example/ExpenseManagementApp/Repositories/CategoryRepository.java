package com.example.ExpenseManagementApp.Repositories;

import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.Transaction;
import com.example.ExpenseManagementApp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

//    @Query(value = "SELECT * FROM category WHERE scope_id = :scopeId", nativeQuery = true)
//    List<Category> findCategoriesByScopeId(@Param("scopeId") Long scopeId);


    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name = ?1 AND c.parent.id = ?2")
    Optional<Category> findByParent(String name, Long parentCategoryId);


    List<Category> findByType(Category.CatType type);

//    List<Category> findByAccountIdOrUserId(Long accountId, Long userId);

    List<Category> findCategoriesById(Long id);

    @Query("SELECT c FROM Category c WHERE c.account.id = ?1 OR c.user.user_id = ?1")
    List<Category> findByAccountIdOrUserID(Long accountId);

    Optional<Category> findByNameAndParent(String name, Category parent);
    Optional<List<Category>> findByParent(Category parent);


    @Query("SELECT c FROM Category c WHERE c.name = ?1 AND (c.account.id = ?2 OR c.user.user_id = ?2)")
    Optional<Category> findByNameAndId(String name, Long Id);
    List<Category> findByIdAndType(Long id, Category.CatType type);

    @Query("SELECT c FROM Category c WHERE  (c.account.id = ?1 OR c.user.user_id = ?1) AND c.type = ?2")
    List<Category> findByAccountIdOrUserIDAndType(Long id, Category.CatType type);

    @Modifying
    @Query("DELETE FROM Category c WHERE c.parent = :category")
    void deleteSubcategories(@Param("category") Category category);



//    Optional<Category> findByUser(User user);

    Optional<Category> findByUserAndName(User user, String name);




//    @Query("SELECT c FROM Category c WHERE c.name = ?1")
//    Optional<Category> findParentByName(String name);
}
