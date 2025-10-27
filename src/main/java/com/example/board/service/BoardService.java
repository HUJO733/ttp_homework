package com.example.board.service;

import com.example.board.domain.*;
import com.example.board.dto.*;
import com.example.board.exception.*;
import com.example.board.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public BoardService(BoardRepository boardRepository, CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 게시글 생성 서비스
     * @param request BoardCreateAndUpdateRequest
     * @return
     */
    @Transactional
    public Board createBoard(BoardCreateAndUpdateRequest request) {
        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setNickname(request.getNickname());
        board.setPassword(request.getPassword());
        return boardRepository.save(board);
    }

    /**
     * 게시글 목록 조회 서비스
     * @return deleted 값이 false인 게시글(삭제하지 않은)들 조회
     */
    @Transactional(readOnly=true)
    public List<Board> listBoards() {
        return boardRepository.findAllByDeletedFalse();
    }

    /**
     * 게시글 상세 조회 서비스
     * @param id 게시글 pk
     * @return
     */
    @Transactional(readOnly=true)
    public Board getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("게시글 찾을 수 없음"));

        // 삭제되지 않은 댓글만 필터링
        board.setComments(
                board.getComments().stream()
                        .filter(comment -> !comment.isDeleted())
                        .toList()
        );

        return board;
    }

    /**
     * 게시글 수정 서비스
     * @param id      수정할 게시글 pk
     * @param request BoardCreateAndUpdateRequest
     * @return
     */
    @Transactional
    public Board updateBoard(Long id, BoardCreateAndUpdateRequest request) {
        Board board = getBoard(id);
        if (!board.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("비밀번호 불일치");
        }
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setNickname(request.getNickname());
        return boardRepository.save(board);
    }

    /**
     * 게시글 삭제 서비스
     * @param id       삭제할 게시글 pk
     * @param password 삭제할 게시글의 비밀번호
     */
    @Transactional
    public void deleteBoard(Long id, String password) {
        Board board = getBoard(id);
        if (!board.getPassword().equals(password)) {
            throw new BadRequestException("비밀번호 불일치");
        }

        board.setDeleted(true); // DB에서 삭제하지 않고 플래그 변경
        boardRepository.save(board); // 변경 사항 저장
    }

    /**
     * 댓글 생성 서비스
     * @param boardId 댓글을 달고자 하는 게시글 pk
     * @param request CommentCreateAndUpdateRequest
     * @return
     */
    @Transactional
    public Comment createComment(Long boardId, CommentCreateAndUpdateRequest request) {
        Board board = getBoard(boardId);
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setContent(request.getContent());
        comment.setNickname(request.getNickname());
        comment.setPassword(request.getPassword());
        return commentRepository.save(comment);
    }

    /**
     * 댓글 수정 서비스
     * @param commentId 수정할 댓글 pk
     * @param request   CommentCreateAndUpdateRequest
     * @return 수정된 댓글
     */
    @Transactional
    public Comment updateComment(Long commentId, CommentCreateAndUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글 찾을 수 없음"));

        if (!comment.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("비밀번호 불일치");
        }

        comment.setContent(request.getContent());
        comment.setNickname(request.getNickname());

        return commentRepository.save(comment);
    }

    /**
     * 댓글 삭제 서비스 (소프트 삭제)
     * @param commentId 삭제할 댓글 pk
     * @param password  삭제할 댓글 비밀번호
     */
    @Transactional
    public void deleteComment(Long commentId, String password) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글 찾을 수 없음"));

        if (!comment.getPassword().equals(password)) {
            throw new BadRequestException("비밀번호 불일치");
        }

        comment.setDeleted(true); // DB에서 삭제하지 않고 플래그 변경
        commentRepository.save(comment);
    }
}
