package com.example.demo.user;

import com.example.demo.config.Jwt.JwtUtil;
import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.dto.UserListResponse;
import com.example.demo.user.dto.UserLoginRequest;
import com.example.demo.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    private String secretKey = "secretKey";
    private Long expireTimeMs = 1000 * 60l * 5 * 60; // 토큰 만료시간 : 5시간

    public void saveUser(UserCreateRequest userCreateRequest) {
        String email = userCreateRequest.getEmail();
        String password = userCreateRequest.getPassword();

        // email 중복 검사
        userRepository.findByEmail(email)
                .ifPresent(userEntity -> {
                    throw new RuntimeException();
                });

        UserEntity userEntity = new UserEntity(email, encoder.encode(password));

        userRepository.save(userEntity);
    }

    public String login(UserLoginRequest userLoginRequest) {
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();

        // email 존재 검사
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException());

        // password 확인
        if (!encoder.matches(password, userEntity.getPassword())) {
            throw new RuntimeException();
        }

        String token = JwtUtil.createToken(userEntity.getUserId(), secretKey, expireTimeMs);

        return token;
    }

    public List<UserListResponse> getUsers() {
        // UserRepository는 UserEntity를 다루는 JPA 레파지토리 인터페이스
        // 따라서 사용자 목록을 가져올 때 userRepository.findAll()을 호출하면 반환되는 것은 UserEntity의 컬렉션
        // 이 컬렉션을 UserListResponse 객체의 리스트로 매핑해야하고 이를 위해 UserEntity 객체를 사용하는 생성자 필요함
        return userRepository.findAll().stream().map(UserListResponse::new).collect(Collectors.toList());
    }

    public void updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        String password = userUpdateRequest.getPassword();

        // +고유번호 존재 검사
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        userEntity.updateUserInfo(password);
    }

    public void deleteUser(Long userId){
        // +고유번호 존재 검사
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        userRepository.delete(userEntity);
    }
}
