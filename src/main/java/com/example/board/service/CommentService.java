package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.dto.CommentCreateAndUpdateRequest;
import com.example.board.exception.BadRequestException;
import com.example.board.exception.NotFoundException;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    /**
     * 댓글 생성 서비스
     * @param boardId 댓글을 달고자 하는 게시글 pk
     * @param request CommentCreateAndUpdateRequest
     * @return
     */
    @Transactional
    public Comment createComment(Long boardId, CommentCreateAndUpdateRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("게시글 찾을 수 없음"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .nickname(request.getNickname())
                .password(request.getPassword())
                .build();

        board.addComment(comment);  // 양방향 연관관계 설정

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

        if (!comment.checkPassword(request.getPassword())) {
            throw new BadRequestException("비밀번호 불일치");
        }

        comment.update(request.getContent(), request.getNickname());
        return commentRepository.save(comment);
    }

    /**
     * 댓글 삭제 서비스 (소프트 삭제)
     * @param commentId 삭제할 댓글 pk
     * @param request  CommentCreateAndUpdateRequest
     */
    @Transactional
    public void deleteComment(Long commentId, CommentCreateAndUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글 찾을 수 없음"));

        if (!comment.checkPassword(request.getPassword())) {
            throw new BadRequestException("비밀번호 불일치");
        }

        comment.delete();
        commentRepository.save(comment);
    }
}
