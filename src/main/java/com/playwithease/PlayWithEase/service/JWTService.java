package com.playwithease.PlayWithEase.service;

import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.repo.UsersRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class  JWTService {

    @Autowired
    private UsersRepo usersRepo;

    private final SecretKey secretKey ;

    public JWTService(@Value("${jwt.secret}") String base64Secret){
        try{
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//            byte[] keyBytes = Base64.getDecoder().decode(base64Secret);
            byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
            this.secretKey = Keys.hmacShaKeyFor(keyBytes);
            System.out.println("Secret Key : " + secretKey);
        } catch(RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    public SecretKey getKey() {
        return this.secretKey;
    }

    public String generateToken(String username){

        Users user = usersRepo.findByUsername(username);

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claim("usersId", user.getId())
                .claim("isVerified", user.getIsVerified())
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30) ))
                .and()
                .signWith(getKey())
                .compact();
    }

//    public SecretKey getKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    public String extractUsername(String token){
        // extract the username from JWTToken
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
}
