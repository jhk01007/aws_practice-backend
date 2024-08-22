package com.example.aws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
