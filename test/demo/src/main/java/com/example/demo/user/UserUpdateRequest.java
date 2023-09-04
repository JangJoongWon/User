package com.example.demo.user;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private long userId;
    private String email;
    private String password;
}
