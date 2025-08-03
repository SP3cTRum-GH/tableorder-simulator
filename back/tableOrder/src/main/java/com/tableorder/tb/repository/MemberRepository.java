package com.tableorder.tb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tableorder.tb.entity.Member;
import com.tableorder.tb.entity.MenuCategory;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);
}
