package com.example.ExpenseManagementApp.Services;

//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//import com.example.ExpenseManagementApp.Configuration.JwtUtil;
//
//@Service
//public class AuthenticationService{
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final UserDetailsService userDetailsService;
//
//    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
//    public String authenticate(String username, String password) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        return jwtUtil.generateToken(userDetails.getUsername());
//    }
//}


//import com.example.ExpenseManagementApp.Configuration.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

//@Service
//public class AuthService {
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtil jwtUtil;
//    private final UserDetailsService userDetailsService;
//
//    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
//    public String authenticate(String username, String password) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        return jwtUtil.generateToken(userDetails.getUsername());
//    }
//}
