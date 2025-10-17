package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.dto.*;
import com.example.board.exception.BadRequestException;
import com.example.board.exception.NotFoundException;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
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

    @Transactional
    public Board createBoard(BoardCreateRequest request) {
        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setNickname(request.getNickname());
        board.setPassword(request.getPassword());
        return boardRepository.save(board);
    }

    @Transactional(readOnly=true)
    public List<Board> listBoards() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new NotFoundException("Board not found"));
    }

    @Transactional
    public Board updateBoard(Long id, BoardUpdateRequest request) {
        Board board = getBoard(id);
        if (!board.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("Password mismatch");
        }
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        board.setNickname(request.getNickname());
        return boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long id, String password) {
        Board board = getBoard(id);
        if (!board.getPassword().equals(password)) {
            throw new BadRequestException("Password mismatch");
        }
        boardRepository.delete(board);
    }

    @Transactional
    public Comment createComment(Long boardId, CommentCreateRequest request) {
        Board board = getBoard(boardId);
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setContent(request.getContent());
        comment.setNickname(request.getNickname());
        comment.setPassword(request.getPassword());
        return commentRepository.save(comment);
    }
}
