package com.doragaza.driverlocation.handler.exception;

import com.doragaza.driverlocation.handler.domain.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.logging.Logger;

@Slf4j
public class BaseExceptionHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(BaseExceptionHandler.class.getName());

    protected ResponseEntity<ErrorResponse> createErrorResponse
            (HttpStatus status, String message, String path) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .path(path)
                .build();
        log.error("🚨 [예외 발생] 상태코드: {}, 메시지: {}, 경로: {}", status.value(), message, path);

        return new ResponseEntity<>(errorResponse, status);
    }

    protected void createJsonErrorResponse
            (HttpServletResponse response, HttpStatus status, String message, String detail) throws IOException
    {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .path(detail)
                .build();

        // JSON 변환 및 로그 출력
        String jsonResponse = objectMapper.writeValueAsString(errorResponse.toString());
        logger.warning("에러 발생" + jsonResponse);

        // HTTP 응답 설정
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}
