package com.example.demo.controller;

import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.util.CustomUserDetails;
import com.example.demo.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto request) {
        return ResponseEntity.status(201).body(userService.signUp(request));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto request) {
        // 로그인 인증 후 JWT 발급
        String jwtToken = userService.login(request);
        return ResponseEntity.ok(jwtToken);  // JWT 반환
    }

    // 사용자 프로필 조회
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("userDetails:{}",userDetails);
        log.info("userDetails.getUsername() :{}",userDetails.getUsername());
        return ResponseEntity.ok(userService.findByUsername(userDetails.getUsername()));
    }

    // 사용자 업데이트
    @PutMapping
    public ResponseEntity<UserResponseDto> update(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.update(userDetails.getId(), request));
    }

    // 사용자 삭제
    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.delete(userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
