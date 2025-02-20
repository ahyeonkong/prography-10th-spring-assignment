package assignment.game.PingPong.global.exception;

import assignment.game.PingPong.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 모든 예외 처리 (catch-all)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAllExceptions(Exception ex) {

        // ApiResponse.serverError() 반환
        return new ResponseEntity<>(
                ApiResponse.serverError(), // 서버 에러 응답
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // 특정 예외 처리 (예: IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        // ApiResponse.serverError() 반환
        return new ResponseEntity<>(
                ApiResponse.serverError(), // 서버 에러 응답
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
