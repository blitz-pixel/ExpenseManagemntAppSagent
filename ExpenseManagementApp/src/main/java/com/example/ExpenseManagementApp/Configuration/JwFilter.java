//package com.example.ExpenseManagementApp.Configuration;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//// Comment for testing
//@Component
//public class JwFilter extends OncePerRequestFilter {
//    private final JwtUtil jwtUtil;
//    private final UserDetailsService userDetailsService;
//
//    public JwFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//            String username = jwtUtil.extractUsername(token);
//
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//                if (jwtUtil.validateToken(token, userDetails)) {  // Pass UserDetails instead of username
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}
