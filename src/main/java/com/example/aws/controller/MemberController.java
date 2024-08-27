package com.example.aws.controller;

import com.example.aws.domain.Member;
import com.example.aws.dto.*;
import com.example.aws.exception.MemberIdDuplicateException;
import com.example.aws.service.MemberService;
import com.example.aws.service.S3Service;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 이미지를 MultipartFile 형식으로 받기위해 form 형식으로 받음
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@ModelAttribute SignupDTO signupDTO) {
        String memberId = signupDTO.getMemberId();
        String password = signupDTO.getPassword();
        String phone_num = signupDTO.getPhoneNum();
        MultipartFile image = signupDTO.getImage();

        Member member = new Member(memberId, password, phone_num);

        if(image!= null) s3Service.uploadImage(image, member); // 이미지 업로드
        memberService.signup(member); // 회원가입

        return ResponseEntity.ok("SIGNUP_SUCCESS");
    }

    /**
     * 아이디 중복체크
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
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDto, HttpSession session) {
        Member loginMember = memberService.login(loginDto.getMemberId(), loginDto.getPassword());
        session.setAttribute("member", loginMember.getId());

        return ResponseEntity.ok("LOGIN_SUCCESS");
    }

    /**
     * 프로필 사진  변경
     */
    @PostMapping("/changeProfile")
    public ResponseEntity<String> changeProfileImage(@ModelAttribute ChangeProfileImageDTO imageDTO, HttpSession session) {
        MultipartFile image = imageDTO.getImage();
        Long login_id = (Long) session.getAttribute("member");
        String newImgUrl = s3Service.uploadImage(image, memberService.getMemberInfo(login_id));

        return ResponseEntity.ok(newImgUrl);
    }

    /**
     * 회원 정보(아이디, 전화번호, 프로필 사진) 조회
     */
    @GetMapping("/memberInfo")
    public MemberDTO getMemberInfo(HttpSession session) {
        Long id = (Long) session.getAttribute("member");
        Member memberInfo = memberService.getMemberInfo(id);

        return new MemberDTO(memberInfo.getMemberId(), memberInfo.getPhoneNum(), memberInfo.getProfileImg());
    }

}
