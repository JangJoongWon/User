package com.example.demo.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateRequest userCreateRequest) {
        userRepository.save(new UserEntity(userCreateRequest.getEmail(), userCreateRequest.getPassword()));
    }

    public List<UserListResponse> getUsers() {
        // UserRepository는 UserEntity를 다루는 JPA 레파지토리 인터페이스
        // 따라서 사용자 목록을 가져올 때 userRepository.findAll()을 호출하면 반환되는 것은 UserEntity의 컬렉션
        // 이 컬렉션을 UserListResponse 객체의 리스트로 매핑해야하고 이를 위해 UserEntity 객체를 사용하는 생성자 필요함
        return userRepository.findAll().stream().map(UserListResponse::new).collect(Collectors.toList());
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) {
        UserEntity userEntity = userRepository.findById(userUpdateRequest.getId()).orElseThrow(IllegalArgumentException::new);
        userEntity.updateUserInfo(userUpdateRequest.getEmail(), userUpdateRequest.getPassword());
        userRepository.save(userEntity);
    }
}
