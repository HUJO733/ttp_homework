package com.example.board.dto;

import com.example.board.domain.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final String nickname;

    private CommentResponseDto(Long id, String content, String nickname) {
        this.id = id;
        this.content = content;
        this.nickname = nickname;
    }

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getNickname()
        );
    }
}

