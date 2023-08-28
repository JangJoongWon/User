package com.example.demo.user;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private long id;

    private String email;

    private String password;
}
