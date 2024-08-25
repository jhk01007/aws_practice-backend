package com.example.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.aws.domain.Member;
import com.example.aws.exception.MemberNotFoundException;
import com.example.aws.exception.NoSessionException;
import com.example.aws.exception.S3ImageUploadException;
import com.example.aws.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class S3Service {
    private final AmazonS3 amazonS3;
    private final MemberRepository repository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public S3Service(AmazonS3 amazonS3, MemberRepository repository) {
        this.amazonS3 = amazonS3;
        this.repository = repository;
    }

    /**
     * S3에 이미지 업로드 하기
     */
    @Transactional
    public String uploadImage(MultipartFile image, Long id) {
        Member member = checkException(id);

        // 파일 확장자 추출
        String extension = getImageExtension(image);
        String fileName = member.getMemberId() + "_profile" + extension;

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        // S3에 파일 업로드 요청 생성
        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata);
        } catch (IOException e) {
            log.error(String.valueOf(e.getCause()));
            throw new S3ImageUploadException("서버오류: 이미지 업로드에 실패하였습니다.");
        }

        // S3에 파일 업로드
        amazonS3.putObject(putObjectRequest);

        // 업로드 후 데이터베이스에 URL 업데이트
        String publicUrl = getPublicUrl(fileName);
        member.setProfileImg(publicUrl);

        return publicUrl;
    }

    private Member checkException(Long id) {
        Optional.ofNullable(id).orElseThrow(() -> new NoSessionException("세션이 만료되었습니다."));
        return repository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원을 찾을 수 없습니다."));
    }

    private String getImageExtension(MultipartFile image) {
        String extension = "";
        String originalFilename = image.getOriginalFilename();

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        return extension;
    }

    private String getPublicUrl(String imageName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), imageName);
    }

}
