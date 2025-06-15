package com.doragaza.driverlocation.handler.exception;

import com.doragaza.driverlocation.handler.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * 컨트롤러와 프로트엔드 통신 시 HTTP 상태 코드처리 클래스
 */
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler  extends BaseExceptionHandler {

    /**
     * 인증 실패 처리
     * @param e       권한 에러 (SecurityExceprion)
     * @param request 웹 요청 정보
     * @return 401 Unauthorized
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized
    (SecurityException e, WebRequest request) {
        log.error("❌ 예외 발생: {}", e.getMessage(), e);
        return createErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "인증 실패: " + e.getMessage(),
                request.getDescription(false)
        );
    }

    /**
     * 잘못된 요청 처리
     *
     * @param e       (IllegalArgumentException)
     * @param request 웹 요청 정보
     * @return 404 Not Found
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException e, WebRequest request) {
        log.error("❌ 예외 발생: {}", e.getMessage(), e);
        return createErrorResponse(
                HttpStatus.BAD_REQUEST,
                "잘못 된 요청: " + e.getMessage(),
                request.getDescription(false)
        );
    }

    /**
     * 서버 내부 오류 처리
     *
     * @param e       Exception
     * @param request 웹 요청 정보
     * @return 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleServerError(Exception e, WebRequest request) {
        log.error("❌ 예외 발생: {}", e.getMessage(), e);
        return createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "서버 오류: " + e.getMessage(),
                request.getDescription(false)
        );
    }
}