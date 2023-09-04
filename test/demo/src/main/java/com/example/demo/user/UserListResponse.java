package com.example.demo.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserListResponse {

    private long userId;
    private String email;
    private String password;

    public UserListResponse(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
    }
//
//    public UserListResponse(Long id, String email, String password) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//    }
//
//
//    // 유저 리스트 조회할 때 게터 필요함!
//    public long getId() {
//        return id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
}
