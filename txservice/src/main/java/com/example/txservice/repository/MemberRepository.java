package com.example.txservice.repository;

import com.example.txservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Member findMemberByUserId(String userId);

    Optional<Member> findByRegisterId(String registerId);
    Optional<Member> findByUserId(String userId);

    Member findByEmail(String email);
}
