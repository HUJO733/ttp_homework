package com.example.board.controller;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.dto.*;
import com.example.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) { this.boardService = boardService; }

    @PostMapping
    public ResponseEntity<ApiResponse<Board>> createBoard(@RequestBody BoardCreateRequest request) {
        Board board = boardService.createBoard(request);
        return ResponseEntity.status(201).body(new ApiResponse<>(board, "Board created"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Board>>> listBoards() {
        List<Board> boards = boardService.listBoards();
        return ResponseEntity.ok(new ApiResponse<>(boards, "Board list"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Board>> getBoard(@PathVariable Long id) {
        Board board = boardService.getBoard(id);
        return ResponseEntity.ok(new ApiResponse<>(board, "Board detail"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Board>> updateBoard(@PathVariable Long id, @RequestBody BoardUpdateRequest request) {
        Board board = boardService.updateBoard(id, request);
        return ResponseEntity.ok(new ApiResponse<>(board, "Board updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(@PathVariable Long id, @RequestParam String password) {
        boardService.deleteBoard(id, password);
        return ResponseEntity.ok(new ApiResponse<>(null, "Board deleted"));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<Comment>> createComment(@PathVariable Long id, @RequestBody CommentCreateRequest request) {
        Comment comment = boardService.createComment(id, request);
        return ResponseEntity.status(201).body(new ApiResponse<>(comment, "Comment created"));
    }
}
