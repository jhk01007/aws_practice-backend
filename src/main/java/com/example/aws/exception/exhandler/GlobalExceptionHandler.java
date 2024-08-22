package com.example.aws.exception.exhandler;

import com.example.aws.dto.ErrorDTO;
import com.example.aws.exception.MemberIdDuplicateException;
import com.example.aws.exception.MemberNotFoundException;
import com.example.aws.exception.PasswordErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MemberNotFoundException.class, PasswordErrorException.class})
    public ResponseEntity<ErrorDTO> loginException(RuntimeException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                e.getClass().toString(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberIdDuplicateException.class)
    public ResponseEntity<ErrorDTO> memberIdDuplicationException(RuntimeException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                HttpStatus.CONFLICT.value(),
                e.getClass().toString(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }
}