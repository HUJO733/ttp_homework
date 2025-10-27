package com.example.board.dto;

import com.example.board.domain.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> comments;

    // 엔티티 → DTO 변환
    public static BoardResponseDto from(Board board) {
        BoardResponseDto dto = new BoardResponseDto();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setNickname(board.getNickname());
        dto.setCreatedAt(board.getCreatedAt());

        // 댓글 포함 변환
        dto.setComments(
                board.getComments().stream()
                        .map(CommentResponseDto::from)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}
