package com.example.board.repository;

import com.example.board.domain.Board;
import com.example.board.domain.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByStatus(PostStatus status);
}
