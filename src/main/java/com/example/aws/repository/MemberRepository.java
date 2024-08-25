package com.example.aws.repository;

import com.example.aws.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByMemberId(String memberId);

    @Query("SELECT m.profileImg from Member m where m.id = :id")
    Optional<String> findProfileImageById(@Param("id") Long id);
}
