package com.example.demo.dto.request;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequestDto {
    private String content;
    private Long userId;
    private Long postId;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .content(this.content)
                .user(user)
                .post(post)
                .build();
    }
}

