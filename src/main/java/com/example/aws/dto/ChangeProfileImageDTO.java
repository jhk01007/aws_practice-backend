package com.example.aws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class ChangeProfileImageDTO {

    private MultipartFile image;
}
