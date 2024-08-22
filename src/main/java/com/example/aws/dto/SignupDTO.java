package com.example.aws.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class SignupDTO {
    private String memberId;
    private String password;
    private String phoneNum;
    private String image; // base64 인코딩 형식
}
