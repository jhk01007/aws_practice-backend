package com.example.aws;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", length = 10 , nullable = false, unique = true)
    private String userId;
    @Column(length = 20, nullable = false)
    private String password;
    @Column(name = "phone_num", length = 15, nullable = false)
    private String phoneNum;
    @Column(name = "profile_img", length = 200)
    private String profileImg;

    public Member(){}

}
