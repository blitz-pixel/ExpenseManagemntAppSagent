package com.example.ExpenseManagementApp.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    // CORS Configuration
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // Allow reques from this originst
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow these HTTP methods
                        .allowedHeaders("*")
                        .exposedHeaders("*")
                        .allowCredentials(true); // Allow credentials (e.g., cookies)
            }
        };
    }

    // Security Configuration for testing
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/**").permitAll()
                        .anyRequest().permitAll()) // Allow all other requests as well
                .csrf(AbstractHttpConfigurer::disable); // Disable CSRF protection

        return http.build();
    }

//    private final JwFilter jwFilter;

    public CorsConfig() {
        ;
    }

//    @Bean
//    public SecurityFilterChain ecurityFilterChain(HttpSecurity http, JwFilter jwtFilter) throws Exception {
//        return http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/Login","/error","/api/v1/Registration").permitAll()
//                        .anyRequest().authenticated())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/v1/Login","/error","api/v1/Registration")) // Ignore CSRF only for login
//                .build();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//
//        public SecurityConfig(UserDetailsService userDetailsService) {
//            this.userDetailsService = userDetailsService;
//        }
//
//        // Exposing AuthenticationManager as a Bean
//        @Bean
//        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//            return config.getAuthenticationManager();
//        }
//
//        // Define Password Encoder Bean
//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return new BCryptPasswordEncoder();
//        }

    // You can also override HttpSecurity configurations here if needed

}




//package com.example.SampleLoginApplication.Configurations;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class SecurityConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Apply CORS to all endpoints
//                .allowedOrigins("http://localhost:5173") // Allow requests from your React app
//                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow these HTTP methods
//                .allowedHeaders("*"); // Allow all headers
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/**")
//                        .permitAll()
//                        .anyRequest() // All other requests need authentication
//                        .authenticated())
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/**"));
//
//        return http.build();
//    }
//}
//
