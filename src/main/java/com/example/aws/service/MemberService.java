package com.example.aws.service;

import com.example.aws.domain.Member;
import com.example.aws.exception.NoSessionException;
import com.example.aws.repository.MemberRepository;
import com.example.aws.exception.PasswordErrorException;
import com.example.aws.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입하기
     */
    public Member signup(Member member) {
        return memberRepository.save(member);
    }

    /**
     * 아이디 중복체크 하기
     */
    public boolean checkIdDuplicate(String userId) {
        Optional<Member> findMember = memberRepository.findByMemberId(userId);
        return findMember.isPresent();
    }


    /**
     * 로그인 하기
     */
    public Member login(String userId, String password) {
       Member loginMember = memberRepository.findByMemberId(userId)
                .orElseThrow(() -> new MemberNotFoundException("아아디가 존재하지 않습니다."));


        if(!loginMember.getPassword().equals(password)) throw new PasswordErrorException("비밀번호가 틀립니다.");

        return loginMember;
    }

    /**
     * 회원정보 가져오기
     */
    public Member getMemberInfo(Long id) {
        Optional.ofNullable(id).orElseThrow(() -> new NoSessionException("세션이 만료되었습니다."));
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원을 찾을 수 없습니다."));
    }



}
