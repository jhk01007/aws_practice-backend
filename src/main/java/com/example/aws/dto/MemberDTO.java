package com.example.aws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDTO {
    private String memberId;
    private String phoneNum;
    private String image; // base64 인코딩
}
