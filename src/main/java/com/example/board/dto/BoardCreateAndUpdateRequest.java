package com.example.board.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 생성 및 수정
 */
@Getter
@Setter
public class BoardCreateAndUpdateRequest {
    /**
     * 게시글 생성 및 수정: 제목은 20자 내 문자
     */
    @NotBlank
    @Size(max=20)
    private String title;

    /**
     * 게시글 생성 및 수정: 내용은 3000자 내 문자
     */
    @NotBlank
    @Size(max=3000)
    private String content;

    /**
     * 게시글 생성 및 수정: 닉네임은 10자 내 특수문자를 제외한 문자
     */
    @NotBlank
    @Size(max=10)
    @Pattern(regexp="^[a-zA-Z0-9가-힣]+$", message="특수문자 불가")
    private String nickname;

    /**
     * 게시글 생성 및 수정: 비밀번호는 4자리 숫자
     */
    @NotBlank
    @Pattern(regexp="\\d{4}", message="비밀번호: 4자리 숫자")
    private String password;
}
