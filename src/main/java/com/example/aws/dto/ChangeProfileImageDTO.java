package com.example.aws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeProfileImageDTO {

    private String image; // base64 인코딩
}
