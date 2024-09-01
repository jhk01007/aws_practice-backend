package com.example.aws.dto;

import lombok.Data;

@Data
public class SessionDTO {
    private final String memberId;
    private final Boolean isValid;
}
