package com.example.demo.user.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    String email;
    String password;
}
