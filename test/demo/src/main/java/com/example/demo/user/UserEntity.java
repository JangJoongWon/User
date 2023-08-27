package com.example.demo.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, length = 255, name="email", unique = true)
    private String email;

    @Column(nullable = false, length = 255, name = "password")
    private String password;

    protected UserEntity() {}

    public UserEntity(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 email(%s)이 들어왔습니다", email));
        };
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 password(%s)이 들어왔습니다", password));
        };
        this.email = email;
        this.password = password;
    }

    public void updateUserInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
