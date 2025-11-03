package com.example.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("status = 'ACTIVE'")
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status = PostStatus.ACTIVE;

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

    @Builder
    public Board(String title, String content, String nickname, String password) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.password = password;
    }

    public void update(String title, String content, String nickname) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
    }

    public void delete() {
        this.status = PostStatus.DELETED;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setBoard(this);
    }
}
