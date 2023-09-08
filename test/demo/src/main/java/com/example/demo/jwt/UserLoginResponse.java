package com.example.demo.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponse {
    String accessToken;
    String refreshToken;
}
