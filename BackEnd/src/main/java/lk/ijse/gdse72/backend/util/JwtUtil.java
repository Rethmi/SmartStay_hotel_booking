//package lk.ijse.gdse72.backend.util;
////
////import io.jsonwebtoken.ExpiredJwtException;
////import io.jsonwebtoken.JwtException;
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.SignatureAlgorithm;
////import io.jsonwebtoken.security.Keys;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.stereotype.Component;
////
////import java.util.Date;
////
////@Component
////public class JwtUtil {
////
////    @Value("${jwt.expiration}")
////    private Long expiration; // 24 hours
////
////    @Value("${jwt.secret}")
////    private String secretKey;
////
////    public String genarateToken(String userName){
////        return Jwts.builder()
////                .setSubject(userName)
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + expiration))
////                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()),
////                        SignatureAlgorithm.HS256).compact();
////    }
////
////    public String extractUsername(String token) {
////        return Jwts.parserBuilder()
////                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
////                .build()
////                .parseClaimsJws(token)
////                .getBody()
////                .getSubject();
////    }
////
////    public boolean validateToken(String token){
////        try {
////            Jwts.parserBuilder()
////                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
////                    .build()
////                    .parseClaimsJws(token);
////            return true;
////        } catch (ExpiredJwtException e) {
////            System.out.println("JWT Token expired: " + e.getMessage());
////        } catch (JwtException e) {
////            System.out.println("JWT Token invalid: " + e.getMessage());
////        } catch (Exception e) {
////            System.out.println("JWT Token validation error: " + e.getMessage());
////        }
////        return false;
////    }
////
////
////}
//package lk.ijse.gdse72.backend.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lk.ijse.gdse72.backend.dto.UserDTO;
//import lk.ijse.gdse72.backend.entity.User;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.io.Serializable;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//
//@Component
//@PropertySource(ignoreResourceNotFound = true, value = "classpath:otherprops.properties")
//public class JwtUtil implements Serializable {
//
//    private static final long serialVersionUID = 234234523523L;
//
//    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 12;
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    //retrieve username from jwt token
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
//    public Claims getUserRoleCodeFromToken(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//    }
//
//    //retrieve expiration date from jwt token
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//
//
//    //for retrieving any information from token we will need the secret key
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//    }
//
//
//    //check if the token has expired
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//
//    //generate token for user
//    public String generateToken(UserDTO userDTO) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("role",userDTO.getRole());
//        return doGenerateToken(claims, userDTO.getEmail());
//    }
//
//    //while creating the token -
//    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//    //2. Sign the JWT using the HS512 algorithm and secret key.
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
//    }
//
//
//    //validate token
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//}
package lk.ijse.gdse72.backend.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import lk.ijse.gdse72.backend.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.secret}")
    private String secretKey;

//    private Key getSigningKey() {
//        // Ensure secretKey is at least 64 bytes for HS512
//        return Keys.hmacShaKeyFor(secretKey.getBytes());
//    }
//
//    // Retrieve username from JWT token
//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
//    public Claims getUserRoleCodeFromToken(String token) {
//        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
//    }
//
//    // Retrieve expiration date from JWT token
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
//    }
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }

    // Generate token for user
    public String generateToken(String email) {
        return Jwts.builder().setSubject(email).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }

    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
//                .compact();
//    }

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
}