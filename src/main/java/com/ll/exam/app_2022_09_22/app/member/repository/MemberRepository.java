package com.ll.exam.app_2022_09_22.app.member.repository;

import com.ll.exam.app_2022_09_22.app.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
