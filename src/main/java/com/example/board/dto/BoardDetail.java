// src/main/java/com/example/board/dto/BoardDetail.java
package com.example.board.dto;

import java.util.List;

public class BoardDetail {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private List<CommentDto> comments;

    // getters and setters
}
