package com.example.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // @RestController에서 발생하는 예외를 자동으로 잡아 처리
@Slf4j
public class GlobalExceptionHandler {

    // 400 Error 처리
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(Exception e) {

        log.error("에러발생", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다." + e.getMessage());
    }

    // 400 Error 처리
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(Exception e) {

        log.error("에러발생", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다." + e.getMessage());
    }

    // 500 Internal Server Error 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {

        log.error("에러발생", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 오류가 발생했습니다: " + e.getMessage());
    }
}
