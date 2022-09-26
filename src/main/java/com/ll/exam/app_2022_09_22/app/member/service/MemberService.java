package com.ll.exam.app_2022_09_22.app.member.service;

import com.ll.exam.app_2022_09_22.app.member.entity.Member;
import com.ll.exam.app_2022_09_22.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member join(String username, String password, String email) {
        Member member = Member
                .builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        memberRepository.save(member);

        return member;
    }
}
