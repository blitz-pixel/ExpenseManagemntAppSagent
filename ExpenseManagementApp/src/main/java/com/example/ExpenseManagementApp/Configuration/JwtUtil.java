//package com.example.ExpenseManagementApp.Configuration;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.auth0.jwt.interfaces.JWTVerifier;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//
//// Comment for testing
//@Component
//public class JwtUtil {
//
//    private static final String SECRET_KEY = "your-256-bit-secret-your-256-bit-secret"; // 256-bit secret key
//
//    // Generate token with the given username
//    public String generateToken(String userName) {
//
////        Map<String,String> response= new HashMap<>();
//
//        return JWT.create()
//                .withSubject(userName)
//                .withIssuedAt(new Date())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
//                .sign(Algorithm.HMAC256(SECRET_KEY));
//    }
//
//    // Extract the username from the token
//    public String extractUsername(String token) {
//        return extractClaim(token, DecodedJWT::getSubject);
////        return username;
//    }
//
//    // Extract a claim from the token
//    public <T> T extractClaim(String token, Function<DecodedJWT, T> claimsResolver) {
//        final DecodedJWT decodedJWT = decodeToken(token);
//        return claimsResolver.apply(decodedJWT);
//    }
//
//    // Decode the token and get the claims
//    private DecodedJWT decodeToken(String token) {
////        System.out.println("Token Before truncating: " + token);
//        if (token.startsWith("Bearer ")) {
//            token = token.substring(7);  // Remove the "Bearer " part
//        }
//        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
//                .build(); // Create the verifier with the specified algorithm and secret key
////        System.out.println("Token After truncating: " + token);
////        System.out.println("Verifier: " + verifier);
//        return verifier.verify(token); // Verify and decode the token
//    }
//
//    // Check if the token is expired
//    public Boolean isTokenExpired(String token) {
//        Date expiration = extractClaim(token, DecodedJWT::getExpiresAt); // Correct method to get expiration date
//        return expiration.before(new Date());
//    }
//
//    // Validate the token by checking the username and expiration
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//}
