package com.example.demo.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final String secretAccessKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request Header에서 token꺼내기
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // token을 보내지 않으면 block
        if (!StringUtils.hasText(authorization) || !authorization.startsWith(("Bearer "))) {
            filterChain.doFilter(request, response);
            return;
        }

        // token에서 Bearer분리하기
        String token = authorization.split(" ")[1];

//        // token Expired 되었는지 여부
//        try {
//            Jwts.parser().setSigningKey(secretAccessKey).parseClaimsJws(token);
//        } catch (ExpiredJwtException e) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            filterChain.doFilter(request, response);
//            return;
//        }

        // token 유효성 검사
        if (StringUtils.hasText(token) && JwtUtil.validateToken(token, secretAccessKey)) {
            System.out.println("--validation ok--");
        }

        // token에서 userId 꺼내기
        Long userId = JwtUtil.getId(token, secretAccessKey);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));

        // Detail build
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
