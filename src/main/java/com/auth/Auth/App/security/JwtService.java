package com.auth.Auth.App.security;

import com.auth.Auth.App.model.Role;
import com.auth.Auth.App.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private  final SecretKey key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;
    private final String issuer;
    public JwtService(
            @Value("${security.jwt.secret}") String secretKey,
            @Value("${security.jwt.access-ttl-seconds}") long accessTtlSeconds,
            @Value("${security.jwt.refresh-ttl-seconds}") long refreshTtlSeconds,
            @Value("${security.jwt.issuer}") String issuer
    ) {
//        if (secretKey == null || secretKey.length() < 64) {
//            throw new IllegalArgumentException("The JWT secret key must be at least 64 characters long");
//        }
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
        this.issuer = issuer;
    }

    //generate token
    public  String generateAccessToken(User user){
        Instant now = Instant.now();
      List<String> roles =  user.getRoles() == null ? List.of() :
                user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(accessTtlSeconds)))
                .addClaims(Map.of( "roles",roles,
                        "email", user.getEmail(),
                        "typ","access")
                       )
                .signWith(key, SignatureAlgorithm.ES512)
                .compact();
    }

    //generate refresh token
    public String generateRefreshToken(User user, String jti){
        Instant now = Instant.now();
        return Jwts.builder()
                .setId(jti)
                .setSubject(user.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
                .addClaims(Map.of(
                        "typ","refresh"))
                .signWith(key, SignatureAlgorithm.ES512)
                .compact();
    }

    //parse token
    public Jws<Claims> parseToken(String token){
        try {
          return  Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        }catch (JwtException e){
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }

    // is access token
    public boolean isAccessToken(String token){
        Jws<Claims> jws = parseToken(token);
        String type = jws.getBody().get("typ", String.class);
        return "access".equals(type);

    }

    // is refresh token
    public boolean isRefreshToken(String token){
        Jws<Claims> jws = parseToken(token);
        String type = jws.getBody().get("typ", String.class);
        return "refresh".equals(type);

    }

    // get user id from refresh token
    public UUID getUserIdFromRefreshToken(String token){
        Jws<Claims> jws = parseToken(token);
        String userIdStr = jws.getBody().getSubject();
        return UUID.fromString(userIdStr);
    }

    //get jti from refresh token
    public String getJtiFromRefreshToken(String token){
        Jws<Claims> jws = parseToken(token);
        return jws.getBody().getId();
    }
}
