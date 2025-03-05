package com.vfs.backend.exception;

import com.vfs.backend.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setErrorMessage(e.getMessage());
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.toString());
        errorResponseDto.setErrorTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setErrorMessage(e.getMessage());
        errorResponseDto.setStatusCode(HttpStatus.BAD_GATEWAY.toString());
        errorResponseDto.setErrorTime(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponseDto);
    }
}
