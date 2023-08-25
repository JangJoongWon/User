package com.example.demo.user;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String email;
    private String password;

}
