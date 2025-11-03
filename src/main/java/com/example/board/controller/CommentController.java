package com.example.board.controller;

import com.example.board.domain.Comment;
import com.example.board.dto.*;
import com.example.board.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  // REST API 컨트롤러임을 표시, 메서드가 반환하는 객체는 자동으로 JSON으로 직렬화되어 HTTP 응답 본문으로 전송
@RequestMapping("/comments")  // 이 클래스 내의 모든 엔드포인트의 기본 URL prefix가 /comments 라는 뜻
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 댓글 생성
     * @param boardid 댓글을 생성하는 게시글 pk
     * @param request CommentCreateAndUpdateRequest
     * @return 성공 시 201 코드, 댓글 정보, 메세지 반환
     */
    @PostMapping("/boards/{boardid}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
            @Positive(message = "pk값은 0 또는 음수일 수 없습니다.") @PathVariable Long boardid,
            @Valid @RequestBody CommentCreateAndUpdateRequest request) {
        Comment comment = commentService.createComment(boardid, request);
        CommentResponseDto commentDto = CommentResponseDto.from(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(commentDto, "댓글 생성 성공"));
    }

    /**
     * 댓글 수정
     * @param commentId 수정할 댓글 pk
     * @param request   CommentCreateAndUpdateRequest
     * @return
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
            @Positive(message = "pk값은 0 또는 음수일 수 없습니다.") @PathVariable Long commentId,
            @Valid @RequestBody CommentCreateAndUpdateRequest request) {
        Comment comment = commentService.updateComment(commentId, request);
        CommentResponseDto commentDto = CommentResponseDto.from(comment);

        return ResponseEntity.ok(new ApiResponse<>(commentDto, "댓글 수정 성공"));
    }

    /**
     * 댓글 삭제
     * @param commentId 삭제할 댓글 pk
     * @param request  CommentCreateAndUpdateRequest
     * @return
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> deleteComment(
            @Positive(message = "pk값은 0 또는 음수일 수 없습니다.") @PathVariable Long commentId,
            @Valid @RequestBody CommentCreateAndUpdateRequest request) {
        commentService.deleteComment(commentId, request);

        return ResponseEntity.ok(new ApiResponse<>(null, "댓글 삭제 성공"));
    }
}
