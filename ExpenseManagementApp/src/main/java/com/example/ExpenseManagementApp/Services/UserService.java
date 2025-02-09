package com.example.ExpenseManagementApp.Services;


//import com.example.ExpenseManagementApp.Configuration.JwtUtil;
import com.example.ExpenseManagementApp.DTO.RegisterDTO;
import com.example.ExpenseManagementApp.Model.Account;
import com.example.ExpenseManagementApp.Model.User;
import com.example.ExpenseManagementApp.Repositories.AccountRepository;
import com.example.ExpenseManagementApp.Repositories.UserRepository;
import jakarta.transaction.Transactional;
//import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// For jwt Authorization do implements UserDetailService
public class UserService{


    private final UserRepository userRepository;
//    private final JwtUtil jwtUtil;
    private final AccountRepository accountRepository;

    @Autowired
    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
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


    @Transactional
    public void addUserPersonal(RegisterDTO registerDTO) {

        if (FindUserByEmail(registerDTO.getEmail()) &&
                userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }


        User user = new User();
        user.setUserName(registerDTO.getUserName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());

        User savedUser = userRepository.save(user);

//        System.out.println("Saved User ID: " + savedUser.getUser_id());

        Account account = new Account();
        account.setAccountName(savedUser.getUserName());
        account.setType(Account.AccountType.personal);
        account.setUser_Foriegn_id(savedUser);
//        System.out.println("Account :"  + account.getUser_Foriegn_id());
//        System.out.println("Account ID:"  + account.getUser_Foriegn_id().getUser_id());

        accountRepository.save(account);
//      System.out.println("Saved Account ID: " + savedAccount.getAccount_id());

    }

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

