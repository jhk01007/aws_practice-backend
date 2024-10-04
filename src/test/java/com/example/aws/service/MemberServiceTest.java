package com.example.aws.service;

import com.example.aws.domain.Member;
import com.example.aws.exception.MemberNotFoundException;
import com.example.aws.exception.PasswordErrorException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;



    @Test
    void signup() {
        Member member = new Member("kim", "1234","01012345678");
        Member addMember = memberService.signup(member);

        System.out.println(addMember.getId());
        assertThat(member.getMemberId()).isEqualTo(addMember.getMemberId());
    }

    @Test
    void checkIdDuplicate() {
        Member member = new Member("kim", "1234","01012345678");
        Member addMember = memberService.signup(member);

        assertThat(memberService.checkIdDuplicate("kim")).isTrue();
    }

    @Test
    void login_id_error() {
        Member member = new Member("kim", "1234","01012345678");
        memberService.signup(member);
        assertThatThrownBy(() -> memberService.login("lee", "1234"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void login_password_error() {
        Member member = new Member("kim", "1234","01012345678");
        memberService.signup(member);
        assertThatThrownBy(() -> memberService.login("kim", "123456"))
                .isInstanceOf(PasswordErrorException.class);
    }

    @Test
    void login_success() {
        Member member = new Member("kim", "1234","01012345678");
        memberService.signup(member);
        Member loginMember = memberService.login("kim", "1234");
        assertThat(member.getMemberId()).isEqualTo(loginMember.getMemberId());
    }


    @Test
    void getMemberInfo() {
        Member member = new Member("kim", "1234","01012345678");
        Member signupMember = memberService.signup(member);
        Long id = signupMember.getId();

        Member memberInfo = memberService.getMemberInfo(id);

        assertThat(memberInfo).isEqualTo(signupMember);

    }
}