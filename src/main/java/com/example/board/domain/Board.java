package com.example.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 엔티티
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Board {
    /**
     * 게시판 엔티티: 게시글 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 게시판 엔티티: 제목 column
     */
    @Column(length=20, nullable=false)
    private String title;

    /**
     * 게시판 엔티티: 내용 column
     */
    @Column(length=3000, nullable=false)
    private String content;

    /**
     * 게시판 엔티티: 닉네임 column
     */
    @Column(length=10, nullable=false)
    private String nickname;

    /**
     * 게시판 엔티티: 비밀번호 column
     */
    @Column(length=4, nullable=false)
    private String password;

    /**
     * 소프트 삭제 플래그
     * 게시글 삭제 시 DB에는 남아있고 목록에서만 삭제
     */
    @Column(nullable = false)
    private boolean deleted = false;

    /**
     * 작성 일시
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 게시글과 댓글 관계(일대다)
     * 1(게시글) : N(댓글)
     */
    @OneToMany(mappedBy="board", cascade=CascadeType.ALL) // "board"는 Comment 엔티티의 왜래키
    private List<Comment> comments = new ArrayList<>();
}
