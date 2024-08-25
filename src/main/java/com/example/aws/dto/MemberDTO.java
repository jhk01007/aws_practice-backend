package com.example.aws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDTO {
    private String memberId;
    private String phoneNum;
    private String image; // s3상의 이미지 url
}
