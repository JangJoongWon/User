package com.example.demo.user;

import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.dto.UserListResponse;
import com.example.demo.user.dto.UserLoginRequest;
import com.example.demo.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody UserCreateRequest userCreateRequest) {
        userService.saveUser(userCreateRequest);
        return ResponseEntity.ok("회원가입");
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) {
        String token = userService.login(userLoginRequest);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping
    public ResponseEntity<List<UserListResponse>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    // 현재 api도 수정하고, 비밀번호와 비밀번호 외의 정보를 수정하는 api를 각각 만들어야함
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);
        return ResponseEntity.ok("회원수정");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("회원삭제");
    }
}
