package com.example.board.dto;

import com.example.board.domain.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

    public static CommentResponseDto from(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.id = comment.getId();
        dto.content = comment.getContent();
        dto.nickname = comment.getNickname();
        return dto;
    }
}
