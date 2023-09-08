package com.example.demo.jwt;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable e) throws IOException {
        response.setStatus(status.value());
//        response.setContentType("application/json; charset=UTF-8");
//
//        JSONObject responseJson = new JSONObject();
//        responseJson.put("HttpStatus", HttpStatus.UNAUTHORIZED);
//        responseJson.put("message", e.getMessage());
//        responseJson.put("status", false);
//        responseJson.put("statusCode", 401);
//        responseJson.put("code", "401");
//        response.getWriter().print(responseJson);
    }
}
