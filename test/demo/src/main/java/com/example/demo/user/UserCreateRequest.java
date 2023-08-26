package com.example.demo.user;

import lombok.Data;

public class UserCreateRequest {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
