package com.example.board.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequest {
    @NotBlank
    @Size(max = 20)
    private String title;

    @NotBlank
    @Size(max = 3000)
    private String content;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9가-힣]{1,10}$", message = "닉네임은 최대 10자, 특수문자 불가")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^\\d{4}$", message = "비밀번호는 4자리 숫자여야 합니다.")
    private String password;
}

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BoardUpdateRequest {
    @NotBlank
    @Size(max = 20)
    private String title;

    @NotBlank
    @Size(max = 3000)
    private String content;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9가-힣]{1,10}$")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^\\d{4}$")
    private String password;
}

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CommentCreateRequest {
    @NotBlank
    @Size(max = 200)
    private String content;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9가-힣]{1,10}$")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^\\d{4}$")
    private String password;
}

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
}

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BoardListItem {
    private Long id;
    private String title;
    private String nickname;
    private LocalDateTime createdAt;
}

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class BoardDetail {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private List<CommentDto> comments;
}

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
}
