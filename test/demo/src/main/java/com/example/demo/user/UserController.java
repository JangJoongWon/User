package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup") // Post user
    public ResponseEntity<String> saveUser(@RequestBody UserCreateRequest userCreateRequest) {
        userService.saveUser(userCreateRequest);
        return ResponseEntity.ok("회원가입 성공");
    }
    @GetMapping("/list")  // Get user list
    public ResponseEntity<List<UserListResponse>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
    @PutMapping("/update")
    public void updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam String email) {
        userService.deleteUser(email);
    }
}
