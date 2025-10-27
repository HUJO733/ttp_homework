package com.example.board.dto;

import com.example.board.domain.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String nickname;

    // 엔티티 → DTO 변환
    public static CommentResponseDto from(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setNickname(comment.getNickname());
        return dto;
    }
}
