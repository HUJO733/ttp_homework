package com.example.board.dto;

import com.example.board.domain.Board;
import lombok.Getter;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private List<CommentResponseDto> comments; // 댓글도 DTO로 변환

    // 엔티티 → DTO 변환용 정적 메서드
    public static BoardResponseDto from(Board board) {
        BoardResponseDto dto = new BoardResponseDto();
        dto.id = board.getId();
        dto.title = board.getTitle();
        dto.content = board.getContent();
        dto.nickname = board.getNickname();

        // 댓글도 DTO로 변환
        dto.comments = board.getComments().stream()
                .map(CommentResponseDto::from)
                .toList();

        return dto;
    }
}
