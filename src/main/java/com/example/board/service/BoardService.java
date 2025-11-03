package com.example.board.service;

import com.example.board.domain.*;
import com.example.board.dto.*;
import com.example.board.exception.*;
import com.example.board.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    /**
     * 게시글 생성 서비스
     * @param request BoardCreateAndUpdateRequest
     * @return
     */
    @Transactional
    public Board createBoard(BoardCreateAndUpdateRequest request) {
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .nickname(request.getNickname())
                .password(request.getPassword())
                .build();
        return boardRepository.save(board);
    }

    /**
     * 게시글 목록 조회 서비스
     * @return ACTIVE인 게시글(삭제하지 않은)들 조회
     */
    @Transactional(readOnly=true)
    public List<Board> listBoards() {
        return boardRepository.findAllByStatus(PostStatus.ACTIVE);
    }

    /**
     * 게시글 상세 조회 서비스
     * @param id 게시글 pk
     * @return
     */
    @Transactional(readOnly=true)
    public Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("게시글 찾을 수 없음"));
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

        board.update(request.getTitle(), request.getContent(), request.getNickname());
        return boardRepository.save(board);
    }

    /**
     * 게시글 삭제 서비스
     * @param id      삭제할 게시글 pk
     * @param request BoardCreateAndUpdateRequest
     */
    @Transactional
    public void deleteBoard(Long id, BoardCreateAndUpdateRequest request) {
        Board board = getBoard(id);
        if (!board.checkPassword(request.getPassword())) {
            throw new BadRequestException("비밀번호 불일치");
        }

        board.delete();
        boardRepository.save(board); // 변경 사항 저장
    }
}
