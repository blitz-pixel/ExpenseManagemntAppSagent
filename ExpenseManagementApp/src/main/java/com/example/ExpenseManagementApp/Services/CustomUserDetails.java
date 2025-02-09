////package com.example.ExpenseManagementApp.Services;
////
////import org.springframework.security.core.userdetails.UserDetails;
////import org.springframework.security.core.userdetails.UserDetailsService;
////import org.springframework.security.core.userdetails.UsernameNotFoundException;
////import org.springframework.stereotype.Service;
////
////@Service
////public class CustomUserDetailsService implements UserDetailsService {
////    private final UserRepository userRepository; // Assuming you have a UserRepository
////
////    public CustomUserDetailsService(UserRepository userRepository) {
////        this.userRepository = userRepository;
////    }
////
////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        return userRepository.findByUsername(username)
////                .map(user -> new CustomUserDetails(user))  // Assuming you have a CustomUserDetails class
////                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
////    }
////}
////
//
//package com.example.ExpenseManagementApp.Services;
//
//import com.example.ExpenseManagementApp.Model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//public class CustomUserDetails implements UserDetails {
//
//
//    private final User user;
//
//
//    public CustomUserDetails(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // If no role is defined in User, return a default role (e.g., "USER")
//        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
//
//    }
//    @Override
//    public String getPassword() {
//        // Return the user's password (already encrypted)
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        // Return the user's email (or username)
//        return user.getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        // Return true if the account is not expired
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        // Return true if the account is not locked
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        // Return true if the credentials are not expired
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        // Return true if the account is enabled (you can assume the account is enabled)
//        return true; // or user.isEnabled() if you plan to add this field later
//    }
//
//    // Additional methods if needed
//    public User getUser() {
//        return user;
//    }
//}
