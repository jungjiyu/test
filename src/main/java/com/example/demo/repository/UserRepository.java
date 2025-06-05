package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일을 기준으로 사용자 찾기
    Optional<User> findByEmail(String email);

    // 닉네임을 기준으로 사용자 찾기
    Optional<User> findByNickname(String nickname);
}