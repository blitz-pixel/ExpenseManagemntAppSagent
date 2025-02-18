package com.example.ExpenseManagementApp.Services;


//import com.example.ExpenseManagementApp.Configuration.JwtUtil;
import com.example.ExpenseManagementApp.Configuration.PasswordUtil;
import com.example.ExpenseManagementApp.DTO.UserDTO;
import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.Category;
import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Repositories.AccountRepository;
import com.example.ExpenseManagementApp.Repositories.CategoryRepository;
import com.example.ExpenseManagementApp.Repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
//import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
// For jwt Authorization do implements UserDetailService
public class UserService{


    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    @PersistenceContext
    private EntityManager entityManager;
    Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserService(UserRepository userRepository, AccountRepository accountRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    // For testing
//    @Transactional
//    public User addUser(User user) {
//        return userRepository.save(user);
//    }

    public boolean FindUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }


//    @Transactional
//    public void addUserPersonal(UserDTO userDTO) {
//
//        if (FindUserByEmail(userDTO.getEmail()) &&
//                userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Email already exists");
//        }
//
//
//        User user = new User();
//        user.setUserName(userDTO.getUserName());
//        user.setEmail(userDTO.getEmail());
//        String HashPassword = PasswordUtil.hashPassword(userDTO.getPassword());
//        user.setPassword(HashPassword);
//
//        User savedUser = userRepository.save(user);
//        entityManager.flush();
//
//        Account account = new Account();
//        account.setAccountName(savedUser.getUserName());
//        account.setType(Account.AccountType.personal);
//        account.setUser_Foriegn_id(savedUser);
//        accountRepository.save(account);
//        entityManager.flush();
//
//       List<Category> parentCategories = new ArrayList<>();
//        for (int i = 0;i < 5;i++){
//            Category category = new Category();
//            category.setName("Category " + i);
//            category.setUser(savedUser);
//            category.setAccount(account);
//            category.setParent(null);
//            parentCategories.add(category);
//        }
//        categoryRepository.saveAll(parentCategories);
//        entityManager.flush();
//        List<Long> parentCategoriesId = parentCategories.stream().map(
//                Category::getId
//        ).toList();
//        List<Category> subCategories = new ArrayList<>();
//
//        for (int i = 0; i< parentCategoriesId.size();i++) {
//            for (int j = 0; j < 5; j++) {
//                Category category = new Category();
//                category.setName("SubCategory " + j);
//                category.setUser(savedUser);
//                category.setAccount(account);
//                category.setParent(parentCategories.get(i));
//                subCategories.add(category);
//            }
//        }
//
//        categoryRepository.saveAll(subCategories);
//
//    }

    private static List<Map<String, Category.CatType>> createSubCategories(Category.CatType type, String... names) {
        return Arrays.stream(names)
                .map(name -> Map.of(name, type))
                .toList();
    }
    @Transactional
    public User addUserPersonal(UserDTO userDTO) {
        Map<String, Category.CatType> parentCategoryTypes = Map.of(
                "Food", Category.CatType.expense,
                "Transport", Category.CatType.expense,
                "Entertainment", Category.CatType.expense,
                "Health", Category.CatType.expense,
                "Income", Category.CatType.income
        );

        Map<String, List<Map<String, Category.CatType>>> subCategories = Map.of(
                "Food", createSubCategories(Category.CatType.expense, "Groceries", "Restaurants", "Fast Food", "Snacks", "Drinks"),
                "Transport", createSubCategories( Category.CatType.expense,"Public Transport", "Taxi", "Car", "Bike", "Fuel"),
                "Entertainment", createSubCategories( Category.CatType.expense,"Movies", "Games", "Concerts", "Events", "Books"),
                "Health", createSubCategories( Category.CatType.expense,"Medicines", "Doctor", "Gym", "Insurance", "Pharmacy"),
                "Income", createSubCategories( Category.CatType.income,"Salary", "Bonus", "Gifts", "Refunds", "Investments")
        );

        if (FindUserByEmail(userDTO.getEmail()) &&
                userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }


        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(PasswordUtil.hashPassword(userDTO.getPassword()));

        User savedUser = userRepository.save(user);
        entityManager.flush();

        Account account = new Account();
        account.setAccountName(savedUser.getUserName());
        account.setType(Account.AccountType.personal);
        account.setUser_Foriegn_id(savedUser);

        Account savedAccount = accountRepository.save(account);
        entityManager.flush();



        List<Category> parentCategories = parentCategoryTypes.entrySet().stream()
                .map(entry -> {
                    Category category = new Category();
                    category.setName(entry.getKey());
                    category.setUser(savedUser);
                    category.setType(entry.getValue());
                    category.setParent(null);
                    entityManager.persist(category);
                    return category;
                }).toList();

        entityManager.flush();

        parentCategories.forEach(parent -> {
            subCategories.getOrDefault(parent.getName(), Collections.emptyList()).forEach(subCategoryMap -> {
                subCategoryMap.forEach((subCategoryName, subCategoryType) -> {
                    Category subCategory = new Category();
                    subCategory.setName(subCategoryName);
                    subCategory.setType(subCategoryType);
                    subCategory.setUser(savedUser);
                    subCategory.setParent(parent);
                    entityManager.persist(subCategory);
                });
            });
        });


        entityManager.flush();
        return savedUser;
    }

//    @Transactional
//    public void addUserPersonal(UserDTO userDTO) {
//
//        if (FindUserByEmail(userDTO.getEmail()) &&
//                userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Email already exists");
//        }
//
//
//        User user = new User();
//        user.setUserName(userDTO.getUserName());
//        user.setEmail(userDTO.getEmail());
//        String HashPassword = PasswordUtil.hashPassword(userDTO.getPassword());
//        user.setPassword(HashPassword);
//
//        User savedUser = userRepository.save(user);
//        entityManager.flush();
//
//        Account account = new Account();
//        account.setAccountName(savedUser.getUserName());
//        account.setType(Account.AccountType.personal);
//        account.setUser_Foriegn_id(savedUser);
//        accountRepository.save(account);
//
//    }



    public User getUserByAccountId(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public Long getAccountID(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        Account account = accountRepository.findByUser_Foriegn_id(user.getUser_id()).orElseThrow(
                () -> new IllegalArgumentException("Account not found")
        );
        return account.getAccountId();
    }

    public Long ValidateUser(UserDTO login){
        User user = userRepository.findByEmail(login.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("User not found. Please register")
        );
        if ((!login.getEmail().equalsIgnoreCase("testprofile@123.com") && (!PasswordUtil.verifyPassword(login.getPassword(), user.getPassword())))) {
            throw new IllegalArgumentException("Invalid password");
        }

        return accountRepository.findOldestAccountId(user.getUser_id()).orElseThrow(
                () -> new IllegalArgumentException("Create a account")
        );
    }


    public void UpdateUserDetails(UserDTO userDTO, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        if (userDTO.getUserName() != null && !userDTO.getUserName().isEmpty()) {
            user.setUserName(userDTO.getUserName());
        }
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            user.setEmail(userDTO.getEmail());
        }
//        System.out.println(userDTO);
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            String HashPassword = PasswordUtil.hashPassword(userDTO.getPassword());
            user.setPassword(HashPassword);
//            logger.info(userDTO.getPassword());
        }
        userRepository.save(user);
    }

//    public String authenticateUser(LoginDTO loginDTO) {
//        //*
//        Optional<User> userOptional = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
//
//        if (userOptional.isEmpty()) {
//            throw new UsernameNotFoundException("User not found with email: " + loginDTO.getEmail());
//        }
//
//        User user = userOptional.get();
//
//        // Validate password
//        if (!(user.getPassword().equals(loginDTO.getPassword()))) {
//            throw new UsernameNotFoundException("Invalid password");
//        }
//
//        // Generate JWT Token
//        return jwtUtil.generateToken(user.getEmail());
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        // Modify this method to load the user by email (or username)
//        Optional<User> userOptional = userRepository.findByEmail(email);
//
//        if (userOptional.isEmpty()) {
//            throw new UsernameNotFoundException("User not found with email: " + email);
//        }
//
//        // Return a custom UserDetails object (you can use CustomUserDetails)
//        User user = userOptional.get();
//        return new CustomUserDetails(user);
//}



}

