package com.example.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

/**
 * 댓글 엔티티
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted = false")
public class Comment {
    /**
     * 댓글 엔티티: 댓글 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 댓글 엔티티: 내용 column
     */
    @Column(length=200, nullable=false)
    private String content;

    /**
     * 댓글 엔티티: 닉네임 column
     */
    @Column(length=10, nullable=false)
    private String nickname;

    /**
     * 댓글 엔티티: 비밀번호 column
     */
    @Column(length=4, nullable=false)
    private String password;

    /**
     * 소프트 삭제 플래그
     * 댓글 삭제 시 DB에는 남아있고 목록에서만 삭제
     */
    @Column(nullable = false)
    private boolean deleted = false;

    /**
     * 댓글과 게시글 관계(다대일)
     * 댓글 생성 시 해당 게시판 pk가 외래키로 자동 생성
     */
    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @Builder
    public Comment(String content, String nickname, String password) {
        this.content = content;
        this.nickname = nickname;
        this.password = password;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void update(String content, String nickname) {
        this.content = content;
        this.nickname = nickname;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
