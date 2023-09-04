package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup") // Post user
    public ResponseEntity<String> saveUser(@RequestBody UserCreateRequest userCreateRequest) {
        userService.saveUser(userCreateRequest);
        return ResponseEntity.ok("회원가입");
    }
    @GetMapping("/list")  // Get user list
    public ResponseEntity<List<UserListResponse>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
        return ResponseEntity.ok("회원수정");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok("회원탈퇴");
    }
}
