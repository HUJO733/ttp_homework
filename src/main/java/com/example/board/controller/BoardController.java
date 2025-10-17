package com.example.board.controller;

import com.example.board.dto.*;
import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createBoard(@Validated @RequestBody BoardCreateRequest req) {
        Board b = boardService.createBoard(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "게시글 생성 성공", b.getId()));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BoardListItem>>> listBoards(@RequestParam(defaultValue = "0") int page) {
        Page<BoardListItem> result = boardService.listBoards(page);
        return ResponseEntity.ok(new ApiResponse<>(200, "성공", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardDetail>> getDetail(@PathVariable Long id) {
        BoardDetail detail = boardService.getBoardDetail(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "성공", detail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> updateBoard(@PathVariable Long id,
                                                         @Validated @RequestBody BoardUpdateRequest req) {
        Board updated = boardService.updateBoard(id, req);
        return ResponseEntity.ok(new ApiResponse<>(200, "수정 성공", updated.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(@PathVariable Long id,
                                                         @RequestParam String password) {
        boardService.deleteBoard(id, password);
        return ResponseEntity.ok(new ApiResponse<>(200, "삭제 성공", null));
    }

    // 댓글 관련 엔드포인트
    @PostMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<Long>> createComment(@PathVariable Long id,
                                                           @Validated @RequestBody CommentCreateRequest req) {
        Comment c = boardService.createComment(id, req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "댓글 생성 성공", c.getId()));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Long>> updateComment(@PathVariable Long commentId,
                                                           @RequestParam String password,
                                                           @Validated @RequestBody CommentCreateRequest req) {
        Comment updated = boardService.updateComment(commentId, password, req.getContent(), req.getNickname());
        return ResponseEntity.ok(new ApiResponse<>(200, "댓글 수정 성공", updated.getId()));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long commentId,
                                                           @RequestParam String password) {
        boardService.deleteComment(commentId, password);
        return ResponseEntity.ok(new ApiResponse<>(200, "댓글 삭제 성공", null));
    }
}
