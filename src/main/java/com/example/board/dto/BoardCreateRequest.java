package com.example.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreateRequest {
    @NotBlank
    @Size(max=20)
    private String title;

    @NotBlank
    @Size(max=3000)
    private String content;

    @NotBlank
    @Size(max=10)
    @Pattern(regexp="^[a-zA-Z0-9가-힣]+$", message="특수문자 불가")
    private String nickname;

    @NotBlank
    @Pattern(regexp="\\d{4}", message="비밀번호는 4자리 숫자")
    private String password;
}
