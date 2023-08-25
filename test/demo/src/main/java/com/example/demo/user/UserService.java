package com.example.demo.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateRequest userCreateRequest) {
        userRepository.save(new UserEntity(userCreateRequest.getEmail(), userCreateRequest.getPassword()));
    }
}
