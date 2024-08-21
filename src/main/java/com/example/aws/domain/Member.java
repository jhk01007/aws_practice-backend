package com.example.aws.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", length = 10 , nullable = false, unique = true)
    private String memberId;
    @Column(length = 20, nullable = false)
    private String password;
    @Column(name = "phone_num", length = 15, nullable = false)
    private String phoneNum;
    @Column(name = "profile_img", length = 200)
    private String profileImg;

    public Member(){}

    public Member(String memberId, String password, String phoneNum) {
        this.memberId = memberId;
        this.password = password;
        this.phoneNum = phoneNum;
    }

    public Member(String memberId, String password, String phoneNum, String profileImg) {
        this.memberId = memberId;
        this.password = password;
        this.phoneNum = phoneNum;
        this.profileImg = profileImg;
    }
}
