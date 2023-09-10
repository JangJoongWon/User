package com.example.demo.jwt;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

    public static Long getId(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userId", Long.class);
    }

    public static String createToken(Long userId, String key, long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

//    public static boolean isExpired(String token, String secretAccessKey) {
//        return Jwts.parser().setSigningKey(secretAccessKey).parseClaimsJws(token)
//                .getBody().getExpiration().before(new Date());
//    }

    public static boolean validateToken(String token, String secretKey) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.out.println("Wrong JWT sign");
            throw new RuntimeException();
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT");
            throw new JwtException("Expired JWT");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT");
            throw new RuntimeException();
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal JWT");
            throw new RuntimeException();
        }
    }
}
