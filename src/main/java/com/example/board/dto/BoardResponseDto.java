package com.example.board.dto;

import com.example.board.domain.Board;
import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class BoardResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final List<CommentResponseDto> comments;

    public static BoardResponseDto from(Board board) {
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .nickname(board.getNickname())
                .createdAt(board.getCreatedAt())
                .comments(
                        board.getComments() == null || board.getComments().isEmpty()
                                ? new ArrayList<>()
                                : board.getComments().stream().map(CommentResponseDto::from).collect(Collectors.toList())
                )
                .build();
    }
}
