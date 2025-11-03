package com.example.board.dto;

import com.example.board.domain.Comment;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final String nickname;

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getNickname()
        );
    }
}

