package com.example.board.dto;

import com.example.board.domain.Board;
import lombok.Getter;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final List<CommentResponseDto> comments;

    @Builder
    public BoardResponseDto(Long id, String title, String content, String nickname,
                            LocalDateTime createdAt, List<CommentResponseDto> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.comments = comments == null ? List.of() : List.copyOf(comments);
    }

    public static BoardResponseDto from(Board board) {
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .nickname(board.getNickname())
                .createdAt(board.getCreatedAt())
                .comments(
                        board.getComments() == null
                                ? List.of()
                                : board.getComments().stream().map(CommentResponseDto::from).collect(Collectors.toList())
                )
                .build();
    }
}
