package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.dto.*;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public Board createBoard(BoardCreateRequest req) {
        Board board = Board.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .nickname(req.getNickname())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .build();
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardListItem> listBoards(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        return boardRepository.findByDeletedFalse(pageable)
                .map(b -> new BoardListItem(b.getId(), b.getTitle(), b.getNickname(), b.getCreatedAt()));
    }

    @Transactional(readOnly = true)
    public BoardDetail getBoardDetail(Long id) {
        Board b = boardRepository.findById(id)
                .filter(board -> !board.isDeleted())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        var comments = commentRepository.findByBoardAndDeletedFalseOrderByCreatedAtAsc(b)
                .stream()
                .map(c -> new CommentDto(c.getId(), c.getContent(), c.getNickname(), c.getCreatedAt()))
                .collect(Collectors.toList());
        return new BoardDetail(b.getId(), b.getTitle(), b.getContent(), b.getNickname(), b.getCreatedAt(), comments);
    }

    @Transactional
    public Board updateBoard(Long id, BoardUpdateRequest req) {
        Board b = boardRepository.findById(id)
                .filter(board -> !board.isDeleted())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        if (!passwordEncoder.matches(req.getPassword(), b.getPasswordHash())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        b.setTitle(req.getTitle());
        b.setContent(req.getContent());
        b.setNickname(req.getNickname());
        return boardRepository.save(b);
    }

    @Transactional
    public void deleteBoard(Long id, String password) {
        Board b = boardRepository.findById(id)
                .filter(board -> !board.isDeleted())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, b.getPasswordHash())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        b.setDeleted(true);
        b.setDeletedAt(java.time.LocalDateTime.now());
        boardRepository.save(b);
    }

    // 댓글 생성/수정/삭제도 유사하게 구현
    @Transactional
    public Comment createComment(Long boardId, CommentCreateRequest req) {
        Board b = boardRepository.findById(boardId)
                .filter(board -> !board.isDeleted())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        Comment c = Comment.builder()
                .board(b)
                .content(req.getContent())
                .nickname(req.getNickname())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .build();
        return commentRepository.save(c);
    }

    @Transactional
    public Comment updateComment(Long commentId, String password, String content, String nickname) {
        Comment c = commentRepository.findById(commentId)
                .filter(cm -> !cm.isDeleted())
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, c.getPasswordHash())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        c.setContent(content);
        c.setNickname(nickname);
        return commentRepository.save(c);
    }

    @Transactional
    public void deleteComment(Long commentId, String password) {
        Comment c = commentRepository.findById(commentId)
                .filter(cm -> !cm.isDeleted())
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, c.getPasswordHash())) {
            throw new BadRequestException("비밀번호가 일치하지 않습니다.");
        }
        c.setDeleted(true);
        c.setDeletedAt(java.time.LocalDateTime.now());
        commentRepository.save(c);
    }
}
