package com.example.aws.controller;

import com.example.aws.domain.Member;
import com.example.aws.dto.*;
import com.example.aws.exception.MemberIdDuplicateException;
import com.example.aws.exception.MemberNotFoundException;
import com.example.aws.exception.PasswordErrorException;
import com.example.aws.service.MemberService;
import com.example.aws.service.S3Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final S3Service s3Service;

    public MemberController(MemberService memberService, S3Service s3Service) {
        this.memberService = memberService;
        this.s3Service = s3Service;
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignupDTO signupDTO) {
        String memberId = signupDTO.getMemberId();
        String password = signupDTO.getPassword();
        String phone_num = signupDTO.getPhoneNum();
        String image = signupDTO.getImage();
        String s3Url = null;
        if(image!= null)
            s3Url = s3Service.uploadImage(image);

        memberService.signup(new Member(memberId, password, phone_num, s3Url));
        return ResponseEntity.ok("SIGNUP_SUCCESS");
    }

    /**
     * 아이디 중복체크
     * json 형식으로 데이터를 받기 위해 맵 형태로 받음
     */
    @PostMapping("/checkIdDuplicate")
    public ResponseEntity<Object> checkIdDuplicate(@RequestBody CheckIdDTO checkIdDTO) {
        String memberId = checkIdDTO.getMemberId();
        if(memberService.checkIdDuplicate(memberId))
            throw new MemberIdDuplicateException("중복된 아이디 입니다.");

        return ResponseEntity.ok("USABLE_ID");
    }


    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDto) {
        memberService.login(loginDto.getMemberId(), loginDto.getPassword());
        return ResponseEntity.ok("LOGIN_SUCCESS");
    }

    /**
     * 프로필 사진  변경
     */
    @PostMapping("/changeProfile")
    public ResponseEntity<String> changeProfileImage(@RequestBody ChangeProfileImageDTO imageDTO, HttpSession session) {
        String image = imageDTO.getImage();
        Long login_id = (Long) session.getAttribute("login");
        String newImgUrl = s3Service.editProfileImg(login_id, image);

        return ResponseEntity.ok(newImgUrl);
    }

    /**
     * 회원 정보(아이디, 전화번호, 프로필 사진) 조회
     */
    @GetMapping("/memberInfo/{id}")
    public MemberDTO getMemberInfo(@PathVariable("id") Long id) {
        Member memberInfo = memberService.getMemberInfo(id);
        MemberDTO memberDTO = new MemberDTO(memberInfo.getMemberId(), memberInfo.getPhoneNum(), memberInfo.getProfileImg());

        return memberDTO;
    }

}
