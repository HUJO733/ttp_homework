package com.example.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=20, nullable=false)
    private String title;

    @Column(length=3000, nullable=false)
    private String content;

    @Column(length=10, nullable=false)
    private String nickname;

    @Column(length=4, nullable=false)
    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy="board", cascade=CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
