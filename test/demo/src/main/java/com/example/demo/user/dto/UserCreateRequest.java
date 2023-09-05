package com.example.demo.user.dto;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String email;
    private String password;
}
