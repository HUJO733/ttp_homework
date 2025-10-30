package com.example.board.controller;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.dto.*;
import com.example.board.service.BoardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;  // HTTP 상태·헤더·바디를 담아 반환
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // REST API 컨트롤러임을 표시, 메서드가 반환하는 객체는 자동으로 JSON으로 직렬화되어 HTTP 응답 본문으로 전송
@RequestMapping("/boards")  // 이 클래스 내의 모든 엔드포인트의 기본 URL prefix가 /boards 라는 뜻
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * 게시글 생성
     * @param request BoardCreateAndUpdateRequest
     * @return 성공 시 201 코드, 게시글 정보, 메세지 반환
     */
    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponseDto>> createBoard(
            @Valid @RequestBody BoardCreateAndUpdateRequest request) {
            // @Valid: DTO 필드의 @NotBlank, @Size, @Pattern 등 자동 검사 유도, 검증 실패 시 스프링이 자동으로 예외 발생시킴(400)

        Board board = boardService.createBoard(request);
        BoardResponseDto responseDto = BoardResponseDto.from(board);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(responseDto, "게시글 생성 성공"));
    }

    /**
     * 게시글 목록 조회
     * @return 성공 시 200 코드, 게시글 목록, 메세지 반환
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardResponseDto>>> listBoards() {
        List<BoardResponseDto> boardsDto = boardService.listBoards().stream()
                .map(BoardResponseDto::from)
                .toList();

        return ResponseEntity.ok(new ApiResponse<>(boardsDto, "게시글 목록 조회 성공"));
    }

    /**
     * 게시글 상세 조회
     * @param id 상세 조회할 게시글 pk
     * @return 성공 시 200 코드, 게시글 정보, 메세지 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> getBoard(
            @Positive(message = "pk값은 0 또는 음수일 수 없습니다.") @PathVariable Long id) {
        Board board = boardService.getBoard(id);
        BoardResponseDto responseDto = BoardResponseDto.from(board);

        return ResponseEntity.ok(new ApiResponse<>(responseDto, "게시글 상세 조회 성공"));
    }

    /**
     * 게시글 수정
     * @param id      수정할 게시글 pk
     * @param request BoardCreateAndUpdateRequest
     * @return 성공 시 200 코드, 게시글 정보, 메세지 반환
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoard(
            @Positive(message = "pk값은 0 또는 음수일 수 없습니다.") @PathVariable Long id,
            @Valid @RequestBody BoardCreateAndUpdateRequest request) {
        Board board = boardService.updateBoard(id, request);
        BoardResponseDto responseDto = BoardResponseDto.from(board);

        return ResponseEntity.ok(new ApiResponse<>(responseDto, "게시글 수정 성공"));
    }

    /**
     * 게시글 삭제
     * @param id       삭제할 게시글 pk
     * @param password 삭제할 게시글의 비밀번호
     * @return 성공 시 200 코드, 메세지 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(
            @Positive(message = "pk값은 0 또는 음수일 수 없습니다.") @PathVariable Long id,
            @RequestParam String password) {
        boardService.deleteBoard(id, password);

        return ResponseEntity.ok(new ApiResponse<>(null, "게시글 삭제 성공"));
    }

    /**
     * 댓글 생성
     * @param id      댓글을 생성하는 게시글 pk
     * @param request CommentCreateAndUpdateRequest
     * @return 성공 시 201 코드, 댓글 정보, 메세지 반환
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
            @Positive(message = "pk값은 0 또는 음수일 수 없습니다.") @PathVariable Long id,
            @Valid @RequestBody CommentCreateAndUpdateRequest request) {
        Comment comment = boardService.createComment(id, request);
        CommentResponseDto commentDto = CommentResponseDto.from(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(commentDto, "댓글 생성 성공"));
    }

    /**
     * 댓글 수정
     * @param commentId 수정할 댓글 pk
     * @param request   CommentCreateAndUpdateRequest
     * @return
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
            @Positive(message = "pk값은 0 또는 음수일 수 없습니다.") @PathVariable Long commentId,
            @Valid @RequestBody CommentCreateAndUpdateRequest request) {
        Comment comment = boardService.updateComment(commentId, request);
        CommentResponseDto commentDto = CommentResponseDto.from(comment);

        return ResponseEntity.ok(new ApiResponse<>(commentDto, "댓글 수정 성공"));
    }

    /**
     * 댓글 삭제
     * @param commentId 삭제할 댓글 pk
     * @param password  삭제할 댓글의 비밀번호
     * @return
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponseDto>> deleteComment(
            @Positive(message = "pk값은 0 또는 음수일 수 없습니다.") @PathVariable Long commentId,
            @RequestParam String password) {
        boardService.deleteComment(commentId, password);

        return ResponseEntity.ok(new ApiResponse<>(null, "댓글 삭제 성공"));
    }
}
