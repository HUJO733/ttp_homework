package com.example.board.dto;

import com.example.board.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponseDto {
    private final Long id;
    private final String content;
    private final String nickname;

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getNickname())
                .build();
    }
}

