package com.example.aws.dto;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

    @Data
    public class SignupDTO {
        private String memberId;
        private String password;
        private String phoneNum;
        private MultipartFile image;
    }
