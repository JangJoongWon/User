package com.example.demo.user;

import com.example.demo.jwt.JwtUtil;
import com.example.demo.jwt.UserLoginResponse;
import com.example.demo.user.dto.UserCreateRequest;
import com.example.demo.user.dto.UserListResponse;
import com.example.demo.user.dto.UserLoginRequest;
import com.example.demo.user.dto.UserUpdateRequest;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    private String secretAccessKey = "secretAccessKey";
    private String secretRefreshKey = "secretRefreshKey";
    private Long expireAccessTimeMs = 1000 * 60l * 1 * 1; // access 토큰 만료시간 : 1분
    private Long expireRefreshTimeMs = 1000 * 60l * 24 * 60 * 14; // refresh 토큰 만료시간 : 2주일

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

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();

        // email 존재 검사
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException());

        // password 확인
        if (!encoder.matches(password, userEntity.getPassword())) {
            throw new RuntimeException();
        }

        // token 생성
        String accessToken = JwtUtil.createToken(userEntity.getUserId(), secretAccessKey, expireAccessTimeMs);
        String refreshToken = JwtUtil.createToken(userEntity.getUserId(), secretRefreshKey, expireRefreshTimeMs);

        // refreshToken을 DB에 저장
        userEntity.updateRefreshToken(refreshToken);

        UserLoginResponse userLoginResponse = new UserLoginResponse(accessToken, refreshToken);

        return userLoginResponse;
    }

    public String autoLogin(String refreshToken) {

        // token을 보내지 않으면 block
        if (!StringUtils.hasText(refreshToken) || !refreshToken.startsWith(("Bearer "))) {
            System.out.println(refreshToken);
            return "유효하지 않은 토큰입니다.";
        }

        // token에서 Bearer분리하기
        String token = refreshToken.split(" ")[1];

        // token 유효성 검사
        try {
            if (!StringUtils.hasText(token)) {
                return "유효하지 않은 토큰입니다.";
            }
            JwtUtil.validateToken(token, secretRefreshKey);
        } catch (JwtException e) {
            return "재로그인이 필요합니다.";
        }

        // token에서 userId 꺼내기
        Long userId = JwtUtil.getId(token, secretRefreshKey);

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException());

        // refresh token이 DB에 저장된 값과 같은지 확인
        if (!userEntity.getRefreshToken().equals(token)) {
            return "토큰이 일치하지 않습니다.";
        }

        // token 생성
        String accessToken = JwtUtil.createToken(userEntity.getUserId(), secretAccessKey, expireAccessTimeMs);

        return accessToken;
    }

    public List<UserListResponse> getUsers() {
        // UserRepository는 UserEntity를 다루는 JPA 레파지토리 인터페이스
        // 따라서 사용자 목록을 가져올 때 userRepository.findAll()을 호출하면 반환되는 것은 UserEntity의 컬렉션
        // 이 컬렉션을 UserListResponse 객체의 리스트로 매핑해야하고 이를 위해 UserEntity 객체를 사용하는 생성자 필요함
        return userRepository.findAll().stream().map(UserListResponse::new).collect(Collectors.toList());
    }

    public void updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        String password = userUpdateRequest.getPassword();

        // userId 존재 검사
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        userEntity.updateUserInfo(password);
    }

    public void deleteUser(Long userId){
        // userId 존재 검사
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        userRepository.delete(userEntity);
    }
}
