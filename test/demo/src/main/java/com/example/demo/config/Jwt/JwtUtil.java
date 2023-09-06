package com.example.demo.config.Jwt;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

    public static Long getId(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userId", Long.class);
    }

//    public static boolean isExpired(String token, String secretKey) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
//                .getBody().getExpiration().before(new Date());
//    }

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

    public static boolean validateToken(String token, String secretKey) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
            throw new RuntimeException();
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
            throw new RuntimeException();
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
            throw new RuntimeException();
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
            throw new RuntimeException();
        }
    }
}
