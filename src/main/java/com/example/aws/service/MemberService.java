package com.example.aws.service;

import com.example.aws.domain.Member;
import com.example.aws.repository.MemberRepository;
import com.example.aws.exception.PasswordErrorException;
import com.example.aws.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private MemberRepository memberRepository;


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
        Optional<Member> loginMember = memberRepository.findByMemberId(userId);
        if(loginMember.isEmpty()) throw new MemberNotFoundException("아이디가 존재하지 않습니다.");

        if(!loginMember.get().getPassword().equals(password)) throw new PasswordErrorException("비밀번호가 틀립니다.");

        return loginMember.get();
    }

    /**
     * 회원정보 가져오기
     */
    public Member getMemberInfo(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        if(findMember.isEmpty()) throw new MemberNotFoundException("해당 회원을 찾을 수 없습니다.");
        return findMember.get();
    }



}
