package com.example.demo.user;

public class UserListResponse {
    private long id;

    private String email;

    private String password;

    public UserListResponse(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
    }

    //    public UserListResponse(Long id, String email, String password) {
    //        this.id = id;
    //        this.email = email;
    //        this.password = password;
    //    }


    // 유저 리스트 조회할 때 게터 필요함!
    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
