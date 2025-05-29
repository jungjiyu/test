package com.example.demo.service;

import com.example.demo.dto.request.CommentRequestDto;
import com.example.demo.dto.response.CommentResponseDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponseDto create(CommentRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("작성자(user)가 존재하지 않습니다."));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글(post)이 존재하지 않습니다."));
        Comment comment = request.toEntity(user, post);
        return CommentResponseDto.fromEntity(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll() {
        return commentRepository.findAll().stream()
                .map(CommentResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        return CommentResponseDto.fromEntity(comment);
    }

    public CommentResponseDto update(Long id, CommentRequestDto request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        comment.update(request.getContent());
        return CommentResponseDto.fromEntity(comment);
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}